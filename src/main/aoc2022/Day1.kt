package aoc2022

/**
 * @param input A list of elves, each entry is a newline separated string with calorie data
 * for each item the elf is carrying.
 */
class Day1(input: List<String>) {

    /**
     * A list of total calories per elf.
     */
    private val parsedInput = input.map { elf ->
        elf.split("\n").sumOf { calories -> calories.toInt() }
    }

    fun solvePart1(): Int {
        return parsedInput.maxOf { it }
    }

    fun solvePart2(): Int {
        return parsedInput.sortedDescending().take(3).sum()
    }
}