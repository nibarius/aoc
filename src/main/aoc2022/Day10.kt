package aoc2022

class Day10(input: List<String>) {

    data class Instruction(val amount: Int, val duration: Int) {
        companion object {
            fun parse(input: String): Instruction {
                return when (input) {
                    "noop" -> Instruction(0, 1)
                    else -> Instruction(input.substringAfter(" ").toInt(), 2)
                }
            }
        }
    }

    val instructions = input.map { Instruction.parse(it) }

    // history of what value x had at a given point in time, cycle n is at index n-1
    private val xHistory = mutableListOf<Int>().apply {
        var x = 1
        instructions.forEach { instruction ->
            repeat(instruction.duration) {
                this.add(x)
            }
            x += instruction.amount
        }
    }

    fun solvePart1(): Int {
        return generateSequence(20) { it + 40 }
            .takeWhile { it <= 220 }
            .sumOf { xHistory[it - 1] * it }
    }

    private fun Int.spritePosition() = this - 1..this + 1

    fun solvePart2(): String {
        return xHistory.indices
            .map { if (it % 40 in xHistory[it].spritePosition()) '#' else '.' }
            .joinToString("")
            .chunked(40)
            .joinToString("\n")
    }
}