package aoc2021

import Grid
import Pos

class Day25(input: List<String>) {
    val grid = Grid.parse(input)
    val w = input.first().length
    val h = input.size

    private fun getDestination(pos: Pos, type: Char) = when (type) {
        '>' -> Pos((pos.x + 1) % w, pos.y)
        else -> Pos(pos.x, (pos.y + 1) % h)
    }

    private fun canMove(pos: Pos, type: Char, gridToUse: Grid): Boolean {
        return gridToUse[pos] == type && gridToUse[getDestination(pos, type)] == '.'
    }

    private fun move(type: Char): Boolean {
        var moved = false
        val copy = grid.copy()
        grid.keys.forEach {
            if (canMove(it, type, copy)) {
                val destination = getDestination(it, type)
                grid[destination] = type
                grid[it] = '.'
                moved = true
            }
        }
        return moved
    }

    fun solvePart1(): Int {
        return generateSequence(1) { it + 1 }
            .first { listOf('>', 'v').map { move(it) }.all { anyMoved -> !anyMoved } }
    }
}