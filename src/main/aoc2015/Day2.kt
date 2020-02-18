package aoc2015

class Day2(input: List<String>) {
    data class Box(val l: Int, val w: Int, val h: Int) {
        val area: Int
            get() = 2 * l * w + 2 * l * h + 2 * w * h
        val slack: Int
            get() = listOf(l, w, h).sorted().dropLast(1).fold(1) { total, next -> total * next }
        val wrap: Int
            get() = listOf(l, w, h).sorted().dropLast(1).fold(0) { total, next -> total + 2 * next }
        val bow: Int
            get() = w * l * h
    }

    private val boxes = input.map { line ->
        line
                .split("x")
                .map { it.toInt() }
                .let { Box(it[0], it[1], it[2]) }
    }


    fun solvePart1(): Int {
        return boxes.sumBy { it.area + it.slack }
    }

    fun solvePart2(): Int {
        return boxes.sumBy { it.wrap + it.bow }
    }
}