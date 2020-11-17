package aoc2018

import java.util.*

class Day20(input: String) {

    private data class Pos(val x: Int, val y: Int) {
        fun move(dir: Char): Pos {
            return when (dir) {
                'N' -> Pos(x, y - 1)
                'S' -> Pos(x, y + 1)
                'W' -> Pos(x - 1, y)
                'E' -> Pos(x + 1, y)
                else -> throw IllegalArgumentException("Moving in invalid direction")
            }
        }
    }

    private data class Location(val pos: Pos, val steps: Int) {
        fun move(dir: Char) = Location(pos.move(dir), steps + 1)
    }

    // Map of all rooms to the distance required to walk to them
    private val map = makeMap(input.drop(1).dropLast(1))

    // Doesn't support loops like ^NESW$, but my input doesn't have any loops like this.
    // These are probably presented something like ^(NE|W)$ in the input which is supported.
    private fun makeMap(input: String): Map<Pos, Int> {
        val ret = mutableMapOf<Pos, Int>()
        val branchPoints = ArrayDeque<Location>()
        var current = Location(Pos(0, 0), 0)
        for (char in input) {
            when (char) {
                'N', 'S', 'W', 'E' -> {
                    current = current.move(char)
                    ret.putIfAbsent(current.pos, current.steps)
                }
                '(' -> branchPoints.push(current)
                '|' -> current = branchPoints.peek()
                ')' -> current = branchPoints.pop()
            }
        }
        return ret
    }

    fun solvePart1(): Int {
        return map.values.maxOrNull()!!
    }


    fun solvePart2(): Int {
        return map.values.filter { it >= 1000 }.count()
    }
}
