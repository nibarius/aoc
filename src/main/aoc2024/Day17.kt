package aoc2024

import kotlin.math.pow

class Day17(input: List<String>) {
    // Register A is the only register that is ever initialized to anything else than 0
    private val initialA = input[0].substringAfterLast(" ").toLong()
    private val program = input.last().substringAfterLast(" ").split(",").map { it.toLong() }

    private fun pow2(exp: Long) = 2.0.pow(exp.toInt()).toLong()

    private fun combo(operand: Long, a: Long, b: Long, c: Long) = when (operand) {
        4L -> a
        5L -> b
        6L -> c
        else -> operand
    }

    private fun runProgram(initialValue: Long = initialA) = buildList {
        var a = initialValue
        var b = 0L
        var c = 0L
        var ip = 0

        while (ip in program.indices) {
            val operand = program[ip + 1]
            when (program[ip]) {
                0L -> a /= pow2(combo(operand, a, b, c))
                1L -> b = b xor operand
                2L -> b = combo(operand, a, b, c) % 8L
                3L -> {
                    if (a != 0L) {
                        ip = operand.toInt()
                        continue
                    }
                }

                4L -> b = b xor c
                5L -> add(combo(operand, a, b, c) % 8L) // Output, add to the result
                6L -> b = a / pow2(combo(operand, a, b, c))
                7L -> c = a / pow2(combo(operand, a, b, c))
            }
            ip += 2
        }
    }

    fun solvePart1(): String {
        return runProgram().joinToString(",")
    }

    fun solvePart2(): Long? {
        return findDesiredInput(program)
    }


    /**
     * Pre-calculate the program output of the first non-zero 10 bit input parameters. Since the program never
     * processes more than 10 bits at a time we only need to look at the first 10 bits.
     *
     * A list of input in binary format to the first output that input will generate. Sorted in ascending order.
     */
    private val programOutputs =
        (1..<1024).map { it.toString(2).padStart(10, '0') to runProgram(it.toLong()).first() }

    /**
     * Recursively finds the desired input that will cause the program to generate the given desired output.
     *
     * The program processes the 10 least significant bits of the input and outputs the most significant entry
     * in the resulting list. After that the input is shifted 3 bits to the right and the process is repeated.
     * This means that with a 15 bit long input bits 5-15 is used to generate the first number in the output, bits
     * 2-12 is used to generate the second, 1-9 the third, 1-6 the fourth and 1-3 the fifth and last number.
     *
     * This function finds the required input 3 bits at a time by processing it from the most to the last significant
     * bits to find the smallest possible input first.
     *
     * The last entry in the output is created based on the first three bits in the input. The second to last entry
     * is based on the first six bits in the input. We first find the 3 bit candidates that can generate the last digit
     * in the output. Then we recursively do a DFS to find the second last digit looking at all 6 bit candidates. Since
     * the output is based on the input from the previous iteration we need to make sure that candidates all begin
     * with the same bits as in the previous step.
     *
     * When we iterate further we will have 7 bits from previous steps that must match and 3 new bits, so we're always
     * looking at 10 bits after we've moved past the initial few digits.
     *
     * @param desiredOutput The desired output that the program should generate.
     * @param soFar Holds the desired input that is being built up in binary form.
     * @param mustStartWith A binary 7 bit long string saying what the next part of the input must start with.
     */
    private fun findDesiredInput(desiredOutput: List<Long>, soFar: String = "", mustStartWith: String = "0000000"): Long? {
        if (desiredOutput.isEmpty()) return soFar.toLong(2)
        return programOutputs
            // Find all input that generates the next desired digit and that also has the desired first 7 bits.
            .filter { it.second == desiredOutput.last() && it.first.startsWith(mustStartWith) }
            .firstNotNullOfOrNull { (candidate, _) ->
                // Since the numbers are ordered in ascending order the first matching number will be the smallest.
                val lastThree = candidate.takeLast(3)
                findDesiredInput(desiredOutput.dropLast(1), soFar + lastThree, (mustStartWith + lastThree).takeLast(7))
            }
    }
}