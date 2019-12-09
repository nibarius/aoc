package aoc2019


class Day5(input: List<String>) {

    private val parsedInput = input.map { it.toLong() }.toMutableList()


    fun solvePart1(): Long {
        val computer = Intcode(parsedInput, mutableListOf(1))
        computer.run()
        return  computer.output.last()
    }

    fun solvePart2(): Long {
        val computer = Intcode(parsedInput, mutableListOf(5))
        computer.run()
        return  computer.output.last()
    }
}