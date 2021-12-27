package aoc2021

class Day24(input: List<String>) {

    // The input has 14 chunks, one for each digit, of 18 instructions each where
    // the 6th and 16th instructions varies and the rest are the same.
    private val variantOne = input.chunked(18).map { it[5].split(" ").last().toInt() }
    private val variantTwo = input.chunked(18).map { it[15].split(" ").last().toInt() }

    /**
     * The result of running the program
     * @param first The first digit in the input that might have to be modified
     * @param second The second digit in the input that might have to be modified
     * @param amount The amount the first digit needs to be increased, or alternatively how much the second digit
     * must be decreased. If increase is zero the program completed as expected.
     */
    data class Result(val first: Int, val second: Int, val amount: Int)

    /**
     * A rewrite in Kotlin of the program given as puzzle input. For z to be 0 at the end x and w must
     * be the same every time variantOne is negative. So this function returns early if that is not
     * the case with instructions on how to modify the input in order for x == w to be true.
     *
     * The program pushes 7 values to a stack and pops 7 values. When it pops a value it compares
     * if the current digit matches the corresponding one pushed earlier with some additions based
     * on variantOne and variantTwo. Each character in the input is therefore connected to another
     * character in the input and the diff between the characters is important. So the characters
     * can have several accepted values as long as the diff between them are constant.
     */
    private fun theProgram4(input: List<Int>): Result {
        var w: Int
        var x: Int
        val stack = mutableListOf(-1 to 0) // <digit that the current value is based on> to <value>
        repeat(14) { digit ->
            w = input[digit]
            x = stack.last().second % 26
            val beforeRemoval = stack.last().first
            if (variantOne[digit] < 0) {
                stack.removeLast()
            }
            x += variantOne[digit]
            if (variantOne[digit] < 0 && x != w) {
                // For z to be 0 at the end x must equal w every time variantOne is negative,
                // if it isn't return how much the digit at 'beforeRemoval' or at 'digit'
                // needs to be adjusted for x to equal w
                return Result(beforeRemoval, digit, (w - x))
            }

            if (x != w) {
                stack.add(digit to w + variantTwo[digit])
            }
        }
        return Result(0, 0, 0)
    }


    fun solvePart1(): Long {
        // Start from all 9:s and decrease the required digit once an error is detected
        val testInput = "99999999999999".map { it.digitToInt() }.toMutableList()
        do {
            val (first, second, amount) = theProgram4(testInput)
            if (amount < 0) {
                testInput[first] += amount
            } else if (amount > 0) {
                testInput[second] -= amount
            }
        } while (amount != 0)
        return testInput.joinToString("").toLong()
    }

    fun solvePart2(): Long {
        // Start from all 1:s and increase the required digit once an error is detected
        val testInput = "11111111111111".map { it.digitToInt() }.toMutableList()
        do {
            val (first, second, amount) = theProgram4(testInput)
            if (amount < 0) {
                testInput[second] -= amount
            } else if (amount > 0) {
                testInput[first] += amount
            }
        } while (amount != 0)
        return testInput.joinToString("").toLong()
    }
}