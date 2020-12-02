package aoc2020

class Day2(input: List<String>) {
    private data class Password(val first: Int, val last: Int, val c: Char, val pwd: String) {
        fun matchesOldRule() = pwd.count { it == c } in first..last
        fun matchesNewRule() = (pwd[first - 1] == c) xor (pwd[last - 1] == c)
    }

    // Example: 12-16 s: snhsmxszbsszzclp
    private val pattern = "([0-9]+)-([0-9]+) ([a-z]): ([a-z]+)".toRegex()

    private val passwords = input.map { line ->
        val (first, last, c, pwd) = pattern.find(line)!!.destructured
        Password(first.toInt(), last.toInt(), c[0], pwd)
    }

    fun solvePart1(): Int {
        return passwords.count { it.matchesOldRule() }
    }

    fun solvePart2(): Int {
        return passwords.count { it.matchesNewRule() }
    }
}