package aoc2021

import Grid
import Pos

class Day20(input: List<String>) {

    private val algorithm = input.first()
    private val initialGrid = Grid.parse(input.drop(2))

    private val allDeltas = buildList {
        for (y in -1..1) {
            for (x in -1..1) {
                add(Pos(x, y))
            }
        }
    }

    private fun lookupPixelFor(pos: Pos, image: Grid, default: Char): Char {
        val offset = allDeltas.map { image.getOrDefault(pos + it, default) }
            .joinToString("") { if (it == '#') "1" else "0" }
            .toInt(2)

        return algorithm[offset]
    }

    private fun enhance(read: Grid, write: Grid, default: Char, offset: Int) {
        for (y in offset until read.size - offset) {
            for (x in offset until read.size - offset) {
                write[Pos(x, y)] = lookupPixelFor(Pos(x, y), read, default)
            }
        }
    }

    // Each enhance grows the grid with one in each direction (+2 horizontally and +2 vertically each turn).
    // Create grid that has the size of the gird after all enhancements are done and put the initial grid
    // in the middle of that grid, then just increase the area that's being read from /written to each iteration.
    private fun repeatedEnhance(times: Int): Int {
        val a = Grid(initialGrid.size + 2 * times).apply { writeAllWithOffset(initialGrid, Pos(times, times)) }
        val grids = listOf(a, a.copy())
        repeat(times) {
            // Example input algorithm starts with . which means 9 blank pixels results in a blank pixel
            // Real input starts with # and ends with . which means that 9 blank pixels becomes a lit pixel
            // and 9 lit pixels becomes a blank pixel. So everything toward infinity flips color with each
            // iteration. This will not work with an algorithm that start and ends with '#', but there is no
            // such input so that doesn't matter.
            val default = if (algorithm.first() == '#' && it % 2 == 1) '#' else '.'
            enhance(grids[it % 2], grids[(it + 1) % 2], default, times - it - 1)
        }
        return grids[times % 2].count('#')
    }

    fun solvePart1(): Int {
        return repeatedEnhance(2)
    }

    fun solvePart2(): Int {
        return repeatedEnhance(50)
    }
}