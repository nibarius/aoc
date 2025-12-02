package aoc2025

class Day2(input: List<String>) {
    val ranges = input.first().split(",")
        .map { range ->
            range.split("-")
                .map { it.toLong() }
                .let { (start, end) ->
                    start..end
                }
        }

    /**
     * Calculate the sum of all invalid ids given a function that say if an
     * id is invalid or not.
     */
    private fun sumInvalid(isInvalid: (String) -> Boolean): Long {
        return ranges.sumOf { range ->
            range.filter { isInvalid(it.toString()) }
                .sum()
        }
    }

    fun solvePart1(): Long {
        return sumInvalid { id ->
            id.take(id.length / 2) == id.drop(id.length / 2)
        }
    }

    fun solvePart2(): Long {
        return sumInvalid { id ->
            for (len in 1..id.length / 2) {
                if (id.length % len != 0) {
                    continue
                }
                val chunked = id.chunked(len)
                if (chunked.all { it == chunked.first() }) {
                    return@sumInvalid true
                }
            }
            false
        }
    }
}