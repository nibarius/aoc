package aoc2022

import AMap
import Direction
import Pos
import kotlin.math.sign

class Day9(input: List<String>) {

    private val moves = input.map { line ->
        val (dir, amount) = line.split(" ")
        Direction.fromChar(dir.first()) to amount.toInt()
    }

    private fun simulate(size: Int): Int {
        val parts = MutableList(size) { Pos(0, 0) }
        val tailVisits = AMap().apply { this[Pos(0, 0)] = 's' }
        for ((dir, steps) in moves) {
            repeat(steps) {
                parts[0] = parts[0].move(dir) // Move head
                for (i in 1 until parts.size) {
                    // Move all others that need moving
                    parts[i] = getNewPartPosition(parts[i], parts[i - 1])
                }
                tailVisits[parts.last()] = '#'
            }
        }
        return tailVisits.keys.size
    }

    private fun getNewPartPosition(tail: Pos, head: Pos): Pos {
        if (head == tail || tail in head.allNeighbours(true)) {
            // head and tail touching, tail does not need to move
            return tail
        }
        // Move one step toward the head in both x and y coordinates
        return Pos(tail.x + (head.x - tail.x).sign, tail.y + (head.y - tail.y).sign)
    }

    fun solvePart1(): Int {
        return simulate(2)
    }

    fun solvePart2(): Int {
        return simulate(10)
    }
}