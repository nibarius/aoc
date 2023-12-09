package aoc2023

class Day9(input: List<String>) {

    private val histories = input.map { it.split(" ").map { raw -> raw.toInt() } }

    private fun differences(history: List<Int>): List<List<Int>> {
        return buildList {
            add(history)
            var current = history
            do {
                val next = current.windowed(2, 1).map { (first, second) -> second - first }
                add(next)
                current = next
            } while (!next.all { it == 0 })
        }
    }

    private fun List<List<Int>>.extrapolate(): Int {
        var next = 0
        for (i in indices.reversed().drop(1)) {
            next += this[i].last()
        }
        return next
    }

    private fun List<List<Int>>.extrapolateBackwards(): Int {
        var next = 0
        for (i in indices.reversed().drop(1)) {
            next = this[i].first() - next
        }
        return next
    }

    fun solvePart1(): Int {
        return histories.map { differences(it) }.sumOf { it.extrapolate() }
    }

    fun solvePart2(): Int {
        return histories.map { differences(it) }.sumOf { it.extrapolateBackwards() }
    }
}