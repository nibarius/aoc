package aoc2025

import AMap
import Pos

class Day4(input: List<String>) {
    val rolls = AMap.parse(input, listOf('.')).keys

    private fun removableRolls(rolls: Set<Pos>): Set<Pos> {
        return rolls.filter { roll ->
            roll.allNeighbours(includeDiagonals = true)
                .filter { it in rolls }.size < 4
        }.toSet()
    }

    fun solvePart1(): Int {
        return removableRolls(rolls).size
    }

    fun solvePart2(): Int {
        val currentRolls = rolls
        val initialNumRolls = currentRolls.size
        while (true) {
            removableRolls(currentRolls)
                .ifEmpty { return initialNumRolls - currentRolls.size }
                .let { toRemove -> currentRolls.removeAll(toRemove) }
        }
    }
}