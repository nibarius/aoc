package aoc2016

class Day15(input: List<String>) {

    private data class Disc(val id: Int, val size: Int, val start: Int) {
        // The position the disc will have when the ball hits if dropped
        // at the given time
        fun positionAt(time: Int) = (id + start + time) % size
    }
    private val discs = parseInput(input)

    private fun parseInput(input: List<String>): MutableList<Disc> {
        val ret = mutableListOf<Disc>()
        for (disc in input) {
            val parts = disc.split(" ")
            ret.add(Disc(
                    parts[1].drop(1).toInt(),
                    parts[3].toInt(),
                    parts.last().dropLast(1).toInt()
            ))
        }
        return ret
    }

    private fun findTimeWhenAllAreAligned(): Int {
        var time = 0
        while (true) {
            if (discs.all { it.positionAt(time) == 0 }) {
                return time
            }
            time++
        }
    }

    fun solvePart1(): Int {
        return findTimeWhenAllAreAligned()
    }

    fun solvePart2(): Int {
        discs.add(Disc(discs.size + 1, 11, 0))
        return findTimeWhenAllAreAligned()
    }
}
