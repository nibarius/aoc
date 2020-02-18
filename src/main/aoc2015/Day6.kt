package aoc2015

import Pos
import kotlin.math.max

class Day6(input: List<String>) {

    sealed class Instruction(val x: IntRange, val y: IntRange) {
        class Toggle(x: IntRange, y: IntRange) : Instruction(x, y) {
            override fun op(v: Int, part1: Boolean) = if (part1) (v + 1) % 2 else v + 2
        }

        class TurnOn(x: IntRange, y: IntRange) : Instruction(x, y) {
            override fun op(v: Int, part1: Boolean) = if (part1) 1 else v + 1
        }

        class TurnOff(x: IntRange, y: IntRange) : Instruction(x, y) {
            override fun op(v: Int, part1: Boolean) = if (part1) 0 else max(0, v - 1)
        }

        abstract fun op(v: Int, part1: Boolean): Int
    }

    private val lights = input.map { line ->
        val x1 = line.substringBefore(",").substringAfterLast(" ").toInt()
        val y1 = line.substringAfter(",").substringBefore(" ").toInt()
        val x2 = line.substringBeforeLast(",").substringAfterLast(" ").toInt()
        val y2 = line.substringAfterLast(",").substringBefore(" ").toInt()
        when {
            (line.startsWith("turn on")) -> Instruction.TurnOn(x1..x2, y1..y2)
            (line.startsWith("turn off")) -> Instruction.TurnOff(x1..x2, y1..y2)
            (line.startsWith("toggle")) -> Instruction.Toggle(x1..x2, y1..y2)
            else -> throw RuntimeException("Unknown instruction type: $line")
        }
    }

    private fun runInstructions(part1: Boolean): Int {
        val grid = mutableMapOf<Pos, Int>()
        lights.forEach { instruction ->
            for (y in instruction.y) {
                for (x in instruction.x) {
                    val pos = Pos(x, y)
                    grid[pos] = instruction.op(grid.getOrDefault(pos, 0), part1)
                }
            }
        }
        return grid.values.sum()
    }


    fun solvePart1(): Int {
        return runInstructions(part1 = true)
    }

    fun solvePart2(): Int {
        return runInstructions(part1 = false)
    }
}