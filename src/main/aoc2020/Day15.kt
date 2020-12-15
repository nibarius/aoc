package aoc2020

class Day15(input: List<String>) {
    private val starting = input.map { it.toInt() }

    private fun playGame(maxTurns: Int): Sequence<Int> = sequence {
        yieldAll(starting)
        val spokenEarlier = IntArray(maxTurns) { -1 }.apply {
            starting.withIndex().forEach { (index, value) -> this[value] = index }
        }
        // Simplification: The starting list contains unique elements, first after the start will always be 0
        var toSay = 0
        var turn = starting.size
        while (true) {
            yield(toSay)
            val nextValue = turn - if (spokenEarlier[toSay] != -1) spokenEarlier[toSay] else turn
            spokenEarlier[toSay] = turn++
            toSay = nextValue
        }
    }

    private fun solve(turns: Int) = playGame(turns).drop(turns - 1).first()

    fun solvePart1(): Int {
        return solve(2020)
    }

    fun solvePart2(): Int {
        return solve(30_000_000)
    }
}