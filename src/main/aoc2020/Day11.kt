package aoc2020

import AMap

class Day11(input: List<String>) {
    private val room = AMap.parse(input)

    private fun count(neighbours: Boolean = false, visible: Boolean = false): Int {
        var current = room
        while (true) {
            val next = current.copy()
            for (pos in next.keys) {
                val numSeen = when {
                    neighbours -> current.numNeighboursWithValue(pos, '#', true)
                    visible -> current.numVisibleWithValue(pos, '#', listOf('.'),true)
                    else -> 0
                }
                when {
                    next[pos] == 'L' && numSeen == 0 -> next[pos] = '#'
                    next[pos] == '#' && numSeen >= if(neighbours) 4 else 5 -> next[pos] = 'L'
                }
            }
            if (current.toMap() == next.toMap()) {
                break
            }
            current = next
        }
        return current.values.count { it == '#' }
    }

    fun solvePart1(): Int {
        return count(neighbours = true)
    }

    fun solvePart2(): Int {
        return count(visible = true)
    }
}