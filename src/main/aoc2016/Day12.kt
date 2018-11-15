package aoc2016

class Day12(private val instructions: List<String>) {

    fun solvePart1(): Int {
        val bunny = Assembunny(instructions)
        bunny.execute()
        return bunny.registers["a"]!!
    }

    fun solvePart2(): Int {
        val bunny = Assembunny(instructions)
        bunny.registers["c"] = 1
        bunny.execute()
        return bunny.registers["a"]!!
    }
}
