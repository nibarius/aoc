package aoc2016

class Day16(private val length: Int, private val input: String) {

    private fun String.reverseInvert(): String {
        return this.reversed().map {
            when (it) {
                '1' -> '0'
                '0' -> '1'
                else -> it
            }
        }.joinToString("")
    }

    fun dragonStep(a: String): String {
        return "${a}0${a.reverseInvert()}"
    }

    private fun dragonCurve(size: Int, input: String): String {
        return generateSequence(input) { dragonStep(it) }
                .dropWhile { it.length < size }
                .first()
                .take(size)
    }

    // Approach 1: Concatenate strings
    // Part 2 takes forever to run (aborted 2 after 20 minutes)
    fun checksum(input: String): String {
        var checksum = input
        while (checksum.length % 2 == 0) {
            var i = 2
            var newChecksum = ""
            while (i <= checksum.length) {
                val pair = checksum.substring(i - 2, i)
                newChecksum += if (pair == "11" || pair == "00") {
                    1
                } else {
                    0
                }
                i += 2
            }
            checksum = newChecksum
        }
        return checksum
    }

    // Approach 2: Convert the input to a list of chars and add to the list then join back to a string
    // Part 2 takes in total 7-10 seconds
    fun checksumOptimized(input: String): String {
        var checksum = input.toList()
        while (checksum.size % 2 == 0) {
            val checksum2 = (0 until checksum.size step 2).map {
                when (checksum[it] == checksum[it + 1]) {
                    true -> '1'
                    false -> '0'
                }
            }
            checksum = checksum2
        }
        return checksum.joinToString("")
    }

    // Approach 3: Try tail recursion instead of an iterative approach. Possibly slightly simpler code.
    // Takes about the same time as the iterative approach.
    fun checksumOptimized2(input: String) = checksumTailRec(input.toList()).joinToString("")
    private tailrec fun checksumTailRec(input: List<Char>): List<Char> {
        val checksum = (0 until input.size step 2).map { if (input[it] == input[it + 1]) '1' else '0' }
        if (checksum.size % 2 != 0) {
            return checksum
        }
        return checksumTailRec(checksum)
    }

    private fun getChecksumForDisk(length: Int, initialState: String): String {
        return checksumOptimized2(dragonCurve(length, initialState))
    }

    fun solvePart1(): String {
        return getChecksumForDisk(length, input)
    }

    fun solvePart2(): String {
        return getChecksumForDisk(length, input)
    }
}
