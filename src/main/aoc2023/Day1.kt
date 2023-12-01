package aoc2023

class Day1(val input: List<String>) {

    /**
     * Search character by character for the first digit in the given order
     */
    private fun findFirstDigit(line: String, iterationOrder: IntProgression): Char {
        val numbers = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

        for (lineOffset in iterationOrder) {
            if (line[lineOffset] in '1'..'9') {
                return line[lineOffset]
            }
            numbers.forEachIndexed { index, textNumber ->
                if (line.drop(lineOffset).startsWith(textNumber)) {
                    return '1' + index
                }
            }
        }
        error("Should not happen")
    }

    private fun getCalibrationValueWithText(line: String): Int {
        val first = findFirstDigit(line, line.indices)
        val last = findFirstDigit(line, line.indices.reversed())
        return "$first$last".toInt()
    }

    private fun getCalibrationValue(line: String): Int {
        val first = line.first { it in '1'..'9' }
        val last = line.last { it in '1'..'9' }
        return "$first$last".toInt()
    }

    fun solvePart1(): Int {
        return input.sumOf { getCalibrationValue(it) }
    }

    fun solvePart2(): Int {
        return input.sumOf { getCalibrationValueWithText(it) }
    }
}