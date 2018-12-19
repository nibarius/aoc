package aoc2018


class Day19(input: List<String>) {
    data class Instruction(val name: String, val a: Int, val b: Int, val output: Int) {
        override fun toString(): String {
            return "$name $a $b $output"
        }
    }


    private val registers = MutableList(6) { 0 }
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

    private fun runProgram() {
        while (ip >= 0 && ip < program.size) {
            doInstruction(program[ip])
        }
    }


    fun solvePart1(): Int {
        runProgram()
        return registers[0]
    }

    private fun theProgram() {
        // init part (line 1 + 17 - 35)
        registers[2] += 2 // line 17
        registers[2] *= registers[2] // line 18
        registers[2] *= 19 // line 19
        registers[2] *= 11 // line 20
        registers[1] += 6  // line 21
        registers[1] *= 22 // line 22
        registers[1] += 18 // line 23
        registers[2] += registers[1] // line 24
        if (registers[0] == 1) { // line 25 - 26
            registers[1] = 27
            registers[1] *= 28
            registers[1] += 29
            registers[1] *= 30
            registers[1] *= 14
            registers[1] *= 32
            registers[2] += registers[1]
        }

/*      // Below part calculates the sum of all factorials of registers[2] and put it in registers[0]
        registers[5] = 1 //line 1
        var a = registers[0]
        var b = registers[1]
        val c = registers[2]
        var d = registers[3]
        //var e = registers[4]
        var f = registers[5]
        do { // a = sum of all factors of of c
            d = 1 //line 2
            do {
                b = f * d // line 3
                if (b == c) { // line 4-7
                    a += f
                }
                d++ // line 8
            } while (d <= c) // line 9-11
            f++ // line 12
        } while (f <= c) // line 13-15
        registers[0] = a
        registers[1] = b
        registers[2] = c
        registers[3] = d
        //registers[4] = e
        registers[5] = f
        return //line 16*/

        // Do the calculation in a more efficient way
        registers[0] = sumOfFactors(registers[2])
    }

    private fun sumOfFactors(number: Int): Int {
        return (1..number)
                .filter { number % it == 0 }
                .sum()
    }

    fun solvePart2(): Int {
        registers[0] = 1
        theProgram()
        return registers[0]
    }
}