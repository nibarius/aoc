package aoc2016

class Day3(private val input: List<String>) {
    private data class Shape(val x: Int, val y: Int, val z: Int)

    private fun process(input: List<String>): List<Shape> {
        val shapes = mutableListOf<Shape>()
        input.forEach { line ->
            val entry = line.trim().split(" ").filter { it != "" }.sortedWith(compareBy { it.toInt() })
            shapes.add(Shape(entry[0].trim().toInt(),
                    entry[1].trim().toInt(),
                    entry[2].trim().toInt())
            )
        }
        return shapes
    }

    private fun process2(input: List<String>): List<Shape> {
        val shapes = mutableListOf<Shape>()
        var i = 0
        while (i < input.size) {
            val row1 = input[i++].trim().split(" ").filter { it != "" }
            val row2 = input[i++].trim().split(" ").filter { it != "" }
            val row3 = input[i++].trim().split(" ").filter { it != "" }
            for (j in 0..2) {
                val shape = listOf(row1[j], row2[j], row3[j]).sortedWith(compareBy { it.toInt() })
                shapes.add(Shape(shape[0].toInt(), shape[1].toInt(), shape[2].toInt()))
            }
        }
        return shapes
    }

    private fun solve(input: List<Shape>): Int {
          return input.count { it.x + it.y > it.z }
    }

    fun solvePart1(): Int {
        return solve(process(input))
    }

    fun solvePart2(): Int {
        return solve(process2(input))
    }
}