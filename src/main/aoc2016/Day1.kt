package aoc2016

import Direction
import Pos
import toDirection


class Day1(input: String) {
    private var currentPosition = Pos(0, 0)
    private var facing = Direction.Up
    private val directions = directions(input)
    private val visited = mutableSetOf<Pos>()

    private fun walk(directions: List<Pair<Direction, Int>>, stopOnRepeat: Boolean = false) {
        for (movement in directions) {
            facing = facing.turn(movement.first)
            repeat(movement.second) {
                currentPosition = currentPosition.move(facing)
                if (stopOnRepeat && visited.contains(currentPosition)) {
                    return
                }
                visited.add(currentPosition)
            }
        }
    }

    private fun directions(input: String): List<Pair<Direction, Int>> {
        val ret = mutableListOf<Pair<Direction, Int>>()
        for (movement in input.split(", ")) {
            val dir = movement.first().toDirection()
            val dist = movement.drop(1).toInt()
            ret.add(Pair(dir, dist))
        }
        return ret
    }

    fun solvePart1(): Int {
        walk(directions)
        return currentPosition.distanceToOrigin
    }

    fun solvePart2(): Int {
        walk(directions, true)
        return currentPosition.distanceToOrigin
    }
}