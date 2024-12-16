package aoc2024

import AMap
import ColorMap
import Direction
import Pos
import Search
import java.awt.Color
import java.util.*

class Day16(input: List<String>) {
    private val map = AMap.parse(input, listOf('#'))
    private val goal = map.positionOf('E')
    private val start = map.positionOf('S')

    private data class State(val pos: Pos, val facing: Direction)

    private class Graph(val map: AMap) : Search.WeightedGraph<State> {
        // It's only possible to move forward or turn, it's only possible
        // to turn if it's possible to walk forward after the turn.
        override fun neighbours(id: State): List<State> {
            return listOf(
                State(id.pos.move(id.facing), id.facing).takeIf { it.pos in map },
                State(id.pos, id.facing.turnLeft()).takeIf { it.pos.move(it.facing) in map },
                State(id.pos, id.facing.turnRight()).takeIf { it.pos.move(it.facing) in map }
            ).mapNotNull { it }
        }

        // Moving costs 1, turning costs 1000
        override fun cost(from: State, to: State) = if (from.facing == to.facing) 1f else 1000f
    }

    private fun findGoal(): SearchResult {
        val goalFn: (State) -> Boolean = { it.pos == goal }
        return djikstra(Graph(map), State(start, Direction.Right), goalFn)
    }

    fun solvePart1(): Int {
        val searchResult = findGoal()
        return searchResult.cost[searchResult.goal]!!.toInt()
    }

    fun solvePart2(): Int {
        val searchResult = findGoal()
        return findAllGoodSpots(searchResult.cameFrom, searchResult.goal)
            .also { visualize(it, searchResult.cost, searchResult.goal) }
            .mapTo(HashSet<Pos>()) { it.pos }
            .size
    }

    /**
     * Holds the result of the search.
     * @param goal the State for the goal
     * @param cameFrom a map with State as key and list of other states that leads to this state
     * @param cost a map with State as key and cost to get to that state as value
     */
    private class SearchResult(val goal: State, val cameFrom: Map<State, List<State>>, val cost: Map<State, Float>)

    /**
     * Modified djikstra search that finds all paths with the lowest cost instead
     * of just one.
     */
    private fun djikstra(
        graph: Graph,
        start: State,
        goalFn: (State) -> Boolean
    ): SearchResult {
        val toCheck = PriorityQueue(compareBy<Pair<State, Float>> { it.second })
        toCheck.add(start to 0f)
        val cameFrom = mutableMapOf(start to mutableListOf(start))
        val costSoFar = mutableMapOf(start to 0.0f)
        while (toCheck.isNotEmpty()) {
            val (current, _) = toCheck.remove()
            if (goalFn(current)) {
                return SearchResult(current, cameFrom, costSoFar)
            }
            for (next in graph.neighbours(current)) {
                val nextCost = costSoFar.getValue(current) + graph.cost(current, next)
                val bestCostSoFar = costSoFar.getOrDefault(next, Float.MAX_VALUE)
                if (nextCost < bestCostSoFar) {
                    toCheck.add(next to nextCost)
                    cameFrom[next] = mutableListOf(current)
                    costSoFar[next] = nextCost
                } else if (nextCost == bestCostSoFar) {
                    // We've been here before, with the same cost. Record this as an
                    // alternative way to get here.
                    cameFrom[next]!!.add(current)
                }
            }
        }
        error("no solution found")
    }

    /**
     * Walk backward from the goal to the start to find all the visited nodes in the
     * lowest cost path
     */
    private fun findAllGoodSpots(cameFrom: Map<State, List<State>>, goal: State): Set<State> {
        return buildSet {
            val toCheck = mutableListOf(goal)
            while (toCheck.isNotEmpty()) {
                val current = toCheck.removeFirst()
                if (current !in this) {
                    add(current)
                    toCheck.addAll(cameFrom[current]!!)
                }
            }
        }
    }

    // Draw the found path, the higher the cost the darker the color.
    private fun visualize(visited: Set<State>, cost: Map<State, Float>, goal: State) {
        if (false) {
            val toDraw = map.copy()
            val max = cost[goal]!!.toInt()
            toDraw.apply {
                visited.forEach { state ->
                    this[state.pos] = (cost[state]!!.toInt() * 180 / max).toChar()
                }
            }
            val colorMap = ColorMap.Plasma.getColorMap((1.toChar()..256.toChar()).toList().asReversed())
                .toMutableMap().apply { this['.'] = Color.WHITE.rgb }
            toDraw.drawPng(colorMap)
        }
    }
}