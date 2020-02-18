package aoc2015

class Day5(val input: List<String>) {

    private fun String.isNice(): Boolean {
        var numVowels = 0
        var twiceInARow = false
        val blacklist = setOf("ab", "cd", "pq", "xy")
        val vowels = setOf('a', 'e', 'i', 'o', 'u')
        for (i in indices) {
            if (i > 0) {
                if (blacklist.contains(substring(i - 1, i + 1))) {
                    return false
                }
                if (!twiceInARow && this[i - 1] == this[i]) {
                    twiceInARow = true
                }
            }
            if (vowels.contains(this[i])) {
                numVowels++
            }
        }
        return twiceInARow && numVowels >= 3
    }

    private fun String.isReallyNice(): Boolean {
        var repeatWithSpace = false
        // pair to list of indices where they show up
        val pairs = mutableMapOf<String, MutableList<Int>>()
        for (i in indices) {
            if (i > 0) {
                val pair = substring(i - 1, i + 1)
                if (!pairs.containsKey(pair)) {
                    pairs[pair] = mutableListOf()
                }
                pairs[pair]!!.add(i - 1)
            }
            if (i > 1) {
                if (!repeatWithSpace && this[i - 2] == this[i]) {
                    repeatWithSpace = true
                }
            }
        }
        return repeatWithSpace && pairs.values.any {
            // three or more pairs means that at least two of them are without overlap
            // with two paris there might be an overlap, check that distance is at least 2
            it.size >= 3 || (it.size == 2 && it[1] - it[0] >= 2)
        }
    }

    fun solvePart1(): Int {
        return input.count { it.isNice() }
    }

    fun solvePart2(): Int {
        return input.count { it.isReallyNice() }
    }
}