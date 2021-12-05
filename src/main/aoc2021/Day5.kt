package aoc2021

import Pos
import increase

class Day5(input: List<String>) {

    data class Line(val start: Pos, val end: Pos) {
        fun isHorizontal() = start.y == end.y
        fun isVertical() = start.x == end.x

        fun allPoints(): List<Pos> {
            val ret = mutableListOf(end)
            var curr = start
            val delta = (end - start).sign()
            while (curr != end) {
                ret.add(curr)
                curr += delta
            }
            return ret
        }
    }

    private val lines = input.map {
        val (x1, y1, x2, y2) = """(\d+),(\d+) -> (\d+),(\d+)""".toRegex().find(it)!!.destructured
        Line(Pos(x1.toInt(), y1.toInt()), Pos(x2.toInt(), y2.toInt()))
    }

    private fun countOverlap(lines: List<Line>): Int {
        val map = mutableMapOf<Pos, Int>()
        lines.forEach { line ->
            line.allPoints().forEach { point -> map.increase(point) }
        }
        return map.values.count { it >= 2 }
    }

    fun solvePart1(): Int {
        return countOverlap(lines.filter { it.isVertical() || it.isHorizontal() })
    }

    fun solvePart2(): Int {
        return countOverlap(lines)
    }
}