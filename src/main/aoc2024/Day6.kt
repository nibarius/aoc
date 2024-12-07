package aoc2024

import AMap
import Direction
import Pos
import Visualization
import java.awt.Color

/**
 * Represent the lab as an AMap, '#' is walls, '.' is unvisited rooms and
 * '1' to '4' is rooms that have been visited 1-4 times. If you've been through
 * the same room 4 times it must be part of a loop (at least with the input given).
 *
 * It's much faster to just keep track of the numbers 1-4 as a char rather than
 * having a map or set with positions and directions.
 */
class Day6(input: List<String>) {
    private val lab = AMap.parse(input)
    private val startPos = lab.positionOf('^').also { lab[it] = '.' }
    private val startFacing = Direction.Up

    /**
     * Walk through the map and count how many different positions are visited.
     */
    fun solvePart1(): Int {
        return numVisitedPositions(lab, startPos, startFacing)
            //.also { visualize(lab) }
    }

    /**
     * Walk through the map like in the first part, but for every step where it's
     * possible to move straight, insert a block ahead and see if it results in
     * a loop. After that move forward and repeat until leaving the lab.
     */
    fun solvePart2(): Int {
        return findAllPossibleLoops(lab)
    }

    /**
     * Turn empty spots into '1' and increase everything else by one.
     */
    private fun AMap.increaseVisit(pos: Pos) {
        when {
            this[pos] == '.' -> this[pos] = '1'
            else -> this[pos] = this[pos]!! + 1
        }
    }

    /**
     * Walk through the whole lab and return how many rooms are visited. Return -1
     * if a loop is detected, and it's not possible to leave the lab.
     */
    private fun numVisitedPositions(lab: AMap, startPos: Pos, startFacing: Direction): Int {
        var currentPos = startPos
        var currentFacing = startFacing

        while (lab.contains(currentPos)) {
            if (lab[currentPos] == '4') {
                // The 4th time you walk by the same position it must be in a loop
                return -1
            }

            lab.increaseVisit(currentPos)
            val nextPos = currentPos.move(currentFacing)
            when (lab[nextPos]) {
                '#' -> currentFacing = currentFacing.turnRight() // wall, we must turn
                else -> currentPos = nextPos
            }
        }
        return lab.values.count { it in '1'..'4' }
    }

    private fun numLoops(lab: AMap, startPos: Pos, startFacing: Direction): Int {
        return if (numVisitedPositions(lab, startPos, startFacing) == -1) 1 else 0
    }

    private fun findAllPossibleLoops(lab: AMap): Int {
        var currentPos = startPos
        var currentFacing = startFacing
        var loopsFound = 0

        while (lab.contains(currentPos)) {
            lab.increaseVisit(currentPos)
            val nextPos = currentPos.move(currentFacing)
            when (lab[nextPos]) {
                '#' -> {
                    currentFacing = currentFacing.turnRight() // wall, turn instead of moving
                    continue
                }

                '.' -> loopsFound += numLoops(
                    lab.copy().apply { this[nextPos] = '#' }, currentPos, currentFacing.turnRight()
                )
            }
            currentPos = nextPos
        }
        return loopsFound
    }

    @Suppress("unused")
    private fun visualize(map: AMap) {
        map[startPos] = '^'
        val colorMap = ColorMap.Viridis.getColorMap(('1'..'8').toList().reversed())
            .toMutableMap().apply {
                this['#'] = Color.BLACK.rgb
                this['^'] = Color.BLUE.rgb
                this['.'] = Color.WHITE.rgb
            }
        Visualization(10).drawPng(map, colorMap, "tmp.png")
    }
}