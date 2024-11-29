package aoc2017

class Day1(val input: String) {

    private fun solveCaptcha(distance: Int) = input.mapIndexed { index, c ->
        if (c == input[(index + distance) % input.length]) c.digitToInt() else 0
    }.sum()

    fun solvePart1(): Int {
        return solveCaptcha(1)
    }

    fun solvePart2(): Int {
        return solveCaptcha(input.length / 2)
    }
}