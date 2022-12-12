package aoc2022

import AMap
import Pos
import Search

class Day12(input: List<String>) {

    private val map = AMap.parse(input)
    private val graph = Graph(map)

    private val start = map.positionOf('S').also { map[it] = 'a' }
    private val end = map.positionOf('E').also { map[it] = 'z' }

    private class Graph(val map: AMap) : Search.Graph<Pos> {
        override fun neighbours(id: Pos): List<Pos> {
            return id.allNeighbours().filter {
                when {
                    map[it] == null -> false // outside map
                    map[it]!! - map[id]!! <= 1 -> true // can go down, straight or max one up
                    else -> false // no other possible moves
                }
            }
        }
    }

    fun solvePart1(): Int {
        val res = Search.bfs(graph, start, end)
        return res.getPath(end).size
    }

    fun solvePart2(): Int {
        val starts = map.keys.filter { map[it] == 'a'}
        return starts.map { Search.bfs(graph, it, end).getPath(end).size }.filter { it > 0 }.min()
    }
}