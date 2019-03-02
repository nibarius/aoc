package aoc2018

import java.lang.Math.abs

class Day25(input: List<String>) {

    data class Point(val coord: List<Int>) {
        fun distanceTo(other: Point): Int {
            return coord.zip(other.coord).map { abs(it.first - it.second) }.sum()
        }
    }

    private val points = parseInput(input)

    private fun parseInput(input: List<String>): Set<Point> {
        val ret = mutableSetOf<Point>()
        input.forEach { line ->
            ret.add(Point(line.split(",").map { it.toInt() }))
        }
        return ret
    }

    private fun makeConstellations(points: Set<Point>): List<Set<Point>> {
        val constellations = mutableListOf<MutableSet<Point>>()
        points.forEach { point ->
            val partOf = constellations.filter { it.map { it.distanceTo(point) }.any { it <= 3 } }
            when {
                partOf.isEmpty() -> constellations.add(mutableSetOf(point))
                partOf.size == 1 -> partOf.first().add(point)
                else -> {
                    constellations.removeAll(partOf)
                    constellations.add(partOf.flatten().toMutableSet().apply { add(point) })
                }
            }
        }
        return constellations.toList()
    }

    fun solvePart1(): Int {
        return makeConstellations(points).size
    }
}
