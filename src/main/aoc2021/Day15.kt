package aoc2021

import AMap
import Pos
import Search

class Day15(input: List<String>) {
    val map = AMap.parse(input)

    class Graph(val map: AMap, private val multiplier: Int) : Search.WeightedGraph {
        private val size = map.xRange().last + 1 // Map is square

        override fun cost(from: Search.Location, to: Search.Location): Float {
            to as Pos
            val extraRisk = to.x / size + to.y / size
            return (((map[Pos(to.x % size, to.y % size)]!!.digitToInt() + extraRisk - 1) % 9) + 1).toFloat()
        }

        override fun neighbours(id: Search.Location): List<Search.Location> {
            id as Pos
            return id.allNeighbours().filter { it.x in 0 until size * multiplier && it.y in 0 until size * multiplier }
        }
    }


    fun solvePart1(): Int {
        val end = Pos(map.xRange().last, map.yRange().last)
        return Search.djikstra(Graph(map, 1), Pos(0, 0), end).cost[end]!!.toInt()
    }

    fun solvePart2(): Int {
        val end = Pos(map.xRange().count() * 5 - 1, map.xRange().count() * 5 - 1)
        return Search.djikstra(Graph(map, 5), Pos(0, 0), end).cost[end]!!.toInt()
    }
}