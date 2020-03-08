package aoc2015

class Day23(val input: List<String>) {

    private class Computer(val program: List<String>) {
        val registers = mutableMapOf("a" to 0L, "b" to 0L)
        private var ip = 0

        private fun execute(instruction: String) {
            val op: String
            val args: List<String>
            instruction.split(" ", ", ").apply {
                op = first()
                args = drop(1)
            }
            when (op) {
                "inc" -> registers[args[0]] = registers[args[0]]!! + 1
                "hlf" -> registers[args[0]] = registers[args[0]]!! / 2
                "tpl" -> registers[args[0]] = registers[args[0]]!! * 3
                "jmp" -> ip += args[0].toInt() - 1 // -1 for the ip increase below
                "jie" -> ip += if (registers[args[0]]!! % 2 == 0L) args[1].toInt() - 1 else 0
                "jio" -> ip += if (registers[args[0]]!! == 1L) args[1].toInt() - 1 else 0
            }
            ip++
        }

        fun runProgram(): Long {
            while (ip in program.indices) {
                execute(program[ip])
            }
            return registers["b"]!!
        }
    }

    fun solvePart1(): Long {
        return Computer(input).runProgram()
    }

    fun solvePart2(): Long {
        return Computer(input).apply { registers["a"] = 1 }.runProgram()
    }
}