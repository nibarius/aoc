package aoc2020

import AMap
import Pos

class Day3(input: List<String>) {
    private val forest = AMap.parse(input)
    private val width = input[0].length
    private val height = input.size

    private fun countTrees(delta: Pos): Int {
        return generateSequence(Pos(0, 0)) { Pos((it.x + delta.x) % width, it.y + delta.y) }
                .takeWhile { it.y < height }
                .sumBy { if (forest[it] == '#') 1 else 0 }
    }

    fun solvePart1(): Int {
        return countTrees(Pos(3, 1))
    }

    fun solvePart2(): Long {
        return listOf(Pos(1, 1), Pos(3, 1), Pos(5, 1), Pos(7, 1), Pos(1, 2))
                .map { countTrees(it) }
                .fold(1L) { acc, i -> acc * i }
    }
}