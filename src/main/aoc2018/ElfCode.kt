package aoc2018

class ElfCode(input: List<String>) {
    data class Instruction(val name: String, val a: Int, val b: Int, val output: Int) {
        override fun toString(): String {
            return "$name $a $b $output"
        }
    }


    val registers = MutableList(6) { 0 }
    private val program: List<Instruction>
    private val ipReg: Int
    private var ip = 0

    init {
        val prog = mutableListOf<Instruction>()
        ipReg = input.first().takeLast(1).toInt()
        for (i in 1 until input.size) {
            val instruction = input[i].split(" ")
            prog.add(Instruction(instruction[0], instruction[1].toInt(), instruction[2].toInt(), instruction[3].toInt()))
        }
        program = prog
    }

    private fun doInstruction(instruction: Instruction) {
        registers[ipReg] = ip
        registers[instruction.output] = when (instruction.name) {
            "addr" -> registers[instruction.a] + registers[instruction.b]
            "addi" -> registers[instruction.a] + instruction.b
            "mulr" -> registers[instruction.a] * registers[instruction.b]
            "muli" -> registers[instruction.a] * instruction.b
            "banr" -> registers[instruction.a] and registers[instruction.b]
            "bani" -> registers[instruction.a] and instruction.b
            "borr" -> registers[instruction.a] or registers[instruction.b]
            "bori" -> registers[instruction.a] or instruction.b
            "setr" -> registers[instruction.a]
            "seti" -> instruction.a
            "gtir" -> if (instruction.a > registers[instruction.b]) 1 else 0
            "gtri" -> if (registers[instruction.a] > instruction.b) 1 else 0
            "gtrr" -> if (registers[instruction.a] > registers[instruction.b]) 1 else 0
            "eqir" -> if (instruction.a == registers[instruction.b]) 1 else 0
            "eqri" -> if (registers[instruction.a] == instruction.b) 1 else 0
            "eqrr" -> if (registers[instruction.a] == registers[instruction.b]) 1 else 0
            else -> throw RuntimeException("Unknown instruction: ${instruction.name}")
        }
        ip = registers[ipReg]
        ip++
    }

    fun runProgram() {
        while (ip >= 0 && ip < program.size) {
            doInstruction(program[ip])
        }
    }
}