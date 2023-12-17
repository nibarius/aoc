package aoc2023

import AMap
import ColorMap
import Direction
import Pos
import Search
import Visualization
import java.awt.Color

class Day17(input: List<String>) {

    private val city = AMap.parse(input)

    // Instead of keeping track of how many steps we have walked in one direction
    // always require a turn after a move and add one neighbour for each distance
    // walked. Walking one step right, two steps right and three steps right are
    // all neighbours. Makes calculating cost a bit more complicated, but it results
    // in fewer states and almost half runtime on part 2.
    private data class State(val pos: Pos, val cameFrom: Direction)

    private class Graph(val city: AMap, val minDistance: Int, val maxDistance: Int) : Search.WeightedGraph<State> {
        override fun neighbours(id: State): List<State> {
            val directions = Direction.entries.toMutableSet().apply {
                remove(id.cameFrom)
                if (id.pos != Pos(0, 0)) remove(id.cameFrom.opposite())
            }
            return directions.flatMap { dir ->
                val steps = minDistance..maxDistance
                steps.mapNotNull { distance ->
                    val next = id.pos.move(dir, distance)
                    when {
                        !city.contains(next) -> null
                        else -> State(id.pos.move(dir, distance), dir.opposite())
                    }
                }
            }
        }

        override fun cost(from: State, to: State): Float {
            val diff = to.pos - from.pos
            val distance = diff.distanceToOrigin
            var currentPos = from.pos
            var currentCost = 0
            repeat(distance) {
                currentPos += diff.sign()
                currentCost += city[currentPos]!!.toString().toInt()
            }
            return currentCost.toFloat()
        }
    }

    private fun searchGraph(minDistance: Int, maxDistance: Int): Pair<State, Search.Result<State>> {
        val graph = Graph(city, minDistance, maxDistance)
        val start = State(Pos(0, 0), Direction.Up)
        val goal = Pos(city.xRange().last, city.yRange().last)
        return Search.aStar(
            graph,
            start,
            goalFn = { state: State -> state.pos == goal },
            heuristic = { state: State -> state.pos.distanceTo(goal).toFloat() }
        )
    }


    @Suppress("unused")
    private fun visualizeBothParts() {
        fun allBetween(from: Pos, to: Pos): List<Pos> {
            return buildList {
                val diff = to - from
                var currentPos = from
                repeat(diff.distanceToOrigin) {
                    currentPos += diff.sign()
                    add(currentPos)
                }
            }
        }

        val part1 = searchGraph(1, 3)
        val part2 = searchGraph(4, 10)
        val path1 = (mutableListOf(Pos(0, 0)) + part1.second.getPath(part1.first).map { it.pos })
            .zipWithNext().flatMap { (start, stop) -> allBetween(start, stop) }
        val path2 = (mutableListOf(Pos(0, 0)) + part2.second.getPath(part2.first).map { it.pos })
            .zipWithNext().flatMap { (start, stop) -> allBetween(start, stop) }
        val colorMap = ColorMap.Inferno.getColorMap(('1'..'9').toList())
            .toMutableMap()
            .apply {
                this['s'] = Color.RED.rgb
                this['g'] = Color.GREEN.rgb
                this['*'] = Color(200, 255, 0).rgb
                this['#'] = Color(0, 200, 255).rgb
            }

        val copy = city.copy().apply {
            path1.forEach { pos -> this[pos] = '*' }
            path2.forEach { pos -> this[pos] = '#' }
            this[Pos(0, 0)] = 's'
            this[Pos(xRange().last, yRange().last)] = 'g'
        }

        Visualization(10).drawPng(copy, colorMap, "2023-day-17.png")
    }

    private fun costToGoal(minDistance: Int, maxDistance: Int): Int {
        val result = searchGraph(minDistance, maxDistance)
        return result.second.cost[result.first]!!.toInt()
    }

    fun solvePart1(): Int {
        return costToGoal(1, 3)
    }

    fun solvePart2(): Int {
        //visualizeBothParts()
        return costToGoal(4, 10)
    }
}