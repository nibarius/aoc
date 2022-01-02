package aoc2019


class Day1(input: List<String>) {

    private val parsedInput = input.map { it.toInt() }

    private fun fuelNeeded(mass: Int): Int = mass / 3 - 2

    private tailrec fun fuelNeededIncludingFuel(mass: Int, soFar: Int = 0): Int {
        val extraNeeded = fuelNeeded(mass)
        return if (extraNeeded > 0) {
            fuelNeededIncludingFuel(extraNeeded, soFar + extraNeeded)
        } else {
            soFar
        }
    }

    fun solvePart1(): Int {
        return parsedInput.sumOf { fuelNeeded(it) }
    }

    fun solvePart2(): Int {
        return parsedInput.sumOf { fuelNeededIncludingFuel(it) }
    }
}