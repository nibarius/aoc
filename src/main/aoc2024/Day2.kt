package aoc2024

import kotlin.math.absoluteValue
import kotlin.math.sign

class Day2(input: List<String>) {
    val input = input.map { line -> line.split(" ").map { it.toInt() } }

    /**
     * Returns the index of the first unsafe level in the report or
     * -1 if the report is safe.
     */
    private fun indexOfFirstUnsafeLevel(report: List<Int>): Int {
        val sign = (report[0] - report[1]).sign
        return report.zipWithNext().indexOfFirst { (a, b) ->
            (a - b).sign != sign || (a - b).absoluteValue !in 1..3
        }
    }

    private fun isSafe(report: List<Int>): Boolean {
        return indexOfFirstUnsafeLevel(report) == -1
    }

    fun solvePart1(): Int {
        return input.count { isSafe(it) }
    }

    private fun List<Int>.removeItemAt(index: Int) =
        subList(0, index) + subList(index + 1, size)

    fun solvePart2(): Int {
        return input.count {
            val index = indexOfFirstUnsafeLevel(it)
            index == -1 || // If it's not safe without removals try removing either of the levels that
                    // were compared when the unsafe condition occurred. If the error happens
                    // when comparing the second and third level,
                    // the error might be due to the first being wrong.
                    isSafe(it.removeItemAt(index)) ||
                    isSafe(it.removeItemAt(index + 1)) ||
                    (index == 1 && isSafe(it.removeItemAt(0)))
        }
    }
}