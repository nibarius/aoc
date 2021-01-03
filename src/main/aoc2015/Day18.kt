package aoc2015

import Grid
import Pos

class Day18(input: List<String>) {

    private val initialState = Grid.parse(input)
    private val corners = initialState.let {
        val last = it.size - 1
        listOf(Pos(0, 0),
                Pos(0, last),
                Pos(last, 0),
                Pos(last, last))
    }

    private fun animate(steps: Int, frozenCorners: Boolean = false): Grid {
        val a = initialState
        val b = initialState.copy()
        var current = a
        var next = b
        repeat(steps) {
            current.keys.forEach { pos ->
                val neighbours = current.numNeighboursWithValue(pos, '#', includeDiagonals = true)
                val on = current[pos] == '#'
                next[pos] = when {
                    frozenCorners && pos in corners -> '#'
                    !on && neighbours == 3 -> '#'
                    on && neighbours !in 2..3 -> '.'
                    else -> current[pos]
                }
            }
            current = next
            next = if (current == a) b else a
        }
        return current
    }

    fun solvePart1(steps: Int = 100): Int {
        return animate(steps).count('#')
    }

    fun solvePart2(steps: Int = 100): Int {
        corners.forEach { initialState[it] = '#' }
        return animate(steps, frozenCorners = true).count('#')
    }
}