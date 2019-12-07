package aoc2019

class Day7(input: List<String>) {

    private val parsedInput = input.map { it.toInt() }.toList()

    data class Amplifier(val program: List<Int>, val setting: Int) {
        private val computer = Intcode(program, mutableListOf(setting))
        fun isDone() = computer.computerState == Intcode.ComputerState.Terminated
        // Provide input to the computer, run until it halts and return the output
        fun input(value: Int) = computer.apply {
            input.add(value)
            run()
        }.output.removeAt(0)
    }

    private fun runAmplifiers(settings: List<Int>): Int {
        val amps = settings.map { Amplifier(parsedInput, it) }
        var i = 0
        // Sequence of amplifier runs each feeds it's output as input to the next one until all has terminated
        return generateSequence(0) { amps[i++ % amps.size].input(it) }
                .first { amps.all { it.isDone() } }
    }

    private fun highestSignal(sequences: List<List<Int>>) = sequences.map { runAmplifiers(it) }.max()!!

    fun solvePart1(): Int {
        return highestSignal(allPermutationsOf(0..4))
    }

    fun solvePart2(): Int {
        return highestSignal(allPermutationsOf(5..9))
    }

    fun allPermutationsOf(input: IntRange) = mutableListOf<List<Int>>().apply {
        permutations(input.toList(), listOf(), this)
    }

    private fun permutations(remaining: List<Int>, soFar: List<Int>, result: MutableList<List<Int>>) {
        if (remaining.isEmpty()) {
            result.add(soFar)
            return
        }

        for (i in remaining.indices) {
            permutations(
                    remaining.toMutableList().apply { removeAt(i) },
                    soFar.toMutableList().apply { add(remaining[i]) },
                    result
            )
        }
    }
}