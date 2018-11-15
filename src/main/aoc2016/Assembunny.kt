package aoc2016

class Assembunny(private val instructions: List<String>) {

    val registers = mutableMapOf("a" to 0, "b" to 0, "c" to 0, "d" to 0)

    private fun doInstruction(instructionPointer: Int, instruction: String, params: List<String>): Int {
        when (instruction) {
            "cpy" -> registers[params[1]] = valueOf(params[0])

            "inc" -> registers.increase(params[0])
            "dec" -> registers.decrease(params[0])
        // Part 2: Make it possible to optimize the assembunny code by adding support for a add instruction
            "add" -> registers[params[1]] = valueOf(params[0]) + valueOf(params[1])
            "jnz" -> {
                if (valueOf(params[0]) != 0) {
                    return instructionPointer + valueOf(params[1])
                }
            }

        }
        return instructionPointer + 1
    }

    private fun valueOf(value: String): Int {
        return try {
            value.toInt()
        } catch (_: NumberFormatException) {
            registers[value]!!
        }
    }

    fun execute() {
        var instructionPointer = 0
        while (instructionPointer < instructions.size) {
            val inst = instructions[instructionPointer].split(" ")
            instructionPointer = doInstruction(instructionPointer, inst[0], inst.drop(1))
        }
    }
}