package aoc2016

class Day8(input: List<String>) {
    private data class Instruction(val action: Action, val a: Int, val b: Int) {
        fun execute(display: Array<IntArray>) {
            when (action) {
                Action.Rect -> createRect(display, a, b)
                Action.RotateRow -> rotateRow(display, a, b)
                Action.RotateColumn -> rotateColumn(display, a, b)
            }
        }

        fun createRect(display: Array<IntArray>, width: Int, height: Int) {
            for (i in 0 until height) {
                for (j in 0 until width) {
                    display[i][j] = 1
                }
            }
        }

        fun rotateRow(display: Array<IntArray>, row: Int, distance: Int) {
            val copy = display[row].copyOf()
            for (i in copy.indices) {
                display[row][(i + distance) % copy.size] = copy[i]
            }
        }

        fun Array<IntArray>.copy() = Array(size) { get(it).clone() }
        fun rotateColumn(display: Array<IntArray>, column: Int, distance: Int) {
            val copy = display.copy()
            for (i in copy.indices) {
                display[(i + distance) % copy.size][column] = copy[i][column]
            }
        }
    }
    private enum class Action {
        Rect,
        RotateRow,
        RotateColumn
    }

    private val display = Array(6) { IntArray(50) { 0 } }
    private val instructions = parseInput(input)


    private fun parseInput(input: List<String>): List<Instruction> {
        val ret = mutableListOf<Instruction>()
        input.forEach {
            when {
                it.startsWith("rect") -> {
                    val a = it.substringAfter(" ").substringBefore("x").toInt()
                    val b = it.substringAfter("x").toInt()
                    ret.add(Instruction(Action.Rect, a, b))
                }
                it.startsWith("rotate row") -> {
                    val a = it.substringAfter("y=").substringBefore(" ").toInt()
                    val b = it.substringAfter("by ").toInt()
                    ret.add(Instruction(Action.RotateRow, a, b))
                }
                else -> {
                    val a = it.substringAfter("x=").substringBefore(" ").toInt()
                    val b = it.substringAfter("by ").toInt()
                    ret.add(Instruction(Action.RotateColumn, a, b))
                }
            }
        }
        return ret
    }

    fun solvePart1(): Int {
        instructions.forEach { it.execute(display) }
        return display.sumOf { it.sum() }
    }

    fun solvePart2() {
        instructions.forEach { it.execute(display) }
        print(display)
    }

    private fun print(display: Array<IntArray>) {
        display.forEach { println(it.joinToString("")
                .replace('0', ' ')
                .replace('1', '#')) }
    }
}
