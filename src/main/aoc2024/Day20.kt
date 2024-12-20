package aoc2024

import AMap
import Pos
import Search

class Day20(input: List<String>) {
    private val map = AMap.parse(input, listOf('#'))
    private val start = map.positionOf('S')
    private val end = map.positionOf('E')
    private val path = listOf(start) + Search.bfs(Graph(map), start, end).getPath(end)

    private class Graph(val map: AMap) : Search.Graph<Pos> {
        override fun neighbours(id: Pos): List<Pos> {
            return id.allNeighbours().filter { it in map }
        }
    }

    private fun countCheats(toSave: Int, cheatDuration: Int): Int {
        var numCheats = 0
        for (fromIndex in path.indices) {
            for (toIndex in (fromIndex + toSave - 2)..<path.size) {
                // A cheat that saves toSave steps requires at least toSave - 2 steps
                // from the starting point (walk 2 steps through one wall). So no need
                // considering any destinations before that.
                val realDistance = toIndex - fromIndex
                val cheatDistance = path[fromIndex].distanceTo(path[toIndex])
                if (realDistance - cheatDistance >= toSave && cheatDistance <= cheatDuration) {
                    numCheats++
                }
            }
        }
        return numCheats
    }

    fun solvePart1(toSave: Int = 100): Int {
        return countCheats(toSave, 2)
    }

    fun solvePart2(toSave: Int = 100): Int {
        return countCheats(toSave, 20)
    }
}