package aoc2019

import java.lang.RuntimeException

class Intcode(private val initialMemory: List<Int>) {
    private var instructionPointer = 0
    private val memory = mutableListOf<Int>()

    sealed class Instruction(val numParams: Int) {
        class Add : Instruction(3)
        class Multiply : Instruction(3)
        class Terminate: Instruction(0)
        fun length() = numParams + 1
    }

    private fun getInstructionAndParameters(memory: List<Int>, address: Int): Pair<Instruction, List<Int>> {
        val instruction = when (val opcode = memory[address]) {
            1 -> Instruction.Add()
            2 -> Instruction.Multiply()
            99 -> Instruction.Terminate()
            else -> throw RuntimeException("Invalid opcode encountered: $opcode")
        }

        val parameters = if (address + 1 + instruction.numParams > memory.size) {
            listOf()
        } else {
            memory.subList(address + 1, address + 1 + instruction.numParams)
        }

        return Pair(instruction, parameters)
    }

    fun run() {
        while (instructionPointer < memory.size) {
            val (instruction, params) = getInstructionAndParameters(memory, instructionPointer)
            when (instruction) {
                is Instruction.Terminate -> return
                is Instruction.Add -> memory[params[2]] = memory[params[0]] + memory[params[1]]
                is Instruction.Multiply -> memory[params[2]] = memory[params[0]] * memory[params[1]]
            }
            instructionPointer += instruction.length()
        }
    }

    val output: Int
        get() {
            return memory[0]
        }

    // Sets the two inputs at address 1 and 2 and resets the state of the computer while doing so
    fun initialize(a: Int? = null, b: Int? = null) {
        memory.clear()
        memory.addAll(initialMemory)
        a?.let { memory[1] = it }
        b?.let { memory[2] = it }
        instructionPointer = 0
    }

    fun dumpMemory() = memory.toList()
}