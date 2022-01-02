package aoc2015

class Day8(val input: List<String>) {

    private fun memoryNeeded(str: String): Int {
        var mem = 0
        var i = 1
        while (i < str.length - 1) { // ignore first and last character (the quotes)
            mem++
            i += when {
                str[i] != '\\' -> 1 // Not escaped
                str[i + 1] != 'x' -> 2 // Escaped, but not ascii
                else -> 4 // Escaped ascii character
            }
        }
        return mem
    }

    private fun memoryNeededForEncode(str: String): Int {
        var mem = 2 // need two memory for the new surrounding quotes
        for (ch in str) {
            mem++
            // \ and " are the only characters that needs to be escaped, requiring one additional memory
            if (ch in listOf('\\', '"')) mem++
        }
        return mem
    }

    fun solvePart1(): Int {
        return input.sumOf { it.length - memoryNeeded(it) }
    }

    fun solvePart2(): Int {
        return input.sumOf { memoryNeededForEncode(it) - it.length }
    }
}