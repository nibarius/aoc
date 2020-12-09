package aoc2020

class Day9(input: List<String>) {
    private val numbers = input.map { it.toLong() }

    // A number is invalid if there are no two entries in the window
    // that sum up to that number.
    private fun invalidNumber(window: List<Long>, sum: Long): Boolean {
        return window.indices.none { i ->
            val candidates = window.toMutableList()
            sum - candidates.removeAt(i) in candidates
        }
    }

    fun solvePart1(preamble: Int): Long {
        return numbers
                .windowed(preamble + 1)
                .first { invalidNumber(it.dropLast(1), it.last()) }
                .last()
    }

    fun solvePart2(target: Long): Long {
        numbers.indices.forEach { first ->
            for (last in first + 2 until numbers.size) {
                val range = numbers.subList(first, last)
                val sum = range.sum()
                when {
                    sum == target -> return range.minOrNull()!! + range.maxOrNull()!!
                    sum > target -> break
                }
            }
        }
        return -1
    }
}