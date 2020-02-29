package aoc2015

import AMap
import Pos

class Day18(input: List<String>) {

    private val initialState = AMap.parse(input)
    private val corners = initialState.let {
        val x = it.xRange()
        val y = it.yRange()
        listOf(Pos(x.first, y.first),
                Pos(x.first, y.last),
                Pos(x.last, y.first),
                Pos(x.last, y.last))
    }

    private fun animate(steps: Int, frozenCorners: Boolean = false): AMap {
        var current = initialState
        repeat(steps) {
            val next = current.copy()
            current.keys.forEach { pos ->
                val neighbours = current.numNeighboursWithValue(pos, '#', includeDiagonals = true)
                val on = current[pos] == '#'
                when {
                    frozenCorners && corners.contains(pos) -> next[pos] = '#'
                    !on && neighbours == 3 -> next[pos] = '#'
                    on && neighbours !in 2..3 -> next[pos] = '.'
                }
            }
            current = next
        }
        return current
    }

    fun solvePart1(steps: Int = 100): Int {
        return animate(steps).values.count { it == '#' }
    }

    fun solvePart2(steps: Int = 100): Int {
        corners.forEach { initialState[it] = '#' }
        return animate(steps, frozenCorners = true).values.count { it == '#' }
    }
}