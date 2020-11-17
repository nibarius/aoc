package aoc2016

import md5

class Day14(private val salt: String) {

    private fun String.threeInARowChar(): Char? {
        if (length < 3) {
            return null
        }
        for (i in 2 until length) {
            if (this[i - 2] == this[i - 1] && this[i - 1] == this[i]) {
                return this[i]
            }
        }
        return null
    }

    private fun String.allFiveInARowChars(): List<Char> {
        val ret = mutableListOf<Char>()
        if (length < 5) {
            return ret
        }
        var i = 4
        while (i < length) {
            if (this[i - 4] == this[i - 3] && this[i - 3] == this[i - 2] && this[i - 2] == this[i - 1] && this[i - 1] == this[i]) {
                if (!ret.contains(this[i])) {
                    ret.add(this[i])
                }
                i += 5
            } else {
                i++
            }
        }
        return ret
    }

    private data class InterestingIndex(val hash: String, val three: Char, val five: List<Char>)
    private val interestingIndices = mutableMapOf<Int, InterestingIndex>()
    private val keys = mutableListOf<Pair<Int, String>>()

    private fun stretch(s: String, times: Int): String {
        var ret = s
        repeat(times) {ret = ret.md5()}
        return ret
    }

    private fun doHashes(stretchAmount: Int): Int {
        var index = 0
        val toCheck = mutableListOf<IntRange>()

        while (keys.size < 64) {
            // Generate hashes
            val hash = stretch("$salt$index", stretchAmount)
            val triplet = hash.threeInARowChar()
            if (triplet != null) {
                interestingIndices[index] = InterestingIndex(hash, triplet, hash.allFiveInARowChars())
                toCheck.add(index + 1..index + 1000)
            }

            // Check for keys when we have 1000 hashes generated after a hash with three characters in a row
            if (toCheck.isNotEmpty() && toCheck[0].last == index) {
                val range = toCheck.removeAt(0)
                // The key candidate is on the index just before the 1000 item long range
                val keyCandidateIndex = range.first - 1
                val charToSearchFor = interestingIndices[keyCandidateIndex]!!.three
                val isKey = interestingIndices
                        .filterKeys { it >= range.first && it <= range.last }
                        .filter { it.value.five.contains(charToSearchFor) }
                        .isNotEmpty()
                if (isKey) {
                    keys.add(Pair(keyCandidateIndex, interestingIndices[keyCandidateIndex]!!.hash))
                }
            }

            index++
        }
        return keys.last().first
    }

    fun solvePart1(): Int {
        return doHashes(1)
    }

    fun solvePart2(): Int {
        return doHashes(2017)
    }
}
