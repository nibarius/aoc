package aoc2019

class Day8(input: String, private val w: Int, private val h: Int) {

    private val image = input.map { it.toString().toInt() }.chunked(w * h)

    fun solvePart1(): Int {
        return image
                .minByOrNull { layer -> layer.count { it == 0 } }!!
                .let { digits -> digits.count { it == 1 } * digits.count { it == 2 } }
    }


    fun solvePart2(): List<Int> {
        return List(w * h) { i -> image.first { it[i] != 2 }[i] }
                .also { printImage(it) }
    }

    private fun printImage(imageData: List<Int>) {
        imageData
                .map { if (it == 1) "#" else " " }
                .chunked(w)
                .map { row -> row.joinToString("") }
                .forEach { println(it)  }
    }
}