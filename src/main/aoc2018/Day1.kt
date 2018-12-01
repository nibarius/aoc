package aoc2018


class Day1(input: List<String>) {

    private val parsedInputs = parseInput(input)

    private fun parseInput(input: List<String>): List<Int> {
        val ret = mutableListOf<Int>()
        input.forEach { ret.add(it.toInt()) }
        return ret
    }

    private fun findFirstRepeatedFrequency(): Int {
        val seenFrequencies = mutableSetOf(0)
        var currentFrequency = 0
        while (true) {
            parsedInputs.forEach {
                currentFrequency += it
                if (seenFrequencies.contains(currentFrequency)) {
                    return currentFrequency
                }
                seenFrequencies.add(currentFrequency)
            }
        }
    }

    fun solvePart1(): Int {
        return parsedInputs.sum()
    }

    fun solvePart2(): Int {
        return findFirstRepeatedFrequency()
    }
}