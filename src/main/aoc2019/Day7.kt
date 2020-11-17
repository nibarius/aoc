package aoc2019

class Day7(input: List<String>) {

    private val parsedInput = input.map { it.toLong() }.toList()

    data class Amplifier(val program: List<Long>, val setting: Long) {
        private val computer = Intcode(program, mutableListOf(setting))
        fun isDone() = computer.computerState == Intcode.ComputerState.Terminated
        // Provide input to the computer, run until it halts and return the output
        fun input(value: Long) = computer.apply {
            input.add(value)
            run()
        }.output.removeAt(0)
    }

    private fun runAmplifiers(settings: List<Long>): Long {
        val amps = settings.map { Amplifier(parsedInput, it) }
        var i = 0
        // Sequence of amplifier runs each feeds it's output as input to the next one until all has terminated
        return generateSequence(0L) { amps[i++ % amps.size].input(it) }
                .first { amps.all { it.isDone() } }
    }

    private fun highestSignal(sequences: List<List<Long>>) = sequences.map { runAmplifiers(it) }.maxOrNull()!!

    fun solvePart1(): Long {
        return highestSignal(allPermutationsOf(0..4))
    }

    fun solvePart2(): Long {
        return highestSignal(allPermutationsOf(5..9))
    }

    fun allPermutationsOf(input: IntRange) = mutableListOf<List<Long>>().apply {
        permutations(input.map { it.toLong() }, listOf(), this)
    }

    private fun permutations(remaining: List<Long>, soFar: List<Long>, result: MutableList<List<Long>>) {
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