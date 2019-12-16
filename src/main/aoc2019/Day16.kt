package aoc2019

import kotlin.math.abs

class Day16(input: String) {
    val parsedInput = input.chunked(1).map { it.toInt() }

    private fun patternLookup(currentDigit: Int, index: Int): Int {
        // digit 0: length 4, digit 1: length 8, ...
        val patternLength = 4 * (currentDigit + 1)

        // First time the pattern is applied it's shifted by one step
        val positionInPattern = (index + 1) % patternLength

        // The pattern have four different sections, divide by current digit to
        // get a 0-3 indicating in which section the index is
        // Sections: 0, 1, 0, -1
        return when(positionInPattern / (currentDigit + 1)) {
            1 -> 1
            3 -> -1
            else -> 0
        }
    }

    private fun runPhases(list: List<Int>): String {
        val ret = list.toIntArray()
        repeat(100) {
            for (currentElement in ret.indices) {
                var theSum = 0
                // As learned in part two (see below) all digits before currentElement is 0, so those can be skipped.
                for (i in currentElement until ret.size) {
                    theSum += ret[i] * patternLookup(currentElement, i)
                }
                ret[currentElement] = abs(theSum) % 10
            }
        }
        return ret.take(8).joinToString("")
    }


    fun solvePart1(): String {
        return runPhases(parsedInput)
    }

    private fun valueStartingAt(offset: Int, list: List<Int>): String {
        /*
        for line 0, you have 0 zeros at the start of phase
        for line 1, you have 1 zero at the start
        for line offset, you have offset zeros at the start
        ==> values of digits only depends on digits after the current digit

        offset > input.size / 2 ==> first offset zeros, then rest is ones
        ==> for any digit, add it's current value with the new value of the digit to the right

        all together means, start from last digit work backwards to the offset digit
         */
        val digits = list.subList(offset, list.size).toIntArray()
        repeat(100) {
            var prev = 0
            for (i in digits.size - 1 downTo 0) {
                digits[i] = (digits[i] + prev) % 10
                prev = digits[i]
            }
        }
        return digits.take(8).joinToString("")
    }

    fun solvePart2(): String {
        val realInput = mutableListOf<Int>()
        repeat(10_000) {
            realInput.addAll(parsedInput)
        }
        val messageOffset = parsedInput.subList(0, 7).joinToString(("")).toInt()
        return valueStartingAt(messageOffset, realInput)
    }
}
