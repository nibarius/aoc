package aoc2019

import Pos
import toDirection


class Day3(input: List<String>) {

    data class Wire(val path: MutableList<Pos> = mutableListOf()) {
        fun trace(input: List<String>): Wire {
            var x = 0
            var y = 0
            input.forEach {
                val direction = it.first().toDirection()
                val distance = it.drop(1).toInt()
                var lastPos = Pos(x, y)
                repeat(distance) {
                    direction.from(lastPos).let { currentPos ->
                        path.add(currentPos)
                        lastPos = currentPos
                    }
                }
                x = lastPos.x
                y = lastPos.y
            }
            return this
        }

        fun distanceTo(pos: Pos): Int {
            return path.indexOf(pos) + 1
        }
    }

    private val wires = input
            .map { it.split(",") }
            .map { Wire().trace(it) }

    private fun List<Wire>.intersections(): Set<Pos> {
        return first().path.intersect(last().path.toSet())
    }

    fun solvePart1(): Int {
        return wires.intersections()
                .map { it.distanceToOrigin }
                .minOrNull() ?: -1
    }

    fun solvePart2(): Int {
        return wires.intersections()
                .map { wires[0].distanceTo(it) + wires[1].distanceTo(it) }
                .minOrNull() ?: -1
    }
}