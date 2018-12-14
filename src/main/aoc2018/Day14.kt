package aoc2018

class Day14(val input: String) {
    private val recipes = input.toInt()
    // Increasing size 'enough' to be able to find the solution for part 2.
    private val buffer = ByteArray(recipes * 100 + 10)
    private var elf1 = 0
    private var elf2 = 1
    private var end = 2

    init {
        buffer[0] = 3
        buffer[1] = 7
    }

    private fun createRecipe() {
        val sum = (buffer[elf1] + buffer[elf2]).toString()
        for (digit in sum) {
            buffer[end++] = digit.toString().toByte()
        }
        elf1 = (elf1 + 1 + buffer[elf1]) % end
        elf2 = (elf2 + 1 + buffer[elf2]) % end
    }

    private fun createRecipes(toCreate: Int): String {
        while (end < toCreate) {
            createRecipe()
        }
        return buffer.slice(toCreate - 10 until toCreate).joinToString("")
    }

    private fun startsWith(buffer:ByteArray, offset: Int, str: String): Boolean {
        for (i in 0 until str.length) {
            if (buffer[offset + i] != str[i].toString().toByte()) {
                return false
            }
        }
        return true
    }

    private fun endOfBufferContains(buffer: ByteArray, endPointer: Int, input: String): Int {
        if (endPointer < input.length + 1) {
            return -1
        }
        var index = endPointer - input.length - 1
        // Each recipe creation rounds can add one or two new recipes, check if the
        // target string matches any of the two strings that ends on the last and
        // second to last character in the buffer.
        repeat(2) {
            if (startsWith(buffer, index, input)) {
                return index
            }
            index++
        }
        return -1
    }

    private fun findIndexOf(input: String): Int {
        while (end < buffer.size) {
            createRecipe()
            val index = endOfBufferContains(buffer, end, input)
            if (index >= 0) {
                return index
            }
        }
        return -1
    }

    fun solvePart1(): String {
        return createRecipes(recipes + 10)
    }

    fun solvePart2(): Int {
        return findIndexOf(input)
    }
}