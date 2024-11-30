package aoc2017

class Day2(val input: List<String>) {

    private fun findSolution(rowTransformation: (List<Int>) -> Int): Int {
        return input.sumOf { row ->
            val parts = row.split(" ", "\t").map { it.toInt() }
            rowTransformation(parts)
        }
    }

    private fun calculateChecksum(parts: List<Int>): Int {
        return parts.max() - parts.min()
    }

    private fun doDivision(parts: List<Int>): Int {
        for (i in parts.indices) {
            for (j in i + 1 ..< parts.size ) {
                val x = listOf(parts[i], parts[j]).min()
                val y = listOf(parts[i], parts[j]).max()
                if (y % x == 0) {
                    return y / x
                }
            }
        }
        error("No evenly divisible numbers found in $parts")
    }

    fun solvePart1(): Int {
        return findSolution(::calculateChecksum)
    }

    fun solvePart2(): Int {
        return findSolution(::doDivision)
    }
}