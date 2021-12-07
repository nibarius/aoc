package aoc2021

class Day6(input: List<String>) {

    private val initial = input.first().split(",").map { it.toInt() }

    private fun solve(iterations: Int): Long {
        var current = LongArray(9)
        initial.forEach { i -> current[i]++ }
        repeat(iterations) {
            val next = LongArray(9)
            for (i in 0..8) {
                if (i == 0) {
                    // at time 0 a lanternfish produces a new one with timer 8 and sets it's own timer to 6
                    next[8] += current[0]
                    next[6] += current[0]
                } else {
                    // all other times just counts down one
                    next[i - 1] += current[i]
                }
            }
            current = next
        }
        return current.sum()
    }

    fun solvePart1(): Long {
        return solve(80)
    }

    fun solvePart2(): Long {
        return solve(256)
    }
}