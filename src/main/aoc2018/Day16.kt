package aoc2018

import aoc2018.Day16.InstructionName.*

class Day16(input: List<String>) {
    data class Example(val before: List<Int>, val after: List<Int>, val instruction: Instruction)
    data class Instruction(val opcode: Int, val a: Int, val b: Int, val output: Int)

    enum class InstructionName {
        ADDR,
        ADDI,
        MULR,
        MULI,
        BANR,
        BANI,
        BORR,
        BORI,
        SETR,
        SETI,
        GTIR,
        GTRI,
        GTRR,
        EQIR,
        EQRI,
        EQRR
    }

    private val registers = MutableList(4) { 0 }
    private val examples: List<Example>
    private val program: List<Instruction>

    init {
        var examplePart = true
        var i = 0
        val ex = mutableListOf<Example>()
        val prog = mutableListOf<Instruction>()
        while (i < input.size) {
            if (input[i].isEmpty()) {
                examplePart = false
                i += 2
            }
            if (examplePart) {
                val before = input[i++].substringAfter("[").substringBefore("]")
                        .split(", ").map { it.toInt() }
                val instruction = input[i++].split(" ").map { it.toInt() }
                val after = input[i++].substringAfter("[").substringBefore("]")
                        .split(", ").map { it.toInt() }
                i++
                ex.add(Example(before, after, Instruction(instruction[0], instruction[1], instruction[2], instruction[3])))
            } else {
                val instruction = input[i++].split(" ").map { it.toInt() }
                prog.add(Instruction(instruction[0], instruction[1], instruction[2], instruction[3]))
            }
        }
        examples = ex
        program = prog
    }

    private fun doInstruction(instruction: Instruction, getInstruction: (Int) -> InstructionName) {
        registers[instruction.output] = when (getInstruction(instruction.opcode)) {
            ADDR -> registers[instruction.a] + registers[instruction.b]
            ADDI -> registers[instruction.a] + instruction.b
            MULR -> registers[instruction.a] * registers[instruction.b]
            MULI -> registers[instruction.a] * instruction.b
            BANR -> registers[instruction.a] and registers[instruction.b]
            BANI -> registers[instruction.a] and instruction.b
            BORR -> registers[instruction.a] or registers[instruction.b]
            BORI -> registers[instruction.a] or instruction.b
            SETR -> registers[instruction.a]
            SETI -> instruction.a
            GTIR -> if (instruction.a > registers[instruction.b]) 1 else 0
            GTRI -> if (registers[instruction.a] > instruction.b) 1 else 0
            GTRR -> if (registers[instruction.a] > registers[instruction.b]) 1 else 0
            EQIR -> if (instruction.a == registers[instruction.b]) 1 else 0
            EQRI -> if (registers[instruction.a] == instruction.b) 1 else 0
            EQRR -> if (registers[instruction.a] == registers[instruction.b]) 1 else 0
        }
    }

    // Generate a list of opcode -> list of opcodes to instructions that are possible pairs based on example data
    private fun testExamples(): List<Pair<Int, List<InstructionName>>> {
        return examples.map { example ->
            val possible = mutableListOf<InstructionName>()
            entries.forEach { instruction ->
                for (i in 0 until registers.size) {
                    registers[i] = example.before[i]
                }
                doInstruction(example.instruction) { instruction }
                if (registers == example.after) {
                    possible.add(instruction)
                }
            }
            example.instruction.opcode to possible
        }
    }

    // Based on all the example data, generate a map from opcode to list of possible instructions for that opcode
    private fun opcodeToInstructions(): Map<Int, List<InstructionName>> {
        val ret = mutableMapOf<Int, List<InstructionName>>()
        val examples = testExamples()
        (0..15).forEach { opcode ->
            val possibleInstructions = examples.filter { it.first == opcode }.map { it.second }
            ret[opcode] = entries.toMutableList()
                    .filter { instruction -> possibleInstructions.all { it.contains(instruction) } }
        }
        return ret
    }

    // Based on a opcode to list of possible instructions map recursively reduce the map
    // with known opcodes until all is known.
    private tailrec fun reduceInstructions(instructions: Map<Int, List<InstructionName>>): Map<Int, List<InstructionName>> {
        val ret = mutableMapOf<Int, List<InstructionName>>()
        val known = instructions.filter { it.value.size == 1 }.values.flatten()
        instructions.forEach { (key, value) ->
            if (value.size == 1) {
                ret[key] = value
            } else {
                val unknown = value.toMutableList()
                known.forEach { unknown.remove(it) }
                ret[key] = unknown
            }
        }

        return if (ret.any { it.value.size > 1 }) {
            reduceInstructions(ret)
        } else {
            ret
        }
    }

    private fun runProgram(instructions: Map<Int, List<InstructionName>>) {
        program.forEach { doInstruction(it) { instruction -> instructions.getValue(instruction).first() } }
    }

    fun solvePart1(): Int {
        return testExamples().count { it.second.size >= 3 }
    }


    fun solvePart2(): Int {
        val instructions = reduceInstructions(opcodeToInstructions())
        (0 until registers.size).forEach { registers[it] = 0 }
        runProgram(instructions)
        return registers[0]
    }
}