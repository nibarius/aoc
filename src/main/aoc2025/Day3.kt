package aoc2025

class Day3(input: List<String>) {
    private val banks = input.map { bank -> bank.chunked(1).map { it.toInt() } }

    private fun findLargestNumber(numDigits: Int, bank: List<Int>): Long {
        // Sort by value first, and implicitly by index second as the sort function is stable
        val sorted = bank.withIndex().sortedByDescending { it.value }
        val number = mutableListOf<Int>()
        var takenIndex = -1
        repeat(numDigits) { digit ->
            // Need to leave enough digits after the selected one to be able to select all digits
            val lastIndex = sorted.size - numDigits + digit
            sorted.first { it.index in (takenIndex + 1)..lastIndex }
                .let {
                    takenIndex = it.index
                    number.add(it.value)
                }
        }
        return number.joinToString("") { it.toString() }.toLong()
    }

    fun solvePart1(): Long {
        return banks.sumOf { bank -> findLargestNumber(2, bank) }
    }

    fun solvePart2(): Long {
        return banks.sumOf { bank -> findLargestNumber(12, bank) }
    }
}