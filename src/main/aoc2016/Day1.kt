package aoc2016

import aoc2016.Day1.Wind.*
import next
import prev
import java.lang.Math.abs



class Day1(input: String) {
    private var x = 0
    private var y = 0
    private var facing = North
    private val directions = directions(input)
    private val visited = mutableSetOf<Pair<Int, Int>>()

    enum class Wind {
        North,
        West,
        South,
        East;

        fun turn(dir: Direction) = when (dir) {
            Direction.Left -> this.next()
            Direction.Right -> this.prev()
        }
    }

    enum class Direction {
        Left,
        Right
    }

    private fun walk(directions: List<Pair<Direction, Int>>, stopOnRepeat: Boolean = false) {
        for (movement in directions) {
            facing = facing.turn(movement.first)
            repeat(movement.second) {
                when (facing) {
                    North -> y += 1
                    West -> x -= 1
                    South -> y -= 1
                    East -> x += 1
                }
                if (stopOnRepeat && visited.contains(Pair(x, y))) {
                    return
                }
                visited.add(Pair(x, y))
            }
        }
    }

    private fun directions(input: String): List<Pair<Direction, Int>> {
        val ret = mutableListOf<Pair<Direction, Int>>()
        for (movement in input.split(", ")) {
            val dir = when (movement[0]) {
                'L' -> Direction.Left
                'R' -> Direction.Right
                else -> throw(IllegalArgumentException("Invalid input: $movement"))
            }
            val dist = movement.drop(1).toInt()
            ret.add(Pair(dir, dist))
        }
        return ret
    }

    fun solvePart1(): Int {
        walk(directions)
        return abs(x) + abs(y)
    }

    fun solvePart2(): Int {
        walk(directions, true)
        return abs(x) + abs(y)
    }
}