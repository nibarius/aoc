package aoc2019

import kotlin.math.pow

class Day24(input: List<String>) {

    // level 0 is same level, -1 or 1 is one lower or higher level
    private data class Tile(val level: Int, val index: Int)

    private val parsedInput = input.joinToString("")
            .replace('.', '0')
            .replace('#', '1')
            .map { it.toString().toInt() }
            .toIntArray()

    private lateinit var adjacent: Map<Int, List<Tile>>
    private val emptyGrid = IntArray(25) { 0 }

    private fun adjacentBugs(level: Int, index: Int, grids: Map<Int, IntArray>): Int {
        // Sum up the number of bugs on all adjacent tiles
        return (adjacent[index] ?: error("$index has no adjacent tiles")).sumBy { adjacentTile ->
            grids.getOrDefault(level + adjacentTile.level, emptyGrid)[adjacentTile.index]
        }
    }

    private fun tick(grids: Map<Int, IntArray>): Map<Int, IntArray> {
        val noEmpty = grids.filterNot { it.value.contentEquals(emptyGrid) }
        val min = noEmpty.keys.min()!! - 1
        val max = noEmpty.keys.max()!! + 1
        val nextState = mutableMapOf<Int, IntArray>()
        for (level in min..max) {
            val nextLevel = IntArray(25)
            for (index in 0..24) {
                val adjacent = adjacentBugs(level, index, grids)
                val newValue = if (grids.getOrDefault(level, emptyGrid)[index] == 1) {
                    // A bug dies unless there is exactly one bug adjacent to it
                    if (adjacent == 1) 1 else 0
                } else {
                    // An empty space becomes infested if exactly one or two bugs are adjacent to it.
                    if ((1..2).contains(adjacent)) 1 else 0
                }
                nextLevel[index] = newValue
            }
            nextState[level] = nextLevel
        }
        return nextState
    }

    private fun generateAdjacencyMapPart1(): Map<Int, List<Tile>> {
        val adjacentTiles = mutableMapOf<Int, List<Tile>>()
        for (it in 0..24) {
            val adjacent = mutableListOf<Tile>()
            // Up, down, left, right
            if (it in 5..24) adjacent.add(Tile(0, it - 5))
            if (it in 0..19) adjacent.add(Tile(0, it + 5))
            if ((it % 5) != 0) adjacent.add(Tile(0, it - 1))
            if ((it % 5) != 4) adjacent.add(Tile(0, it + 1))
            adjacentTiles[it] = adjacent
        }
        return adjacentTiles
    }

    fun solvePart1(): Int {
        adjacent = generateAdjacencyMapPart1()
        var ret = mapOf(0 to parsedInput)
        val seen = mutableSetOf<Int>()
        var diversity: Int
        while (true) {
            ret = tick(ret)
            diversity = ret[0]!!.withIndex().sumBy { 2.0.pow(it.index).toInt() * it.value }
            if (!seen.add(diversity)) {
                break
            }
        }
        return diversity
    }

    private fun generateAdjacencyMapPart2(): Map<Int, List<Tile>> {
        val adjacentTiles = mutableMapOf<Int, List<Tile>>()
        for (it in 0..24) {
            if (it == 12) {
                // Center square is actually a separate level, so it's handled
                // specially. Mark it as adjacent to no one
                adjacentTiles[it] = listOf()
                continue
            }
            val adjacent = mutableListOf<Tile>()
            // Up
            when (it) {
                in 0..4 -> adjacent.add(Tile(1, 7))
                17 -> (20..24).forEach { i -> adjacent.add(Tile(-1, i)) }
                else -> adjacent.add(Tile(0, it - 5))
            }

            // Down
            when (it) {
                in 20..24 -> adjacent.add(Tile(1, 17))
                7 -> (0..4).forEach { i -> adjacent.add(Tile(-1, i)) }
                else -> adjacent.add(Tile(0, it + 5))
            }

            // Left
            when {
                (it % 5) == 0 -> adjacent.add(Tile(1, 11))
                it == 13 -> listOf(4, 9, 14, 19, 24).forEach { i -> adjacent.add(Tile(-1, i)) }
                else -> adjacent.add(Tile(0, it - 1))
            }

            // Right
            when {
                (it % 5) == 4 -> adjacent.add(Tile(1, 13))
                it == 11 -> listOf(0, 5, 10, 15, 20).forEach { i -> adjacent.add(Tile(-1, i)) }
                else -> adjacent.add(Tile(0, it + 1))
            }
            adjacentTiles[it] = adjacent
        }
        return adjacentTiles
    }

    fun solvePart2(duration: Int): Int {
        adjacent = generateAdjacencyMapPart2()
        var grids = mapOf(0 to parsedInput)
        repeat(duration) {
            grids = tick(grids)
        }

        return grids.values.sumBy { it.sum() }
    }
}