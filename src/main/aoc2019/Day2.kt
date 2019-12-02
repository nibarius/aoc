package aoc2019


class Day2(input: List<String>) {

    private val parsedInput = input.map { it.toInt() }.toMutableList()

    fun solvePart1(): Int {
        val computer = Intcode(parsedInput)
        computer.initialize(12, 2)
        computer.run()
        return computer.output
    }

    fun solvePart2(): Int {
        val desired = 19690720
        val computer = Intcode(parsedInput)
        for (noun in 0 until 99) {
            for (verb in 0 until 99) {
                computer.initialize(noun, verb)
                computer.run()
                if (computer.output == desired) {
                    return 100 * noun + verb
                }
            }
        }
        return -1
    }
}