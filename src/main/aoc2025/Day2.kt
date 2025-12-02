package aoc2025

import kotlin.math.pow

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
     * Calculate 10 to the power of x and return it as a long.
     */
    private fun tenPow(x: Int): Long {
        return 10.toDouble().pow(x).toLong()
    }

    /**
     * Returns a list of all chunks that numbers in the range can be divided into.
     * Each entry is a pair of chunkSize to num chunks.
     * When limited chunks are true only 2 chunks are allowed
     */
    private fun getChunkSizesFor(range: LongRange, limitedChunks: Boolean): List<Pair<Int, Int>> {
        val numDigits = range.first.toString().length
        return (1..numDigits / 2)
            .filter { !limitedChunks || numDigits % 2 == 0 && it == numDigits / 2 }
            .filter { chunkSize -> numDigits % chunkSize == 0 }
            .map { it to numDigits / it }
    }

    /**
     * Generate all invalid ids within the given range. All numbers in the range is
     * guaranteed to have the same number of digits. In addition to generating all
     * invalid ids in the range it may also generate some ids outside the range to
     * make the code simpler.
     *
     * This function looks at the first digit of the first number in the range and
     * the first digit of the last number in the range to decide the range to generate
     * invalid ids in. 123456 - 654321 will generate all invalid ids in the range
     * 100000 - 700000.
     *
     * For small ranges with high numbers it generates a large amount of ids outside
     * the range, but it makes the implementation fairly simple. Compared to testing
     * all numbers in the range it's still much faster with my puzzle input. Around
     * 13x faster for part 2 and 10x faster for part 1. The example input becomes around
     * 5x slower though, but overall runtime for all inputs is 7-8x faster than testing
     * all numbers.
     */
    private fun generateInvalidIds(range: LongRange, limitedChunks: Boolean): Set<Long> {
        val chunkList = getChunkSizesFor(range, limitedChunks)
        val firstDigit = range.first.toString().take(1).toInt()
        val firstDigitEnd = range.last.toString().take(1).toInt()
        val ret = mutableSetOf<Long>()
        chunkList.forEach { (chunkSize, chunks) ->
            val rangeStart = firstDigit * tenPow(chunkSize - 1)
            val rangeEnd = (firstDigitEnd + 1) * tenPow(chunkSize - 1)
            (rangeStart..<rangeEnd).forEach {
                ret.add(it.toString().repeat(chunks).toLong())
            }
        }
        return ret
    }

    /**
     * Sum up all invalid ids in the given range. Do this by generating all invalid ids
     * in the range, including some slightly outside to keep the code fairly simple.
     * Then filter out the invalid ids outside the range before summing it up.
     */
    private fun invalidInRange(range: LongRange, limitedChunks: Boolean): Long {
        val startLength = range.first.toString().length
        val endLength = range.last.toString().length
        val candidates = mutableSetOf<Long>()

        if (startLength == endLength) {
            // In the input most ranges are within the same power of ten
            candidates.addAll(generateInvalidIds(range, limitedChunks))
        } else {
            // but a few goes on to the next one. Split those up in two ranges.
            val breakpoint = tenPow(startLength)
            candidates.addAll(generateInvalidIds(range.first..<breakpoint, limitedChunks))
            candidates.addAll(generateInvalidIds(breakpoint..range.last, limitedChunks))
        }

        return candidates.filter { it in range }.sum()
    }

    fun solvePart1(): Long {
        return ranges.sumOf { range ->
            invalidInRange(range, true)
        }
    }

    fun solvePart2(): Long {
        return ranges.sumOf { range ->
            invalidInRange(range, false)
        }
    }
}