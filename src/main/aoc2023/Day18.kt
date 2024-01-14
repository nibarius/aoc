package aoc2023

import AMap
import Direction
import Pos
import Visualization
import size
import toDirection
import kotlin.math.max
import kotlin.math.min

class Day18(input: List<String>) {
    private val digPlan = input.map {
        DigInstruction(
            it.first().toDirection(),
            it.drop(2).substringBefore(" ").toInt(),
            it.substringAfter("(").dropLast(1)
        )
    }
    private val digPlan2 = digPlan.map {
        val raw = it.color.drop(1)
        val distance = raw.take(5).toInt(16)
        val direction = when(raw.last()) {
            '0' -> Direction.Right
            '1' -> Direction.Down
            '2' -> Direction.Left
            '3' -> Direction.Up
            else -> error("invalid direction")
        }
        DigInstruction(direction, distance, "")
    }

    data class DigInstruction(val direction: Direction, val distance: Int, val color: String)
    data class DigInstruction2(val direction: Direction, val distance: Int, val c: String)
    data class VerticalLine(val x: Int, val yRange: IntRange)

    private fun findVerticalLines(instructions: List<DigInstruction>): List<VerticalLine> {
        var current = Pos(0,0)
        return instructions.mapNotNull { digInstruction ->
            val next = current.move(digInstruction.direction, digInstruction.distance)
            val ret = if (digInstruction.direction.isVertical()) {
                VerticalLine(current.x, min(current.y, next.y)..max(current.y, next.y))
            }
            else {
                null
            }
            current = next
            ret
        }
    }

    private fun positionOnLine(y: Int, line: IntRange): Int {
        return when (y) {
            line.first -> 0
            line.last -> 1
            else -> 2
        }
    }
    private fun thisSegmentIsInside(previousIsInside: List<Boolean>, y: Int, a: VerticalLine, b: VerticalLine): Boolean {
        val at = positionOnLine(y, a.yRange)
        val bt = positionOnLine(y, b.yRange)
        return when {
            previousIsInside.size == 1 -> true // Passing the first edge means moving in
            at == 2 -> !previousIsInside.last() // always toggle when left edge is in the middle
            at == 0 && bt == 0 -> !previousIsInside.last() // top line always toggles
            at == 1 && bt == 1 -> !previousIsInside.last() // bottom line always toggles
            at == 1 && bt == 0 -> true // stairs are always on the border, so always inside
            at == 0 && bt == 1 -> true // stairs never toggle
            else -> false
        }
    }


    private fun scanHorizontalLine(y: Int, lines: List<VerticalLine>, debugMap: AMap?): Long {
        var prev: Char? = null //null whenever outside the area
        var prevX: Int? = null
        var sum = 0L

        //new approach variables
        var needToClose: Set<Char>? = null //null when outside
        var onBorder = false // If we're currently on the border/not completely within
        var doubleCounting = 0

        for (line in lines){
            if (prevX != null) {
                // We're inside, count the number of tiles
                sum += (prevX..line.x).size()

                if (debugMap != null) {
                    for (x in prevX..line.x) {
                        if (!debugMap.contains(Pos(x, y))) {
                            debugMap[Pos(x, y)] = '1'
                        } else {
                            debugMap[Pos(x, y)] = debugMap[Pos(x, y)]!! + 1
                        }
                    }
                }
            }


            // Map out all intersections
            val posOnLine = positionOnLine(y, line.yRange)
            val next = when {
                posOnLine == 2 -> '|'
                prev == null && posOnLine == 0 -> 'F'
                prev == null && posOnLine == 1 -> 'L'
                prev == '|' && posOnLine == 0 -> 'F'
                prev == '|' && posOnLine == 1 -> 'L'
                prev == 'F' && posOnLine == 1 -> 'J'
                prev == 'F' && posOnLine == 0 -> '7'
                prev == 'L' && posOnLine == 1 -> 'J'
                prev == 'L' && posOnLine == 0 -> '7'
                prev == 'J' && posOnLine == 1 -> 'L'
                prev == 'J' && posOnLine == 0 -> 'F'
                prev == '7' && posOnLine == 1 -> 'L'
                prev == '7' && posOnLine == 0 -> 'F'
                else -> error("unkonwn state: prev: $prev, posOnLine: $posOnLine")
            }

            //New approach
            if (needToClose == null) {
                // we're now moving inside
                needToClose = when (next) {
                    '|' -> setOf('|')
                    'F' -> setOf('|', '7')
                    'L' -> setOf('|', 'J')
                    else -> error("unreachable")
                }
                onBorder = needToClose.size == 2
            } else if (next in needToClose){
                // we're now moving outside
                needToClose = null
                onBorder = false
            } else {
                // we're moving past a corner that keeps us inside
                doubleCounting++
                onBorder = !onBorder
                if (onBorder) {
                    // we just moved into the border again, we must update what may close the area
                    // as we're on the inside it's different from when we just stepped inside
                    needToClose = when (next) {
                        'F' -> setOf('|', 'J')
                        'L' -> setOf('|', '7')
                        else -> error("unreachable")
                    }
                }
            }

            if (needToClose == null) {
                // We're moving outside now
                prev = null
                prevX = null
            } else {
                prev = next
                prevX = line.x
            }
        }
        return sum - doubleCounting
    }

    private fun countArea(lines: List<VerticalLine>, debugMap: AMap? = null): Long {
        var area = 0L
        var y = lines.minBy { it.yRange.first }.yRange.first
        val max = lines.maxBy { it.yRange.last }.yRange.last
        val allInterestingYs = lines.flatMap { setOf(it.yRange.first, it.yRange.last) }.toSet()
        while (y <= max) {
            val linesOnThisY = lines.filter { y in it.yRange }.sortedBy{ it.x }
            val thisLine = scanHorizontalLine(y, linesOnThisY, debugMap)
            val nextY = if (y in allInterestingYs) y + 1 else allInterestingYs.filter { it > y }.min()
            val numLines = nextY - y
            area += thisLine*numLines
            y = nextY
        }

        if (debugMap != null) {
            val colors = ColorMap.Inferno.getColorMap(('1'..'9').toList().reversed())
            Visualization().drawPng(debugMap, colors)
        }
        return area
    }


    private fun drawMap(): AMap {
        val ret = AMap()
        var current = Pos(0,0)
        val colors = mutableMapOf(1 to "#ffffff")
        val trace = mutableMapOf<Pos, String>()
        var colorOffset = 1
        ret[current] = Char(colorOffset)
        ret[current] = '*'//Char(colorOffset)
        digPlan.forEach { digInstruction ->
            colors[colorOffset++] = digInstruction.color
            repeat(digInstruction.distance) {
                current = current.move(digInstruction.direction)
                //ret[current] = Char(colorOffset) // todo: restore colors
                ret[current] = '1'
            }
        }
        return ret
    }

    fun solvePart1(): Long {
        //val map = drawMap()
        val x = findVerticalLines(digPlan)
        return countArea(x, null)
    }

    fun solvePart2(): Long {
        //return -1
        val x = findVerticalLines(digPlan2)
        return countArea(x)
    }
}