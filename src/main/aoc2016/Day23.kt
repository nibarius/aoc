package aoc2016

class Day23(private val instructions: List<String>) {

    fun solvePart1(): Int {
        val bunny = Assembunny(instructions.toMutableList())
        bunny.registers["a"] = 7
        bunny.execute()
        return bunny.registers["a"]!!
    }

    fun solvePart2(): Int {
        val bunny = Assembunny(instructions.toMutableList())
        bunny.registers["a"] = 12
        bunny.execute()
        return bunny.registers["a"]!!
    }
}
