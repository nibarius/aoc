package aoc2018

class Day18(input: List<String>) {
    data class Pos(val x: Int, val y: Int)

    private var area: Map<Pos, Char>
    private val size: Int = input.size

    init {
        val mutableMap = mutableMapOf<Pos, Char>()
        for (y in 0 until input.size) {
            for (x in 0 until input[0].length) {
                mutableMap[Pos(x, y)] = input[y][x]
            }
        }
        area = mutableMap
    }

    @Suppress("unused")
    fun printArea() {
        for (y in 0 until size) {
            for (x in 0 until size) {
                print("${area.getOrDefault(Pos(x, y), '.')}")
            }
            println()
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
            val nextState = mutableMapOf<Pos, Char>()
            area.keys.forEach { pos -> nextState[pos] = nextState(pos) }
            area = nextState
        }
    }

    private fun findLoop(startAt: Int): List<Int> {
        val scores = mutableListOf<Int>()
        var numSame = 0
        for (second in 1 until startAt + 100) {
            val nextState = mutableMapOf<Pos, Char>()
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