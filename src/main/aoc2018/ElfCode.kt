package aoc2018

class ElfCode(input: List<String>) {
    data class Instruction(val name: String, val a: Int, val b: Int, val output: Int) {
        override fun toString(): String {
            return "$name $a $b $output"
        }
    }


    val registers = MutableList(6) { 0L }
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
        registers[ipReg] = ip.toLong()
        registers[instruction.output] = when (instruction.name) {
            "addr" -> registers[instruction.a] + registers[instruction.b]
            "addi" -> registers[instruction.a] + instruction.b
            "mulr" -> registers[instruction.a] * registers[instruction.b]
            "muli" -> registers[instruction.a] * instruction.b
            "banr" -> registers[instruction.a] and registers[instruction.b]
            "bani" -> registers[instruction.a] and instruction.b.toLong()
            "borr" -> registers[instruction.a] or registers[instruction.b]
            "bori" -> registers[instruction.a] or instruction.b.toLong()
            "setr" -> registers[instruction.a]
            "seti" -> instruction.a.toLong()
            "gtir" -> if (instruction.a > registers[instruction.b]) 1L else 0L
            "gtri" -> if (registers[instruction.a] > instruction.b) 1L else 0L
            "gtrr" -> if (registers[instruction.a] > registers[instruction.b]) 1L else 0L
            "eqir" -> if (instruction.a.toLong() == registers[instruction.b]) 1L else 0L
            "eqri" -> if (registers[instruction.a] == instruction.b.toLong()) 1L else 0L
            "eqrr" -> if (registers[instruction.a] == registers[instruction.b]) 1L else 0L
            else -> throw RuntimeException("Unknown instruction: ${instruction.name}")
        }
        ip = registers[ipReg].toInt()
        ip++
    }


    fun runProgram(breakAt: Int = -1): Int {
        var canBreak = false
        while (ip >= 0 && ip < program.size) {
            if (ip == breakAt && canBreak) {
                return 0
            }
            canBreak = true
            doInstruction(program[ip])
        }
        return 0
    }
}