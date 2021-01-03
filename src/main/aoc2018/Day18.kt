package aoc2018

import Grid
import Pos

class Day18(input: List<String>) {
    private var area = Grid.parse(input)

    private fun nextState(area: Grid, pos: Pos): Char {
        val trees = area.numNeighboursWithValue(pos, '|', true)
        val yards = area.numNeighboursWithValue(pos, '#', true)
        return when (area[pos]) {
            '.' -> if (trees >= 3) '|' else '.'
            '|' -> if (yards >= 3) '#' else '|'
            '#' -> if (yards >= 1 && trees >= 1) '#' else '.'
            else -> throw RuntimeException("Unknown character: ${area[pos]}")
        }
    }


    /**
     * Tries to find a loop within the given amount of interations. If
     * a loop is found the first entry in the pair is the loop length and
     * the second the scores or the loop. If no loop is found the second
     * entry in the pair is a list containing only the score of the last iteration
     */
    private fun findLoop(maxIterations: Int): Pair<Int, List<Int>> {
        val scores = mutableListOf<Int>() // index 0 is score for minute 1
        val seen = mutableMapOf<Int, Int>() // hashcode to minute seen last
        var current = area
        var next = area.copy()
        var tmp: Grid
        for (minute in 1..maxIterations) {
            current.keys.forEach { pos -> next[pos] = nextState(current, pos) }
            tmp = current
            current = next
            next = tmp

            val hash = current.hashCode()
            if (seen.containsKey(hash)) { // loop found
                return seen[hash]!! to scores.drop(seen[hash]!! - 1)
            }
            seen[hash] = minute
            scores.add(getScore(current))
        }
        return -1 to scores.takeLast(1)
    }

    private fun getScore(area: Grid) = area.count('#') * area.count('|')

    fun solvePart1(): Int {
        return findLoop(10).second.single()
    }

    fun solvePart2(): Int {
        val target = 1000000000
        val (start, loop) = findLoop(target)
        val index = (target - start) % loop.size
        return loop[index]
    }
}