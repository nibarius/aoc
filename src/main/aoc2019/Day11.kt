package aoc2019

import next
import prev

class Day11(input: List<String>) {
    private data class Pos(val x: Int, val y: Int)

    private data class Robot(private val computer: Intcode, private val map: MutableMap<Pos, Int>) {
        @Suppress("unused") // All directions are used via the next/prev extension functions
        enum class Dir(private val dx: Int, private val dy: Int) {
            UP(0, -1),
            RIGHT(1, 0),
            DOWN(0, 1),
            LEFT(-1, 0);

            fun from(pos: Pos) = Pos(pos.x + dx, pos.y + dy)
        }

        private var facing = Dir.UP
        private var pos = Pos(0, 0)

        private fun isNotDoneYet() = computer.computerState != Intcode.ComputerState.Terminated

        private fun input(color: Int) {
            computer.run {
                input.add(color.toLong())
                run()
                paint(output.removeAt(0).toInt())
                move(output.removeAt(0).toInt())
            }
        }

        private fun paint(color: Int) {
            map[pos] = if (color == 0) 0 else 1
        }

        private fun move(direction: Int) {
            facing = if (direction == 1) {
                facing.next() // Turn right
            } else {
                facing.prev() // Turn left
            }
            pos = facing.from(pos)
        }

        fun runUntilDone() {
            while (isNotDoneYet()) {
                input(map.getOrDefault(pos, 0))
            }
        }
    }

    private val parsedInput = Intcode(input.map { it.toLong() })
    private val map = mutableMapOf<Pos, Int>()

    private fun Map<Pos, Int>.xRange() = keys.minBy { it.x }!!.x..keys.maxBy { it.x }!!.x
    private fun Map<Pos, Int>.yRange() = keys.minBy { it.y }!!.y..keys.maxBy { it.y }!!.y
    private fun printArea(map: Map<Pos, Int>): String {
        return (map.yRange()).joinToString("\n") { y ->
            (map.xRange()).joinToString("") { x ->
                map.getOrDefault(Pos(x, y), 0).let { if (it == 0) " " else "#" }
            }
        }
    }

    fun solvePart1(): Int {
        Robot(parsedInput, map).runUntilDone()
        return map.keys.size
    }

    fun solvePart2(): String {
        map[Pos(0, 0)] = 1
        Robot(parsedInput, map).runUntilDone()
        return printArea(map)
    }
}


