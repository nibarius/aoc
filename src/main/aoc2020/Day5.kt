package aoc2020

class Day5(input: List<String>) {
    private fun String.toBinary() = map { if (it in listOf('B', 'R')) '1' else '0' }.joinToString("")

    private val ids = input
            .map { it.toBinary().toInt(2) }
            .sortedDescending()

    fun solvePart1(): Int {
        return ids.first()
    }

    fun solvePart2(): Int {
        return ids.zipWithNext()
                .first { (first, second) -> second != first - 1 }
                .first - 1
    }
}