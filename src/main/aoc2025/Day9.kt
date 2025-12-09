package aoc2025

import Direction
import Pos
import kotlin.math.absoluteValue

class Day9(input: List<String>) {
    val tiles = input.map { coord ->
        coord.split(",").let { (x, y) ->
            Pos(x.toInt(), y.toInt())
        }
    }

    private fun Pos.directionTo(other: Pos): Direction {
        return when ((other - this).sign()) {
            Pos(1, 0) -> Direction.Right
            Pos(-1, 0) -> Direction.Left
            Pos(0, 1) -> Direction.Down
            Pos(0, -1) -> Direction.Up
            else -> error("$this and $other is not on the same line")
        }
    }

    private fun getCornerType(enter: Char, exit: Char): Char {
        return when {
            // Moving clockwise
            enter == '^' && exit == '>' -> '┌'
            enter == '>' && exit == 'v' -> '┐'
            enter == 'v' && exit == '<' -> '┘'
            enter == '<' && exit == '^' -> '└'
            // Moving counter-clockwise
            enter == '^' && exit == '<' -> '╗'
            enter == '<' && exit == 'v' -> '╔'
            enter == 'v' && exit == '>' -> '╚'
            enter == '>' && exit == '^' -> '╝'
            else -> error("$enter and $exit doesn't form a corner")
        }
    }

    // this likely doesn't work if we are moving counterclockwise in the real input
    // but that doesn't seem to happen
    private fun Char.isMovingOutFromCorner() = this in "┌┐└┘"

    val border = buildMap {
        // Pre-set the direction that we come into the first corner
        this[tiles.first()] = tiles.last().directionTo(tiles.first()).toString().first()
        (tiles + tiles.first()).zipWithNext().forEach { (a, b) ->
            val dir = a.directionTo(b)
            this[a] = getCornerType(this[a]!!, dir.toString().first())
            var curr = a.move(dir)
            while (curr != b) {
                this[curr] = dir.toString().first()
                curr = curr.move(dir)
            }
            if (curr != tiles.first()) {
                // Set the end-corners incoming direction for next iteration
                // but don't do this when the start connects with the end
                this[curr] = dir.toString().first()
            }
        }
    }


    val distances = tiles.map { current ->
        val allDistances = tiles.map { it to current.distanceTo(it) }
        val furthest = allDistances.sortedByDescending { it.second }
        current to furthest
    }

    private fun xLen(a: Pos, b: Pos): Long {
        return (a.x - b.x).absoluteValue + 1L
    }

    private fun yLen(a: Pos, b: Pos): Long {
        return (a.y - b.y).absoluteValue + 1L
    }

    fun solvePart1(): Long {
        val corners = distances.map { (key, value) ->
            val a = key
            val b = value.first().first
            Triple(key, value.first(), xLen(a, b) * yLen(a, b))
        }
        return corners.maxByOrNull { it.third }!!.third
    }

    private fun rectangleIsWithinBorders(a: Pos, b: Pos): Boolean {
        val xSteps = (b.x - a.x).absoluteValue
        val xDir = Pos(a.x, 0).directionTo(Pos(b.x, 0))
        val ySteps = (b.y - a.y).absoluteValue
        val yDir = Pos(0, a.y).directionTo(Pos(0, b.y))
        var current = a
        var currentDir = xDir
        var inside = false // initialize with false to support stepping out right away
        repeat(xSteps) {
            val next = current.move(currentDir)
            if (!inside && next in border) {
                // I was potentially outside, but directly moved into a border, safe
                inside = true
            } else if (!inside) {
                // We moved out and did not move back into a border, we're definitely outside
                return false
            } else {
                val symbol = border.getOrDefault(next, '.')
                if (symbol in "^v" || symbol.isMovingOutFromCorner()) {
                    // we're moving past the wrong border and going out
                    inside = false
                }
            }
            current = next
        }
        currentDir = yDir
        repeat(ySteps) {
            val next = current.move(currentDir)
            if (!inside && next in border) {
                // I was potentially outside, but directly moved into a border, safe
                inside = true
            } else if (!inside) {
                // We moved out and did not move back into a border, we're definitely outside
                return false
            } else {
                val symbol = border.getOrDefault(next, '.')
                if (symbol in "<>" || symbol.isMovingOutFromCorner()) {
                    // we're moving past the wrong border and going out
                    inside = false
                }
            }
            current = next
        }

        currentDir = xDir.opposite()
        repeat(xSteps) {
            val next = current.move(currentDir)
            if (!inside && next in border) {
                // I was potentially outside, but directly moved into a border, safe
                inside = true
            } else if (!inside) {
                // We moved out and did not move back into a border, we're definitely outside
                return false
            } else {
                val symbol = border.getOrDefault(next, '.')
                if (symbol in "^v" || symbol.isMovingOutFromCorner()) {
                    // we're moving past the wrong border and going out
                    inside = false
                }
            }
            current = next
        }

        currentDir = yDir.opposite()
        repeat(ySteps) {
            val next = current.move(currentDir)
            if (!inside && next in border) {
                // I was potentially outside, but directly moved into a border, safe
                inside = true
            } else if (!inside) {
                // We moved out and did not move back into a border, we're definitely outside
                return false
            } else {
                val symbol = border.getOrDefault(next, '.')
                if (symbol in "<>" || symbol.isMovingOutFromCorner()) {
                    // we're moving past the wrong border and going out
                    inside = false
                }
            }
            current = next
        }
        return true
    }

    /**
     * Idea: Draw the bounding box that the input defines into a Map<Pos,Char>
     * by walking round the edges. Keep track of whether the turns
     * are clockwise or counter-clockwise.
     *
     * Then check each rectangle by walking around the edges of them, walking
     * horizontally and crossing a vertical edge means we are leaving the
     * bounding box. Similar with horizontal edges when walking vertically.
     * Crossing a counter-clockwise corner is safe, that keeps us within
     * the bounding box. Crossing a clockwise corner causes us to leave the
     * bounding box.
     *
     * If the order of positions in the input had caused us to walk around
     * the bounding box in counter-clockwise order I guess we would have had
     * to change which corners are safe and which are not.
     *
     * This process is then repeated from the largest rectangle toward the
     * smaller ones until we finally find one that is within the bounding box.
     *
     *
     * .....┌>>>┐
     * .....^...v
     * ┌>>>>╝...v
     * ^........v
     * └<<<<<<╗.v
     * .......^.v
     * .......└<┘
     */


    fun solvePart2(): Long {
        // generates distances both ways, so double the amount of needed coordinates
        val dist = distances.flatMap { (curr, allOther) ->
            allOther.map { Triple(curr, it.first, it.second) }
        }.filter {
            // Filter out one width rectangles and the mirror pairs
            // TODO: I thought this would work, but it doesn't for some reason
            //it.first.x <= it.second.x && it.first.y != it.second.y
            true

        }.sortedByDescending { it.third }


        val ret = dist.first { rectangleIsWithinBorders(it.first, it.second) }
            .let {
                println("Best is $it")
                xLen(it.first, it.second) * yLen(it.first, it.second)
            }
        return ret
    }
}