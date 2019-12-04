package aoc2019


class Day4(input: String) {
    private val range = input.take(6).toInt()..input.takeLast(6).toInt()

    // Basically two criteria to check: never decreasing and there should be
    // a certain number of repeats.
    private fun testPassword(pw: Int, repeatCondition: (Int) -> Boolean): Boolean {
        val digits = pw.toString().map { it.toString().toInt() }
        var previous = -1
        var sameInARow = 1
        val numRepeats = mutableSetOf<Int>()
        for (i in digits.indices) {
            when {
                digits[i] < previous -> return false  // digits decrease, not valid, bail early
                digits[i] == previous -> sameInARow++ // one more adjacent digit is the same
                else -> {                             // adjacent digits differ
                    numRepeats.add(sameInARow)
                    sameInARow = 1
                }
            }
            previous = digits[i]
        }
        numRepeats.add(sameInARow) // Add last group of digits

        return numRepeats.any(repeatCondition)
    }

    fun solvePart1(): Int {
        return range.count { testPassword(it) { x -> x >= 2 } }
    }

    fun solvePart2(): Int {
        return range.count { testPassword(it) { x -> x == 2 } }
    }
}