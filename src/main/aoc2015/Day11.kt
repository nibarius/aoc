package aoc2015

class Day11(private val input: String) {

    private fun String.increment(): String {
        val chars = this.toCharArray()
        var last = chars.size - 1
        var done = false
        do {
            if (chars[last] != 'z') {
                chars[last] = chars[last] + 1
                done = true
            } else {
                chars[last] = 'a'
                last--
                if (last < 0) {
                    return "a" + chars.joinToString("")
                }
            }
        } while (!done)
        return chars.joinToString("")
    }

    fun isValid(pwd: String): Boolean {
        var threeIncreasing = false
        val pairIndices = mutableListOf<Int>() // indices of where pairs are found
        for (i in pwd.indices) {
            // black listed characters
            if (listOf('i', 'l', 'o').contains(pwd[i])) {
                return false
            }

            // one increasing straight of at least three letters
            if (!threeIncreasing && i >= 2 && pwd[i - 2] == pwd[i - 1] - 1 && pwd[i - 2] == pwd[i] - 2) {
                threeIncreasing = true
            }

            // at least two different, non-overlapping pairs of letters
            if (i >= 1 && pwd[i] == pwd[i - 1] && pairIndices.lastOrNull() != i - 2) {
                pairIndices.add(i - 1)
            }
        }
        return threeIncreasing && pairIndices.size >= 2
    }

    fun solvePart1(oldPwd: String = input): String {
        var pwd = oldPwd
        do {
            pwd = pwd.increment()
        } while (!isValid(pwd))
        return pwd
    }

    fun solvePart2(): String {
        return solvePart1(solvePart1())
    }
}