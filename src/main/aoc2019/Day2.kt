package aoc2019


class Day2(input: List<String>) {

    private val parsedInput = input.map { it.toLong() }.toMutableList()

    fun solvePart1(): Long {
        val computer = Intcode(parsedInput)
        computer.reinitialize(listOf(Pair(1L, 12L), Pair(2L, 2L)))
        computer.run()
        return computer.dumpMemory()[0]
    }

    fun solvePart2(): Int {
        val desired = 19690720L
        val computer = Intcode(parsedInput)
        for (noun in 0 until 99) {
            for (verb in 0 until 99) {
                computer.reinitialize(listOf(Pair(1L, noun.toLong()), Pair(2L, verb.toLong())))
                computer.run()
                if (computer.dumpMemory()[0] == desired) {
                    return 100 * noun + verb
                }
            }
        }
        return -1
    }
}