package aoc2022

import Search

class Day16(input: List<String>) {

    private data class Valve(val name: String, val flowRate: Int, val leadsTo: List<String>)

    private val nodes = input.map { line ->
        val regex = "Valve ([\\w]{2}) has flow rate=(\\d+); tunnels? leads? to valves? (.*)".toRegex()
        val (valve, rate, leadTo) = regex.find(line)!!.destructured
        Valve(valve, rate.toInt(), leadTo.split(", "))
    }
    private val nameToValve = nodes.associateBy { it.name }

    // the cost of having all valves closed. this is how much pressure is not being released
    private val maxCost = nodes.sumOf { it.flowRate }

    private val optimizedMap = optimizeMap()
    private val nameToNode = optimizedMap.associateBy { it.name }

    // leadsTo is a pair of node + distance to that node
    private data class Node(val name: String, val flowRate: Int, val leadsTo: List<Pair<String, Int>>)

    private tailrec fun walkToNextSignificantNode(from: String, isAt: String, distanceSoFar: Int): Pair<String, Int> {
        val current = nameToValve[isAt]!!
        if (current.flowRate > 0 || current.leadsTo.size > 2) {
            return current.name to distanceSoFar
        }
        val next = current.leadsTo.first { it != from }
        return walkToNextSignificantNode(current.name, next, distanceSoFar + 1)
    }

    private fun optimizeMap(): Set<Node> {
        return nodes.filter { it.flowRate > 0 || it.leadsTo.size > 2 }
            .map { valve ->
                val tunnels = valve.leadsTo.map { walkToNextSignificantNode(valve.name, it, 1) }
                Node(valve.name, valve.flowRate, tunnels)
            }.toSet()
    }

    private class OptimizedGraph(val nameToNode: Map<String, Node>, val maxCost: Int) : Search.WeightedGraph<State3> {
        override fun neighbours(id: State3): List<State3> {
            if (id.time >= 30) {
                return listOf()
            }
            if (id.openValves.size == nameToNode.size) {
                // All valves open, fast-forward to time 30
                return listOf(
                    State3(
                        30,
                        id.flowRate,
                        id.openValves,
                        id.currentPosition,
                        id.pressureReleased + id.flowRate * (30 - id.time),
                        null
                    )
                )
            }

            val ret = mutableListOf<State3>()

            // Things that can be done:
            //  - Open a valve
            //  - Walk to another room (but don't walk back)
            val currentValve = nameToNode[id.currentPosition]!!
            if (currentValve.name !in id.openValves && currentValve.flowRate > 0) {
                ret.add(
                    State3(
                        id.time + 1,
                        id.flowRate + currentValve.flowRate,
                        id.openValves.toMutableList().apply { add(currentValve.name) },
                        id.currentPosition,
                        id.pressureReleased + id.flowRate + currentValve.flowRate,
                        null
                    )
                )
            }

            currentValve.leadsTo
                .filter { it.first != id.cameFrom }
                .forEach { neighbour ->
                    val timeLeft = 30 - id.time
                    if (timeLeft > neighbour.second) {
                        // Enough time to walk to the neighbour and possibly open a valve
                        ret.add(
                            State3(
                                id.time + neighbour.second,
                                id.flowRate,
                                id.openValves,
                                neighbour.first,
                                id.pressureReleased + id.flowRate * neighbour.second,
                                currentValve.name
                            )
                        )
                    } else {
                        // Not enough time to walk to the neighbour. Just wait for time to run out.
                        ret.add(
                            State3(
                                30,
                                id.flowRate,
                                id.openValves,
                                id.currentPosition,
                                id.pressureReleased + id.flowRate * timeLeft,
                                null
                            )
                        )
                    }
                }
            return ret
        }

        override fun cost(from: State3, to: State3): Float {
            // Cost to move between states is the amount of pressure not being released during the
            // time it takes to move between the states.
            return ((maxCost - to.flowRate) * (to.time - from.time)).toFloat()
        }
    }

    private data class Move(val dest: String, val durationLeft: Int, val valveToOpen: String?)

    private data class State3(
        val time: Int, val flowRate: Int, val openValves: List<String>, val currentPosition: String,
        val pressureReleased: Int, val cameFrom: String?
    )

    private class BetterGraph(val nameToNode: Map<String, Node>, val maxCost: Int) : Search.WeightedGraph<BetterState> {
        override fun neighbours(id: BetterState): List<BetterState> {
            if (id.time >= numMinutes) {
                return listOf()
            }
            if (id.openValves.size == nameToNode.size - 1) { // AA is not openable
                // All valves open, fast-forward to time 26
                return listOf(
                    BetterState(
                        id.myPosition,
                        id.myTimeToDest,
                        id.elephantPosition,
                        id.elephantTimeToDest,
                        id.openValves,
                        true
                    ).apply {
                        time = numMinutes
                        // all valves are open and releases pressure at maxCost rate until the end.
                        pressureReleased = id.pressureReleased + maxCost * (numMinutes - id.time)
                    }
                )
            }

            // Set of all moves that can be made, how I move to how elephant moves.
            val moves = mutableSetOf<Pair<Move, Move>>()

            val myCurrentPos = nameToNode[id.myPosition]!!
            val elephantCurrentPos = nameToNode[id.elephantPosition]!!

            if (id.elephantPosition == id.myPosition && id.elephantTimeToDest == 0 && id.myTimeToDest == 0) {
                // We are at the same place and both can take action
                val canVisit = myCurrentPos.leadsTo

                if (myCurrentPos.name !in id.openValves && myCurrentPos.flowRate > 0) {
                    // valve can be opened, I open valve and elephant moves
                    val myMove = Move(myCurrentPos.name, 1, myCurrentPos.name)
                    canVisit.forEach { next ->
                        val elephantMove = Move(next.first, next.second, null)
                        moves.add(myMove to elephantMove)
                    }
                }

                // Ignore the valve and have both move.
                // There's no difference between (me->A, elephant->B) and (me->B, elephant->A) so
                // use sets to keep track of where to go so that duplicates gets merged.
                val toVisit = mutableSetOf<Set<Pair<String, Int>>>()
                canVisit.forEach { me ->
                    canVisit.forEach { elephant ->
                        toVisit.add(setOf(me, elephant))
                    }
                }

                toVisit.forEach { destinations ->
                    if (destinations.size == 1) {
                        // Both are going to the same location
                        val dest = destinations.single()
                        val bothMoves = Move(dest.first, dest.second, null)
                        moves.add(bothMoves to bothMoves)
                    } else {
                        // We're going to different places
                        val (me, elephant) = destinations.toList()
                        val myMove = Move(me.first, me.second, null)
                        val elephantMove = Move(elephant.first, elephant.second, null)
                        moves.add(myMove to elephantMove)
                    }
                }
            } else {
                // Not the same place, both act independently
                val myMoves = mutableListOf<Move>()
                if (id.myTimeToDest == 0) {
                    // I can take action
                    if (myCurrentPos.name !in id.openValves && myCurrentPos.flowRate > 0) { // open valve
                        myMoves.add(Move(myCurrentPos.name, 1, myCurrentPos.name))
                    }

                    // Move
                    // desperate test, never move away from closed valves that has a flow rate higher than 10, those should always be opened.
                    if (myCurrentPos.name in id.openValves || myCurrentPos.flowRate < 4) {
                        myCurrentPos.leadsTo.forEach { nextLocation ->
                            myMoves.add(Move(nextLocation.first, nextLocation.second, null))
                        }
                    }
                } else {
                    // Currently moving towards a new node, just keep moving and reduce time to arrival
                    // time to dest will be reduced by one further down where the actual next step is created
                    myMoves.add(Move(id.myPosition, id.myTimeToDest, null))
                }
                val elephantMoves = mutableListOf<Move>()
                if (id.elephantTimeToDest == 0) {
                    // Elephant can take action
                    if (elephantCurrentPos.name !in id.openValves && elephantCurrentPos.flowRate > 0) { // open valve
                        elephantMoves.add(Move(elephantCurrentPos.name, 1, elephantCurrentPos.name))
                    }
                    // Move
                    // desperate test, never move away from valves that has a flow rate higher than 10, those should always be opened.
                    if (elephantCurrentPos.name in id.openValves || elephantCurrentPos.flowRate < 4) {
                        elephantCurrentPos.leadsTo.forEach { nextLocation ->
                            elephantMoves.add(Move(nextLocation.first, nextLocation.second, null))
                        }
                    }
                } else {
                    // Currently moving towards a new node, just keep moving and reduce time to arrival
                    elephantMoves.add(Move(id.elephantPosition, id.elephantTimeToDest, null))
                }

                // Make a list of all possible move combinations
                myMoves.forEach { myMove ->
                    elephantMoves.forEach { elephantMove ->
                        moves.add(myMove to elephantMove)
                    }
                }
            }

            val ret = moves.map { (myMove, elephantMove) ->
                var addedFlowRate = myMove.valveToOpen?.let { nameToNode[it]!!.flowRate } ?: 0
                addedFlowRate += elephantMove.valveToOpen?.let { nameToNode[it]!!.flowRate } ?: 0

                val openValves = id.openValves.toMutableList().apply {
                    myMove.valveToOpen?.let { add(it) }
                    elephantMove.valveToOpen?.let { add(it) }
                }

                BetterState(
                    myMove.dest,
                    myMove.durationLeft - 1,
                    elephantMove.dest,
                    elephantMove.durationLeft - 1,
                    openValves
                ).apply {
                    time = id.time + 1
                    pressureReleased = id.pressureReleased + openValves.sumOf { nameToNode[it]!!.flowRate }
                }
            }

            return ret
        }

        override fun cost(from: BetterState, to: BetterState): Float {
            // Cost to move between states is the amount of pressure not being released during the
            // time it takes to move between the states.
            val releasedPressure = to.openValves.sumOf { nameToNode[it]!!.flowRate }
            return (maxCost - releasedPressure).toFloat()
        }
    }

    data class BetterState(
        val myPosition: String, val myTimeToDest: Int,
        val elephantPosition: String, val elephantTimeToDest: Int,
        val openValves: List<String>,
        val isComplete: Boolean = false
    ) {
        var time = -1
        var pressureReleased = -1

        override fun toString(): String {
            return "t: $time, m: $myPosition($myTimeToDest), e: $elephantPosition($elephantTimeToDest), o: $openValves, p: $pressureReleased"
        }
    }

    fun solvePart1(): Int {
        val start3 = State3(1, 0, listOf(), "AA", 0, null)
        val res = Search.aStar2(OptimizedGraph(nameToNode, maxCost), start3, { pos -> pos.time == 30 },
            { start, _ ->
                // simple best case heuristic, close best valve, move one step, close one valve
                val stillClosed =
                    nodes.filter { it.flowRate > 0 && it.name !in start.openValves }.sortedBy { -it.flowRate }
                stillClosed.withIndex().sumOf { (i, v) -> (2 * i + 1) * v.flowRate }.toFloat()
            })

        val x = res.cost.filterKeys { it.time == 30 }
        val max = x.maxBy { it.key.pressureReleased }.key.pressureReleased
        val path = res.getPath(x.keys.first())
        return max
    }

    companion object {
        private const val numMinutes = 26
    }

    private fun costHeuristic(currentState: BetterState, goalFn: (BetterState) -> Boolean): Float {
            var timeLeft = numMinutes - currentState.time
            var estimatedCost = 0
            var openValveTurn = true
            val stillClosed =
                nodes.filter { it.flowRate > 0 && it.name !in currentState.openValves }
                    .sortedBy { -it.flowRate }
                    .toMutableList()
            // Absolute best case heuristic, open the two best valves, move one step, open the two best
            // and continue until all valves are open
            while (timeLeft > 0 && stillClosed.isNotEmpty()) {
                if (openValveTurn) {
                    // open the two best valves
                    stillClosed.removeFirstOrNull()
                    stillClosed.removeFirstOrNull()
                }
                // else: move one space (doesn't change what's closed)
                timeLeft--
                openValveTurn = !openValveTurn
                val notVentingThisTurn = stillClosed.sumOf { it.flowRate }
                estimatedCost += notVentingThisTurn
            }

            return estimatedCost.toFloat()
    }

    fun solvePart2(): Int {
        val start = BetterState("AA", 0, "AA", 0, listOf())
            .apply {
                time = 1
                pressureReleased = 0
            }
        val res = Search.aStar2(BetterGraph(nameToNode, maxCost), start,
            { pos -> pos.time == numMinutes },
            ::costHeuristic
            )

        val x = res.cost.filterKeys { it.time == numMinutes }
        val max = x.maxBy { it.key.pressureReleased }.key.pressureReleased
        return max
    }

}