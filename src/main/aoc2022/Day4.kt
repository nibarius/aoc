package aoc2022

import kotlin.math.min

class Day4(input: List<String>) {
    val pairs = input.map { line ->
        line.split(",").map { elf ->
            elf.split("-")
                .let { (first, last) -> first.toInt()..last.toInt() }
                .toSet()
        }
    }

    fun solvePart1(): Int {
        return pairs.count { (a, b) -> (a intersect b).size == min(a.size, b.size) }
    }

    fun solvePart2(): Int {
        return pairs.count { (a, b) -> (a intersect b).isNotEmpty() }
    }
}