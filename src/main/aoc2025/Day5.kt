package aoc2025

import unionAll

class Day5(input: List<String>) {
    val ranges = input.first().split("\n").map { range ->
        val parts = range.split("-").map { it.toLong() }
        parts.first()..parts.last()
    }
    val ids = input.last().split("\n").map { it.toLong() }

    fun solvePart1(): Int {
        return ids.count{ id -> ranges.any { id in it }}
    }

    fun solvePart2(): Long {
        return ranges.unionAll().sumOf { it.last - it.first + 1 }
    }
}