package aoc2024

import kotlin.math.abs

class Day1(input: List<String>) {
    val left: List<Int>
    val right: List<Int>

    init {
        val (l, r) = input.map { line ->
            line.split("   ")
                .let { it.first().toInt() to it.last().toInt() }
        }.unzip()
        left = l
        right = r
    }

    fun solvePart1(): Int {
        return left.sorted().zip(right.sorted()).sumOf {
            abs(it.second - it.first)
        }
    }

    fun solvePart2(): Int {
        return left.sumOf { l -> l * right.count { it == l } }
    }
}