package aoc2016

class Day20(input: List<String>) {

    // Sorted list of ranges, ranges fully contained in another range are not included
    private val blacklist = parseInput(input)

    private fun parseInput(input: List<String>): List<LongRange> {
        val ranges = mutableListOf<LongRange>()
        for (line in input) {
            val parts = line.split("-")
            ranges.add(parts[0].toLong()..parts[1].toLong())
        }
        val sorted = ranges.sortedBy { it.first }
        val ret = mutableListOf(sorted.first())
        sorted.forEach { range ->
            if (ret.last().contains(range.first) && ret.last().contains(range.last)) {
                // Range fully contained in previous range, skip it.
            } else {
                ret.add(range)
            }
        }
        return ret
    }


    private fun findFirstAllowed(): Long {
        var toTest = 0L
        for (range in blacklist) {
            if (toTest < range.start) {
                return toTest
            }
            toTest = range.endInclusive + 1
        }
        return toTest
    }

    private fun findAllAllowed(): List<LongRange> {
        val ret = mutableListOf<LongRange>()
        var toTest = 0L
        blacklist.forEach { range ->
            if (toTest < range.start) {
                ret.add(toTest until range.start)
            }
            toTest = range.endInclusive + 1
        }
        if (toTest < 4294967295) {
            ret.add(toTest..4294967295)
        }
        return ret
    }

    fun solvePart1(): Long {
        return findFirstAllowed()
    }

    fun solvePart2(): Int {
        return findAllAllowed().sumBy { it.count() }
    }
}
