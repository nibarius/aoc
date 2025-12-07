package aoc2025

import AMap
import Direction
import Pos

class Day7(input: List<String>) {
    val map = AMap.parse(input)
    val start = Pos(input.first().indexOf('S'), 0).move(Direction.Down)

    /**
     * Recursively count the number of tachyon beam splits from the given position.
     */
    private fun countNumberOfSplits(from: Pos, visited: MutableSet<Pos>): Int {
        if (from in visited) return 0
        visited.add(from)
        return when {
            map[from] == '.' -> countNumberOfSplits(from.move(Direction.Down), visited)
            map[from] == '^' -> 1 + countNumberOfSplits(from.move(Direction.Left), visited) +
                    countNumberOfSplits(from.move(Direction.Right), visited)

            else -> 0
        }
    }

    fun solvePart1(): Int {
        return countNumberOfSplits(start, mutableSetOf())
    }

    /**
     * Recursively count the total number of unique paths the tachyon beams
     * can take from the given position.
     */
    private fun countDistinctPaths(from: Pos, memory: MutableMap<Pos, Long>): Long {
        return when {
            from in memory -> memory.getValue(from)
            from !in map -> 1
            map[from] == '.' -> countDistinctPaths(from.move(Direction.Down), memory)
            map[from] == '^' -> countDistinctPaths(from.move(Direction.Left), memory) +
                    countDistinctPaths(from.move(Direction.Right), memory)

            else -> error("Invalid character: ${map[from]}")
        }.also { memory[from] = it }
    }

    fun solvePart2(): Long {
        return countDistinctPaths(start, mutableMapOf())
    }
}