package aoc2019


class Day5(input: List<String>) {

    private val parsedInput = input.map { it.toInt() }.toMutableList()


    fun solvePart1(): Int {
        val computer = Intcode(parsedInput, mutableListOf(1))
        computer.run()
        return  computer.output.last()
    }

    fun solvePart2(): Int {
        val computer = Intcode(parsedInput, mutableListOf(5))
        computer.run()
        return  computer.output.last()
    }
}