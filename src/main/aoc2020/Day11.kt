package aoc2020

import Grid

class Day11(input: List<String>) {
    private val room = Grid.parse(input)

    private fun count(neighbours: Boolean = false, visible: Boolean = false): Int {
        val a = room.copy()
        val b = room.copy()
        var current = a
        var next = b
        while (true) {
            for (pos in next.keys) {
                val numSeen = when {
                    neighbours -> current.numNeighboursWithValue(pos, '#', true)
                    visible -> current.numVisibleWithValue(pos, '#', listOf('.'), true)
                    else -> 0
                }
                next[pos] = when {
                    current[pos] == 'L' && numSeen == 0 -> '#'
                    current[pos] == '#' && numSeen >= if (neighbours) 4 else 5 -> 'L'
                    else -> current[pos]
                }
            }
            if (current == next) {
                break
            }
            current = next
            next = if (current == a) b else a
        }
        return current.count('#')
    }

    fun solvePart1(): Int {
        return count(neighbours = true)
    }

    fun solvePart2(): Int {
        return count(visible = true)
    }
}