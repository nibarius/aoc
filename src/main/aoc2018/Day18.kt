package aoc2018

import Pos
import AMap

class Day18(input: List<String>) {
    private var area = AMap()

    init {
        for (y in input.indices) {
            for (x in input[0].indices) {
                area[Pos(x, y)] = input[y][x]
            }
        }
    }

    // Get all adjacent acres (x for acres outside the area)
    private fun getAdjacent(pos: Pos): List<Char> {
        return (-1..1).map { dy -> (-1..1).map { dx -> Pos(pos.x + dx, pos.y + dy) } }
                .flatten()
                .filterNot { it == pos }
                .map { area.getOrDefault(it, 'x') }

    }

    private fun nextState(pos: Pos): Char {
        val adjacent = getAdjacent(pos)
        return when (area[pos]) {
            '.' -> if (adjacent.count { it == '|' } >= 3) '|' else '.'
            '|' -> if (adjacent.count { it == '#' } >= 3) '#' else '|'
            '#' -> if (adjacent.count { it == '#' } >= 1 && adjacent.count { it == '|' } >= 1) '#' else '.'
            else -> throw RuntimeException("Unknown character: ${area[pos]}")
        }
    }

    private fun letTimePass(minutes: Int) {
        repeat(minutes) {
            val nextState = AMap()
            area.keys.forEach { pos -> nextState[pos] = nextState(pos) }
            area = nextState
        }
    }

    private fun findLoop(startAt: Int): List<Int> {
        val scores = mutableListOf<Int>()
        var numSame = 0
        for (second in 1 until startAt + 100) {
            val nextState = AMap()
            area.keys.forEach { pos -> nextState[pos] = nextState(pos) }
            area = nextState
            val score = getScore()
            val minLoopLength = 10

            if (scores.isNotEmpty() && score == scores[0]) {
                return scores
            }
            if (second >= startAt) {
                scores.add(score)
            }
            if (second > startAt + minLoopLength && scores[numSame] == score) {
                numSame++
            }
        }
        throw java.lang.RuntimeException("No loop found")
    }

    private fun getScore() = area.values.count { it == '#' } * area.values.count { it == '|' }

    fun solvePart1(): Int {
        letTimePass(10)
        return getScore()
    }

    fun solvePart2(): Int {
        val startAt = 500
        val target = 1000000000
        val loop = findLoop(startAt)
        val index = (target - startAt) % loop.size
        return loop[index]
    }
}