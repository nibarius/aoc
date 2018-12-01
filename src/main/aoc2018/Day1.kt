package aoc2018


class Day1(input: List<String>) {

    private val parsedInput = input.map { it.toInt() }

    private fun findFirstRepeatedFrequency(): Int {
        val seenFrequencies = mutableSetOf(0)
        var currentFrequency = 0
        while (true) {
            parsedInput.forEach {
                currentFrequency += it
                if (seenFrequencies.contains(currentFrequency)) {
                    return currentFrequency
                }
                seenFrequencies.add(currentFrequency)
            }
        }
    }

    fun solvePart1(): Int {
        return parsedInput.sum()
    }

    fun solvePart2(): Int {
        return findFirstRepeatedFrequency()
    }
}