package aoc2022

import kotlin.math.pow

class Day25(input: List<String>) {
    val numbers = input.map { snafuToDecimal(it) }

    private fun Char.value(): Double = when (this) {
        '1' -> 1.0
        '2' -> 2.0
        '0' -> 0.0
        '-' -> -1.0
        '=' -> -2.0
        else -> error("Invalid digit")
    }

    private fun snafuToDecimal(snafu: String): Double {
        var ret = 0.0
        var power = 0
        for (digit in snafu.reversed()) {
            ret += digit.value() * 5.0.pow(power++)
        }
        return ret
    }


    /**
     * Converts one decimal digit to the snafu version of it. Does not include the carry, that
     * is 4 ==> 1-. This is handle separately by the caller of this function.
     * @param target The digit to convert
     * @param smaller The decimal value that the snafu digit 0 represent at the current position. So if target is 26
     *                this would be 25. In other words, the largest power of 5 that is smaller than target.
     */
    private fun convertDecimalDigitToSnafu(target: Double, smaller: Double) = when (target) {
        0.0 -> "0"
        in smaller..<smaller * 2 -> "1"
        in smaller * 2..<smaller * 3 -> "2"
        in smaller * 3..<smaller * 4 -> "="
        in smaller * 4..<smaller * 5 -> "-"
        else -> error("should never happen")
    }

    /***
     * The result of adding one to a snafu digit (carry is not included)
     */
    private fun String.plusOne() = when (this) {
        "0" -> "1"
        "1" -> "2"
        "2" -> "="
        "=" -> "-"
        "-" -> "0"
        else -> error("should not happen")
    }

    /**
     * Convert a decimal digit to a Snafu digit.
     */
    private fun decimalToSnafu(dec: Double): String {
        var ret = ""
        var power = 0
        var carry = false
        var processed = 0.0
        do {
            // Get the least significant, unprocessed digit by getting the remainder when dividing by the next digit.
            val target = (dec - processed) % 5.0.pow(power + 1)
            val result = convertDecimalDigitToSnafu(target, 5.0.pow(power++))

            // If the previous digit subtracts from this one (carry set) then this digit needs to be increased.
            val toAdd = if (carry) result.plusOne() else result

            // If this digit subtracts from the next one, we need to remember that when processing the next digit.
            carry = if (carry) toAdd in listOf("0", "-", "=") else toAdd in listOf("-", "=")

            ret = toAdd + ret
            processed += target
        } while (processed < dec)

        return if (carry) "1$ret" else ret
    }

    fun solvePart1(): String {
        return decimalToSnafu(numbers.sum())
    }
}