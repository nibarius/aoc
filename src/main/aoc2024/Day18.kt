package aoc2024

import Pos
import Search

class Day18(input: List<String>, private val maxCoord: Int = 70) {
    private val fallingBytes = input.withIndex().associate { (index, line) ->
        Pos(
            line.split(",").first().toInt(),
            line.split(",").last().toInt()
        ) to index + 1
    }


    private class Graph(val falling: Map<Pos, Int>, val maxCoord: Int, val time: Int) : Search.Graph<Pos> {
        override fun neighbours(id: Pos): List<Pos> {
            // It's a neighbour if it's within bounds and if it's not falling
            // or will land at a later time than this graph is for.
            return id.allNeighbours().filter {
                it.x in 0..maxCoord && it.y in 0..maxCoord &&
                        falling.getOrDefault(it, Int.MAX_VALUE) > time
            }
        }
    }

    private fun findPathAtTime(t: Int): Search.Result<Pos> {
        val graph = Graph(fallingBytes, maxCoord, t)
        return Search.bfs(graph, Pos(0, 0), Pos(maxCoord, maxCoord))
    }

    fun solvePart1(toSimulate: Int = 1024): Int {
        return findPathAtTime(toSimulate).getPath(Pos(maxCoord, maxCoord)).size
    }

    fun solvePart2(lowerBound: Int = 1024): String {
        val timesToTry = ((lowerBound + 1)..<fallingBytes.size).toList()
        // Find first time that can't get to the exit
        val indexOfFirstMatch = binarySearch(timesToTry) { t ->
            !findPathAtTime(t).cameFrom.containsKey(Pos(maxCoord, maxCoord))
        }
        return fallingBytes
            .filterValues { it == timesToTry[indexOfFirstMatch] }.keys
            .first()
            .let { "${it.x},${it.y}" }
    }

    /**
     * A binary search method that returns the index of the first value in an ordered list
     * that causes the 'match' function to return true. Uses memorization so that
     * the 'match' function is called at most once per index.
     */
    private fun binarySearch(input: List<Int>, match: (Int) -> Boolean): Int {
        var low = 0
        var high = input.size - 1
        var mid: Int
        val memory = mutableMapOf<Int, Boolean>()

        while (low <= high) {
            mid = low + (high - low) / 2
            val criteriaMet = memory[mid] ?: match(input[mid]).also { memory[mid] = it }
            when {
                criteriaMet && (mid == 0 || memory[mid - 1] == false) -> return mid
                !criteriaMet -> low = mid + 1 // not found yet, continue searching
                criteriaMet -> high = mid // mid is true, mid or anything before may be the first
            }
        }
        return -1
    }
}