package aoc2021

class Day1(input: List<String>) {
   private val numbers = input.map { it.toInt() }

    fun solvePart1(): Int {
        return numPositiveDeltas(numbers)
    }

    fun solvePart2(): Int {
        val sums = numbers.windowed(3, 1).map { it.sum() }
        return numPositiveDeltas(sums)
    }

    private fun numPositiveDeltas(list: List<Int>): Int {
        return list.zipWithNext { a, b -> b - a }.count { it > 0 }
    }
}