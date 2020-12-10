package aoc2020

class Day10(input: List<String>) {
    private val differences = input.map { it.toInt() }
            .toMutableList()
            .apply { add(0); add(maxOrNull()!! + 3) }
            .sorted()
            .zipWithNext { a, b -> b - a }

    fun solvePart1(): Int {
        return differences.groupingBy { it }
                .eachCount()
                .values
                .reduce(Int::times) // There are only 1 and 3 differences in the input, multiply them
    }

    fun solvePart2(): Long {
        // Different variations only happen with at least two ones in a row, count the length of all sequences of one.
        var onesInARow = 0
        val sequencesOfOnes = differences
                .map { if (it == 1) 0.also { onesInARow++ } else onesInARow.also { onesInARow = 0 } }
                .filter { it > 1 }

        // We're in luck, the input never has more than 4 ones in a row. We don't need any generic
        // formula for calculating the length, we can just count by hand how many possibilities
        // each sequence generate. Then we just multiply everything to get the total number of combinations.
        return sequencesOfOnes.map {
            when (it) {
                2 -> 2L
                3 -> 4L
                4 -> 7L
                else -> error("unsupported amount: $it")
            }
        }.reduce(Long::times)
    }
}