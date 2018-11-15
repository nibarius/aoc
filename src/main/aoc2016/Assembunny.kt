package aoc2016

class Assembunny(private val instructions: MutableList<String>) {

    val registers = mutableMapOf("a" to 0, "b" to 0, "c" to 0, "d" to 0)

    private fun doInstruction(instructionPointer: Int, instruction: String, params: List<String>): Int {
        when (instruction) {
            "cpy" -> registers[params[1]] = valueOf(params[0])
            "inc" -> registers.increase(params[0])
            "dec" -> registers.decrease(params[0])
        // Day 12 Part 2: Make it possible to optimize the assembunny code by adding support for a add instruction
            "add" -> registers[params[1]] = valueOf(params[0]) + valueOf(params[1])
            "jnz" -> {
                if (valueOf(params[0]) != 0) {
                    return instructionPointer + valueOf(params[1])
                }
            }
        // Day 23: Add a toggle operation
            "tgl" -> {
                val toggleIndex = instructionPointer + valueOf(params[0])
                if (toggleIndex in 0 until instructions.size) {
                    val toToggle = instructions[toggleIndex].split(" ").first()
                    val newInstruction = when (toToggle) {
                        "inc" -> "dec"
                        "dec" -> "inc"
                        "tgl" -> "inc"
                        "jnz" -> "cpy"
                        "cpy" -> "jnz"
                        else -> "error" //tgl is not allowed to toggle add, mul and nop
                    }
                    instructions[toggleIndex] = instructions[toggleIndex].replace(toToggle, newInstruction)
                }
            }
        // Day 23, Part 2: Add a multiplication and nop operation
            "mul" -> registers[params[1]] = valueOf(params[0]) * valueOf(params[1])
            "nop" -> {
                // Does nothing, just need to take up space in the program
                // to be able to detect if the toggle operations tries to toggle it.
                // If it does the instructions replaced with a copy can't be replaced.
            }
            "error" -> throw RuntimeException("Toggle hit a unsupported instruction.")
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