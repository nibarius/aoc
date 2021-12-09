package aoc2021

import Grid
import Pos

class Day9(input: List<String>) {
    private val rangeX = input.first().indices
    private val rangeY = input.indices

    val grid = Grid.parse(input)

    private fun findLowPoints(): List<Pos> {
        return grid.keys
            .filter { it.y in rangeY } // real input is square, but example input has y < x
            .filter { pos ->
                pos.allNeighbours()
                    .filter { it.x in rangeX && it.y in rangeY }
                    .all { grid[it] > grid[pos] } // All neighbours are larger than current ==> minimum
            }
    }

    // Return a list of all points part of the basin around the given low point
    private fun findBasin(lowPoint: Pos): Set<Pos> {
        val ret = mutableSetOf<Pos>()
        val toCheck = mutableSetOf(lowPoint)
        while (toCheck.isNotEmpty()) {
            val curr = toCheck.first()
            toCheck.remove(curr)
            ret.add(curr)
            val next = curr.allNeighbours().filter {
                it.x in rangeX && it.y in rangeY && it !in ret && grid[it] != '9'
            }
            toCheck.addAll(next)
        }
        return ret
    }

    fun solvePart1(): Int {
        return findLowPoints().sumOf { 1 + grid[it].digitToInt() }
    }

    fun solvePart2(): Int {
        return findLowPoints()
            .map { findBasin(it).size }
            .sorted()
            .takeLast(3)
            .reduce(Int::times)
    }
}