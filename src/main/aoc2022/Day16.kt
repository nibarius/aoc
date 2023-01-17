package aoc2022

import Search

/**
 * Main idea:
 *  - Reduce the map to traverse by creating an optimized search graph where only the significant nodes
 *    are included. Nodes without valves are not included
 *  - Also include opening a valve in the action. That is each action is move to another valve and open it.
 *    So it's not possible to move to a valve and not open it. You may of cause move past valves in the
 *    original map, but in the optimized search graph that's included in the distance to the destination valve.
 *  - Do a* search on the optimized graph to find the best path to maximize the amount of pressure vented.
 *    Some notes on how this is done and what to keep in mind:
 *     + To avoid negative costs, use amount of pressure not being vented as cost
 *     + Goal state is unknown, so use a goal function instead (stop searching when time is up regardless
 *       of which valves are open and where in the graph the actors are).
 *     + As a cost heuristic move to the closest valve and open the most valuable valve each time.
 *     + The order of in which the valves are opened is important
 *   - Use the same logic for both part 1 (one actor) and part 2 (two actors).
 *     + With one actor, the second actor is stuck at location "Done" all the time and doesn't help out.
 *     + With two actors it's important that they don't move to the same valves
 *     + Having one actor go to A and the other go to B is the same as the first goes to B and the second to A.
 */
class Day16(input: List<String>) {

    private data class Valve(val name: String, val flowRate: Int, val leadsTo: List<String>)

    private val nodes = input.map { line ->
        val regex = "Valve ([\\w]{2}) has flow rate=(\\d+); tunnels? leads? to valves? (.*)".toRegex()
        val (valve, rate, leadTo) = regex.find(line)!!.destructured
        Valve(valve, rate.toInt(), leadTo.split(", "))
    }
    private val nameToValve = nodes.associateBy { it.name }

    /**
     * The distances from every significant node to every other significant node.
     */
    private val allDistances = distances()

    /**
     * A simple graph over the input just as it's given without any modifications. Used for BFS to find
     * the shortest paths between significant nodes which is used for the real graph where the main
     * pathfinding takes place.
     */
    private class VolcanoMap(private val nodes: Map<String, Valve>) : Search.Graph<String> {
        override fun neighbours(id: String) = nodes[id]!!.leadsTo
    }

    /**
     * The time it takes to go from any valve to any other valve and open that valve.
     */
    private fun distances(): Map<String, Map<String, Int>> {
        val map = VolcanoMap(nameToValve)
        val significantNodes = nodes.filter { it.name == "AA" || it.flowRate > 0 }
        return significantNodes.associate { from ->
            val res = Search.bfs(map, from.name, null)
            from.name to significantNodes.filter { it != from && it.name != "AA" }
                .associate { to -> to.name to res.getPath(to.name).size + 1 }
        }
    }

    /**
     * Used for the different states while searching.
     * Time and pressure released needs to be tracked, but it's not used for comparisons between states.
     * @param pos List of the current positions for the actors, has either one or two values.
     * @param dist List of remaining distance until the actor reaches the position indicated in pos.
     *             Has either one or two values, at least one value is always 0.
     * @param openValves The valves that have been opened so far, in the order they were opened.
     */
    private data class State(val pos: List<String>, val dist: List<Int>, val openValves: List<String>) {
        var timeLeft = 0
        var pressureReleased = 0
    }

    /**
     * An optimized graph representing the volcano and actions that can be taken. The only actions that can be taken
     * is to walk to an unvisited valve and open the valve there. There is no need to track any intermediate valves
     * as you always walk the shortest path to the next valve you want to open.
     * @param distances The time it takes to go from one valve to another and open it (for all combinations of valves)
     * @param valves A map between valve names and valves.
     */
    private class Graph(val distances: Map<String, Map<String, Int>>, val valves: Map<String, Valve>) :
        Search.WeightedGraph<State> {
        val maximumFlowRate = valves.values.sumOf { it.flowRate } // todo: better name
        val numValves = valves.values.count { it.flowRate > 0 }
        override fun neighbours(id: State): List<State> {
            if (id.openValves.size == numValves) {
                // All valves are open, fast-forward to the end
                return listOf(
                    State(listOf("Done", "Done"), listOf(0, 0), id.openValves).apply {
                        timeLeft = 0
                        pressureReleased = id.pressureReleased + id.timeLeft * maximumFlowRate
                    }
                )
            }

            // Don't walk to valves that the other player is going to
            val closedValves = valves.values.filter { it.flowRate > 0 && it.name !in id.openValves + id.pos }

            // moves is a list with two elements: List of all moves for p1 and list of all moves for p2
            // Each move is name + time it takes to go there
            val moves = (id.pos zip id.dist).map { (pos, dist) ->
                if (dist != 0) {
                    // Continue the move in progress
                    listOf(pos to dist)
                } else {
                    // Start moving toward a new valve
                    if (closedValves.isEmpty() || pos == "Done") {
                        // Everything open, or already done (elephant is always in done state in part 1): do nothing.
                        listOf("Done" to id.timeLeft)
                    } else {
                        closedValves.map { it.name to distances[pos]!![it.name]!! }
                    }
                }
            }

            // Set of all possible moves pairs, each move pair is a set of my move and the elephant move
            // Each move is in turn a destination-time pair.
            val toVisit = mutableSetOf<Set<Pair<String, Int>>>()
            moves.first().forEach { me ->
                moves.last().forEach { elephant ->
                    // Never have both go to the same node at once
                    if (me.first != elephant.first) {
                        toVisit.add(setOf(me, elephant))
                    }
                }
            }

            // Create all the neighbouring states from the move sets.
            return toVisit.map { moveSet ->
                val (me, elephant) = moveSet.toList()
                // Fast-forward to when the next move is done for either actor or to when time is up.
                val nextTimeLeft = listOf(0, id.timeLeft - me.second, id.timeLeft - elephant.second).max()
                val timeTaken = id.timeLeft - nextTimeLeft
                val currentlyVenting = id.openValves.sumOf { valves[it]!!.flowRate }
                val openValves = id.openValves.toMutableList().apply {
                    // open a valve when time to destination reaches 0
                    if (me.second - timeTaken == 0) add(me.first)
                    if (elephant.second - timeTaken == 0) add(elephant.first)
                }
                State(
                    listOf(me.first, elephant.first),
                    listOf(me.second - timeTaken, elephant.second - timeTaken),
                    openValves
                ).apply {
                    timeLeft = id.timeLeft - timeTaken
                    pressureReleased = id.pressureReleased + timeTaken * currentlyVenting
                }
            }
        }

        /**
         * The cost to move between two states is the amount of pressure that is not being released
         * during the time it takes to move.
         */
        override fun cost(from: State, to: State): Float {
            val notVenting = maximumFlowRate - from.openValves.sumOf { valves[it]!!.flowRate }
            return (notVenting * (from.timeLeft - to.timeLeft)).toFloat()
        }
    }

    /**
     * Estimate remaining cost by assuming that the distance all actors have to walk to reach a valve is
     * the shortest distance to any open valve, including from the current position of a player that's
     * on its way to a valve already. Also assume that all players will open the best valves possible
     * every time.
     *
     * This never overestimates and is fairly close with my input (88% of the real cost when estimating
     * on the first node on part 2).
     */
    private fun costHeuristic(currentState: State): Float {
        var timeLeft = currentState.timeLeft
        var estimatedCost = 0

        // Closed valves ordered by the most useful first
        val closedValves = nodes.filter { it.flowRate > 0 && it.name !in currentState.openValves }
            .sortedBy { -it.flowRate }
            .toMutableList()

        if (closedValves.isEmpty()) {
            return 0f
        }

        val timeLeftToFinishStartedAction = currentState.dist.sum() // at least one is always zero
        val minDistanceBetweenValves = closedValves.minOf { node -> allDistances[node.name]!!.values.min() }
        val minDistanceToAValve = if (timeLeftToFinishStartedAction in 1 until minDistanceBetweenValves) {
            timeLeftToFinishStartedAction
        } else {
            minDistanceBetweenValves
        }
        var timeToNextOpen = minDistanceToAValve
        while (timeLeft > 0 && closedValves.isNotEmpty()) {
            if (timeToNextOpen == 0) {
                // Each actor that's not done can open a valve
                repeat(currentState.pos.filter { it != "Done" }.size) {
                    closedValves.removeFirstOrNull()
                }
                timeToNextOpen = closedValves.minOfOrNull { node -> allDistances[node.name]!!.values.min() } ?: 0
            } else {
                timeToNextOpen--
            }
            estimatedCost += closedValves.sumOf { it.flowRate }
            timeLeft--
        }
        return estimatedCost.toFloat()
    }

    /**
     * Find the best path using A*
     */
    private fun solveGeneric(start: State): Int {
        val res = Search.aStar(
            graph = Graph(distances(), nameToValve),
            start = start,
            goalFn = { node -> node.timeLeft == 0 },
            heuristic = ::costHeuristic
        )
        return res.first.pressureReleased
    }

    fun solvePart1(): Int {
        return solveGeneric(State(listOf("AA", "Done"), listOf(0, 0), listOf()).apply { timeLeft = 30 })
    }

    fun solvePart2(): Int {
        return solveGeneric(State(listOf("AA", "AA"), listOf(0, 0), listOf()).apply { timeLeft = 26 })
    }
}