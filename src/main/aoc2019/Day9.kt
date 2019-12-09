package aoc2019

class Day9(input: List<String>) {
    private val parsedInput = input.map { it.toLong() }.toMutableList()
    private fun runComputerWith(input: Long): Long {
        return Intcode(parsedInput, mutableListOf(input)).run {
            run()
            output.first()
        }
    }

    fun solvePart1(): Long {
        return runComputerWith(1)
    }

    fun solvePart2(): Long {
        return runComputerWith(2)
    }
}