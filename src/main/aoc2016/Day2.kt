package aoc2016

class Day2(val input: List<String>) {
    private var currX = -1
    private var currY = -1

    private fun moveIfPossible(dialPad: Map<Pair<Int, Int>, Char>, x: Int, y: Int) {
        if (dialPad.contains(Pair(x, y))) {
            currX = x
            currY = y
        }
    }

    private fun findNextDigit(dialPad: Map<Pair<Int, Int>, Char>, steps: String): Char {
        steps.forEach {
            when (it) {
                'U' -> moveIfPossible(dialPad, currX, currY - 1)
                'L' -> moveIfPossible(dialPad, currX - 1, currY)
                'D' -> moveIfPossible(dialPad, currX, currY + 1)
                'R' -> moveIfPossible(dialPad, currX + 1, currY)
                else -> throw(IllegalArgumentException("invalid input: $steps"))
            }
        }
        return dialPad.getValue(Pair(currX, currY))
    }

    private fun solve(dialPad: Map<Pair<Int, Int>, Char>): String {
        dialPad.entries.filter { it.value == '5' }.forEach {
            currX = it.key.first
            currY = it.key.second
        }
        var res = ""
        input.forEach {
            res += findNextDigit(dialPad, it)
        }
        return res
    }

    fun solvePart1(): String {
        val dialPad = mapOf(
                Pair(1, 1) to '1', Pair(2, 1) to '2', Pair(3, 1) to '3',
                Pair(1, 2) to '4', Pair(2, 2) to '5', Pair(3, 2) to '6',
                Pair(1, 3) to '7', Pair(2, 3) to '8', Pair(3, 3) to '9')
        return solve(dialPad)
    }

    fun solvePart2(): String {
        val dialPad = mapOf(
                Pair(3, 1) to '1',
                Pair(2, 2) to '2', Pair(3, 2) to '3', Pair(4, 2) to '4',
                Pair(1, 3) to '5', Pair(2, 3) to '6', Pair(3, 3) to '7', Pair(4, 3) to '8', Pair(5, 3) to '9',
                Pair(2, 4) to 'A', Pair(3, 4) to 'B', Pair(4, 4) to 'C',
                Pair(3, 5) to 'D')
        return solve(dialPad)
    }
}