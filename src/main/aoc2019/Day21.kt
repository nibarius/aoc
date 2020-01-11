package aoc2019

class Day21(input: List<String>) {
    val parsedInput = input.map { it.toLong() }

    class Springdroid(private val computer: Intcode) {
        fun run(program: List<String>) {
            program.forEach {instruction ->
                instruction.forEach { computer.input.add(it.toLong()) }
                computer.input.add(10L) // Newline
            }
            computer.run()
        }

        fun hullDamage() = computer.output.last()

        @Suppress("unused")
        fun getAsciiOutput(): String {
            return computer.output.map { it.toChar() }.joinToString("")
        }
    }

    fun solvePart1(): Long {
        val droid = Springdroid(Intcode(parsedInput))
        // Jump if landing location is ground (4 steps ahead) and there is any hole before that (1-3 steps ahead)
        droid.run(listOf("NOT A J",
                "NOT B T",
                "OR T J",
                "NOT C T",
                "OR T J",
                "AND D J",
                "WALK"))
        return droid.hullDamage()
    }

    fun solvePart2(): Long {
        val droid = Springdroid(Intcode(parsedInput))
        // Jump if:
        // (part 1) landing location is ground (4 steps ahead) and there is any hole before that (1-3 steps ahead)
        // and (part 2) there is either ground on step 8 (immediately jump again) or on step 5 (can move after landing)
        droid.run(listOf("NOT A J",
                "NOT B T",
                "OR T J",
                "NOT C T",
                "OR T J",
                "AND D J", // Same as part one, and calculate part two condition using t register only
                "NOT E T",
                "NOT T T", // Put e in t
                "OR H T",  // 5 steps ahead or 8 steps ahead is ground
                "AND T J", // part one + extra part 2 condition
                "RUN"))
        return droid.hullDamage()
    }
}