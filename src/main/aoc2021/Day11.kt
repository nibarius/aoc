package aoc2021

import Grid
import Pos

class Day11(input: List<String>) {

    val grid = Grid.parse(input)

    /**
     * Does one step in the flashing process.
     * @return the number of flashes during the step.
     */
    private fun doOneStep(): Int {
        val toFlash = mutableSetOf<Pos>()

        // code reuse, need to do this twice below
        fun Pos.increaseEnergyLevel() {
            if (++grid[this] > '9') {
                toFlash.add(this)
            }
        }

        // First increase energy level of each octopus
        grid.keys.forEach { it.increaseEnergyLevel() }

        // Then flash all with energy larger than 10
        val alreadyFlashed = mutableSetOf<Pos>()
        while (toFlash.isNotEmpty()) {
            val flashing = toFlash.first().also { toFlash.remove(it) }
            alreadyFlashed.add(flashing)
            grid.neighboursInGrid(flashing, true)
                .filter { !alreadyFlashed.contains(it) && !toFlash.contains(it) }
                .forEach { it.increaseEnergyLevel() }
        }

        // Finally, energy level is set to 0 for all who flashed
        alreadyFlashed.forEach { grid[it] = '0' }

        return alreadyFlashed.size
    }

    fun solvePart1(): Int {
        return (1..100).sumOf { doOneStep() }
    }

    fun solvePart2(): Int {
        return generateSequence(1) { it + 1 }
            .map { it to doOneStep() }
            .first { it.second == 100 }
            .first
    }
}