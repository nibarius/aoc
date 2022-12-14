package aoc2022

import AMap
import Direction
import Pos
import kotlin.math.max
import kotlin.math.min

class Day14(input: List<String>) {

    val map = AMap().apply {
        input.forEach { line ->
            line.split(" -> ").windowed(2).forEach { (a, b) ->
                val (x1, y1) = a.split(",").map { it.toInt() }
                val (x2, y2) = b.split(",").map { it.toInt() }
                (min(x1, x2)..max(x1, x2)).forEach { x ->
                    (min(y1, y2)..max(y1, y2)).forEach { y ->
                        this[Pos(x, y)] = '#'
                    }
                }
            }
        }
    }

    private tailrec fun findFirstBelow(pos: Pos): Pos? {
        return when {
            pos.x !in map.xRange() || pos.y !in 0..map.yRange().last -> null // will fall into the abyss
            map[pos] != null -> pos
            else -> findFirstBelow(pos.move(Direction.Down))
        }
    }

    private fun sandLandsOn(start: Pos): Pos? {
        val center = findFirstBelow(start) ?: return null
        val left = findFirstBelow(center.move(Direction.Left))
        val right = findFirstBelow(center.move(Direction.Right))
        return when {
            center.y == left?.y && center.y == right?.y -> center.move(Direction.Up)
            center.y != left?.y -> left?.let { sandLandsOn(it) }
            else -> right?.let { sandLandsOn(it) }
        }
    }

    private fun simulate() {
        val sandOrigin = Pos(500, 0)
        var next = sandLandsOn(sandOrigin)
        while (next != null) {
            map[next] = 'o'
            next = sandLandsOn(sandOrigin)
        }
    }

    // If there would be no rock, this is the amount of sand that would fall until the whole space is filled
    private fun sandPyramidSize(): Int {
        return (0..map.yRange().last).sumOf { 1 + it * 2 }
    }

    // A space that have three rock spaces above it can't have sand on it, so mark it as rocks as well
    private fun blockOutSpacesWhereSandCantLand() {
        map.yRange().forEach { y ->
            map.xRange().forEach { x ->
                val curr = Pos(x, y)
                val left = curr.move(Direction.Left)
                val right = curr.move(Direction.Right)
                val down = curr.move(Direction.Down)
                if (map[curr] == '#' && map[left] == '#' && map[right] == '#' && down.y in map.yRange()) {
                    map[down] = '#'
                }
            }
        }
    }

    fun solvePart1(): Int { // takes 10 seconds to finish, need to find a faster solution
        simulate()
        return map.values.count { it == 'o' }
    }

    fun solvePart2(): Int { // takes about 100 ms
        map[Pos(500, map.yRange().last + 1)] = '.' // add one empty space at the bottom that can be filled by sand
        blockOutSpacesWhereSandCantLand()
        val pyramidSize = sandPyramidSize()
        return pyramidSize - map.values.count { it == '#' }
    }
}