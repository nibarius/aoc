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

    private data class State(
        val time: Int, val flowRate: Int, val openValves: List<String>, val currentPosition: String,
        val pressureReleased: Int
    )

    private data class State2(
        val time: Int, val flowRate: Int, val openValves: Set<String>, val myPosition: String,
        val elephantPosition: String, val pressureReleased: Int
    )

    private class Graph2(val nameToValve: Map<String, Valve>, val maxCost: Int) : Search.WeightedGraph<State2> {
        private val openableValves = nameToValve.values.filter { it.flowRate > 0 }.map { it.name }.toSet()
        override fun neighbours(id: State2): List<State2> {
            if (id.time == 26) {
                return listOf()
            }
            val canBeOpened = openableValves - id.openValves

            // Pair of next position to valve to open
            val myMoves = mutableListOf<Pair<String, String?>>()
            if (id.myPosition in canBeOpened) {
                myMoves.add(id.myPosition to id.myPosition)
            }
            nameToValve[id.myPosition]!!.leadsTo.forEach { myMoves.add(it to null) }

            // If we are on the same place I always open a valve and let the elephant move
            val elephantMoves = mutableListOf<Pair<String, String?>>()
            if (id.elephantPosition != id.myPosition && id.elephantPosition in canBeOpened) {
                elephantMoves.add(id.elephantPosition to id.elephantPosition)
            }
            nameToValve[id.elephantPosition]!!.leadsTo.forEach { elephantMoves.add(it to null) }

            val ret = mutableListOf<State2>()
            for (myMove in myMoves) {
                for (elephantMove in elephantMoves) {
                    var addedFlowRate = myMove.second?.let { nameToValve[it]!!.flowRate } ?: 0
                    addedFlowRate += elephantMove.second?.let { nameToValve[it]!!.flowRate } ?: 0

                    val open = id.openValves.toMutableSet().apply {
                        myMove.second?.apply { add(this) }
                        elephantMove.second?.apply { add(this) }
                    }
                    ret.add(
                        State2(
                            id.time + 1,
                            id.flowRate + addedFlowRate,
                            open,
                            myMove.first,
                            elephantMove.first,
                            id.pressureReleased + id.flowRate + addedFlowRate
                        )
                    )
                }
            }
            return ret
        }

        override fun cost(from: State2, to: State2): Float {
            return (maxCost - to.flowRate).toFloat()
        }
    }


    private class Graph(val nameToValve: Map<String, Valve>, val maxCost: Int) : Search.WeightedGraph<State> {
        override fun neighbours(id: State): List<State> {
            if (id.time == 30) {
                return listOf()
            }
            // Not worth doing a fast-forward when time is up, extra checks every time takes more than the few nodes it cuts down
            /*if (id.openValves.size == nameToValve.values.filter { it.flowRate > 0 }.size) {
                // all valves are open, we can fast-forward to the end
                val timeLeft = 30 - id.time
                return listOf(
                    State(
                        30,
                        id.flowRate,
                        id.openValves,
                        id.currentPosition,
                        id.pressureReleased + id.flowRate * timeLeft
                    )
                )
            }*/
            val currentValve = nameToValve[id.currentPosition]!!
            val ret = mutableListOf<State>()
            if (id.currentPosition !in id.openValves && currentValve.flowRate > 0) {
                // can be opened, try opening it
                ret.add(
                    State(
                        id.time + 1,
                        id.flowRate + currentValve.flowRate,
                        id.openValves.toMutableList().apply { add(currentValve.name) },
                        id.currentPosition,
                        id.pressureReleased + id.flowRate + currentValve.flowRate

                    )
                )
            }
            // move to all other valves
            currentValve.leadsTo.forEach { neighbour ->
                ret.add(
                    State(
                        id.time + 1,
                        id.flowRate,
                        id.openValves,
                        neighbour,
                        id.pressureReleased + id.flowRate
                    )
                )
            }
            return ret
        }

        override fun cost(from: State, to: State): Float {
            return (maxCost - to.flowRate).toFloat()
        }
    }


    private val start = State(1, 0, listOf(), "AA", 0)
    private val start2 = State2(1, 0, setOf(), "AA", "AA", 0)


    fun solvePart1old(): Int { // solves part 1 example, but runs out of memory on real input
        val res = Search.djikstra2(Graph(nameToValve, maxCost), start, { pos -> pos.time == 30 })

        val x = res.cost.filterKeys { it.time == 30 }
        val max = x.maxBy { it.key.pressureReleased }.key.pressureReleased
        val path = res.getPath(x.keys.first())
        return max
    }

    fun solvePart1(): Int { // solves part 1 example, but runs out of memory on real input
        val res = Search.aStar2(Graph(nameToValve, maxCost), start, { pos -> pos.time == 30 },
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


    // TODO: need to change the graph to merge nodes that can't be opened. All rate zero (except AA) has only two neighbours
    fun solvePart2(): Int { // part 2 real input runs out of memory after a while even with heuristic (that doesn't work
        var prints = 0
        val res = Search.aStar2(Graph2(nameToValve, maxCost), start2, { pos -> pos.time == 26 },
            { start, _ ->
                // This does not work, for some reason it overestimates the cost. Don't understand why (takes 1500 visits)
                val stillClosed = nodes.filter { it.flowRate > 0 && it.name !in start.openValves }.map { it.flowRate }
                stillClosed.sortedDescending().sum().toFloat()
                // dropping two makes it work with 3769 visits

                // estimate cost to 1 all the time make it work (but bad estimate), 3206 nodes visited
                //1f


                // simple best case heuristic, close best valve, move one step, close one valve each
                /*val stillClosed =
                    nodes.filter { it.flowRate > 0 && it.name !in start.openValves }.map{it.flowRate}.sortedBy { -it }
                val x = stillClosed.windowed(2).map { it.sum() }
                x.withIndex().sumOf { (i, v) -> (2 * i + 1) * v }.toFloat()*/
            }
        )
        val x = res.cost.filterKeys { it.time == 26 }
        val max = x.maxBy { it.key.pressureReleased }.key.pressureReleased
        val path = res.getPath(x.keys.first())
        return max
    }
}