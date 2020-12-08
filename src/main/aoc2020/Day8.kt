package aoc2020

class Day8(input: List<String>) {

    private data class Instruction(val operation: String, val argument: Int)
    private class Computer(val program: List<Instruction>) {
        var accumulator = 0
            private set

        /**
         * Runs the program. Returns true if successful or false if an infinite loop was detected.
         */
        fun run(): Boolean {
            var pc = 0
            val seen = mutableSetOf<Int>()
            while (pc < program.size) {
                if (seen.contains(pc)) return false
                seen.add(pc)
                val (op, arg) = program[pc]
                when (op) {
                    "acc" -> accumulator += arg
                    "jmp" -> pc += arg - 1
                }
                pc++
            }
            return true
        }
    }

    private val program = input
            .map { it.split(" ")
            .let { (op, arg) -> Instruction(op, arg.toInt()) } }

    /**
     * Generate a sequence of all possible programs with one nop/jmp instruction swapped
     */
    private fun generatePrograms(): Sequence<List<Instruction>> {
        val ret = mutableListOf<List<Instruction>>()
        for (i in program.indices) {
            val (op, arg) = program[i]
            if (op in listOf("nop", "jmp")) {
                val newOp = if (op == "nop") "jmp" else "nop"
                program.toMutableList()
                        .apply { this[i] = Instruction(newOp, arg) }
                        .also { ret.add(it) }
            }
        }
        return ret.asSequence()
    }

    fun solvePart1(): Int {
        return Computer(program).apply { run() }.accumulator
    }

    fun solvePart2(): Int {
        return generatePrograms()
                .map { Computer(it) }
                .first { it.run() }
                .accumulator
    }
}