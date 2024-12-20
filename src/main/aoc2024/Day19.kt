package aoc2024

class Day19(input: List<String>) {
    private val available = input.first().split(", ")
    private val desired = input.drop(2)

    private fun isPossible(pattern: String): Boolean {
        return pattern.isEmpty() || available.any {
            pattern.startsWith(it) && isPossible(pattern.drop(it.length))
        }
    }

    fun solvePart1(): Int {
        return desired.count { isPossible(it) }
    }

    private val memory = mutableMapOf("" to 1L)
    private fun findAllCombinations(design: String): Long {
        return if (design in memory) memory[design]!!
        else available
            .filter { design.startsWith(it) }
            .sumOf { findAllCombinations(design.drop(it.length)) }
            .also { memory[design] = it }
    }

    fun solvePart2(): Long {
        return desired.sumOf { findAllCombinations(it) }
    }
}