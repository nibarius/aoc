package aoc2015

class Day1(val input: String) {

    fun solvePart1(): Int {
        return input.fold(0) { total, char -> total + if (char == '(') 1 else -1 }
    }

    fun solvePart2(): Int {
        var total = 0
        input.withIndex().forEach { (index, char) ->
            if (char == '(') total++ else total--
            if (total == -1) return index + 1
        }
        return -1
    }
}