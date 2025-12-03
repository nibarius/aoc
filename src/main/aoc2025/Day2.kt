package aoc2025


class Day2(input: List<String>) {
    val ranges = input.first().split(",")
        .map { it.split("-") }

    /**
     * Returns a list of all chunks that numbers in the range can be divided into.
     * Each entry is a pair of chunkSize to num chunks.
     * When limited chunks are true only 2 chunks are allowed
     */
    private fun getChunkSizesFor(numDigits: Int, limitedChunks: Boolean): List<Pair<Int, Int>> {
        return (1..numDigits / 2)
            .filter { !limitedChunks || numDigits % 2 == 0 && it == numDigits / 2 }
            .filter { chunkSize -> numDigits % chunkSize == 0 }
            .map { it to numDigits / it }
    }

    /**
     * Generate all invalid ids within the given range and then sum them. All
     * numbers in the range are guaranteed to have the same number of digits.
     *
     * This is done by splitting up the range in all the possible group sizes and
     * generating all repeating ids. Example:
     * 120056-570078 becomes
     * 111111, 222222, 333333, 444444, 555555
     * 121212, 131313, 141414... 565656
     * 120120, 121121, 122122... 569569
     */
    private fun generateAndSumInvalidIds(start: String, end: String, limitedChunks: Boolean): Long {
        // Use a set to avoid duplicates (for example 1.1.1.1 and 11.11)
        return buildSet {
            val chunkSizes = getChunkSizesFor(start.length, limitedChunks)
            chunkSizes.forEach { (chunkSize, numChunks) ->
                val rangeStart = start.take(chunkSize).toInt()
                val rangeEnd = end.take(chunkSize).toInt()
                (rangeStart..rangeEnd).forEach {
                    val invalidId = it.toString().repeat(numChunks)
                    // The first and last id generated may be outside the range: both
                    // 11 and 33 are outside the range 12-32. So verify that generated
                    // ids are in range before adding them. String comparison is fine
                    // since all strings are numbers of the same length
                    if (invalidId in start..end) {
                        add(invalidId.toLong())
                    }
                }
            }
        }.sum()
    }


    private fun sumInvalidInAllRanges(limitedChunks: Boolean): Long {
        return ranges.sumOf { (start, end) ->
            val startLength = start.length
            if (startLength == end.length) {
                // In the input most ranges are within the magnitude
                generateAndSumInvalidIds(start, end, limitedChunks)
            } else {
                // but a few goes on to the next one. Split those up in two ranges.
                generateAndSumInvalidIds(start, "9".repeat(startLength), limitedChunks) +
                        generateAndSumInvalidIds("1" + "0".repeat(startLength), end, limitedChunks)
            }
        }
    }

    fun solvePart1(): Long {
        return sumInvalidInAllRanges(true)
    }

    fun solvePart2(): Long {
        return sumInvalidInAllRanges(false)
    }
}