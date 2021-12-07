package aoc2021

import kotlin.math.abs

class Day7(input: List<String>) {
    val numbers = input.first().split(",").map { it.toInt() }

    private fun fuelCostPart1(x: Int): Int {
        return numbers.sumOf { abs(it - x) }
    }

    private fun fuelCostPart2(x: Int): Int {
        return numbers.sumOf {
            // Sum of consecutive numbers: n / 2 (first number + last number).
            val n = abs(it - x)
            (n.toFloat() / 2 * (1 + n)).toInt()
        }
    }

    private fun findCheapest(fuelCalculationMethod: (Int) -> Int): Int {
        // Fuel cost graph is V shaped. Calculate the next fuel cost as long as it's
        // getting cheaper, when the next is more expensive we've found the cheapest, so return it
        return generateSequence(0) { it + 1 }
            .map { x -> fuelCalculationMethod(x) }
            .zipWithNext()
            .first { (curr, next) -> curr < next }
            .first
    }

    fun solvePart1(): Int {
        return findCheapest(this::fuelCostPart1)
    }

    fun solvePart2(): Int {
        return findCheapest(this::fuelCostPart2)
    }
}