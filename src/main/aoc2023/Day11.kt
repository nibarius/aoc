package aoc2023

import AMap
import Pos

class Day11(input: List<String>) {
    private val universe = AMap.parse(input, listOf('.'))

    private fun expand(expansionFactor: Int): AMap {
        val expandedUniverse = AMap()
        var expandedX = 0
        var expandedY = 0
        val columnIsEmpty = universe.xRange().associateWith { x -> universe.keys.none { it.x == x } }
        for (y in universe.yRange()) {
            val rowIsEmpty = universe.keys.none { it.y == y }
            // when row is empty expand instead of processing row
            if (rowIsEmpty) {
                expandedY += expansionFactor
                continue
            }
            for (x in universe.xRange()) {
                val curr = universe[Pos(x, y)]
                if (curr != null) {
                    expandedUniverse[Pos(expandedX, expandedY)] = curr
                }
                expandedX += if (columnIsEmpty[x]!!) expansionFactor else 1
            }
            expandedX = 0
            expandedY++
        }
        return expandedUniverse
    }

    private fun pairs(keys: Set<Pos>) = buildSet {
        keys.forEach { a ->
            keys.forEach { b ->
                if (a != b) {
                    add(setOf(a, b))
                }
            }
        }
    }

    private fun calculateSum(universe: AMap) = pairs(universe.keys).sumOf { it.first().distanceTo(it.last()).toLong() }

    fun solvePart1(): Long {
        return calculateSum(expand(2))
    }

    fun solvePart2(expansionFactor: Int = 1_000_000): Long {
        return calculateSum(expand(expansionFactor))
    }
}