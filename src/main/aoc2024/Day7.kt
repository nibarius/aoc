package aoc2024

class Day7(val input: List<String>) {
    private val equations = input.map { line ->
        val result = line.substringBefore(":").toLong()
        val numbers = line.substringAfter(": ").split(" ").map { it.toLong() }
        result to numbers
    }

    private fun isValid(desired: Long, soFar: Long, remaining: List<Long>, ops: List<(Long, Long) -> Long>): Boolean {
        return when {
            remaining.isEmpty() && soFar == desired -> true
            remaining.isEmpty() || soFar > desired -> false
            else -> ops.any { op ->
                isValid(desired, op(soFar, remaining.first()), remaining.subList(1), ops)
            }
        }
    }

    private fun List<Long>.subList(from: Int): List<Long> {
        return subList(from, size)
    }

    private fun sumValid(ops: List<(Long, Long) -> Long>): Long {
        return equations
            .filter { isValid(it.first, it.second.first(), it.second.subList(1), ops) }
            .sumOf { it.first }
    }

    fun solvePart1(): Long {
        val ops = listOf<(Long, Long) -> Long>(Long::times, Long::plus)
        return sumValid(ops)
    }

    fun solvePart2(): Long {
        val ops = listOf<(Long, Long) -> Long>(Long::times, Long::plus, { a, b -> "$a$b".toLong() })
        return sumValid(ops)
    }
}