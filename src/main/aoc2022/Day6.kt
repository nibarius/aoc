package aoc2022

class Day6(input: List<String>) {

    private val buffer = input.first()

    private fun findFirstMarker(markerLength: Int): Int {
        return buffer.windowedSequence(markerLength).indexOfFirst { it.toSet().size == markerLength } + markerLength
    }

    fun solvePart1(): Int {
        return findFirstMarker(4)
    }

    fun solvePart2(): Int {
        return findFirstMarker(14)
    }
}