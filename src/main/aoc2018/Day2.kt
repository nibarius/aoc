package aoc2018

class Day2(private val input: List<String>) {

    private fun calculateChecksum(words: List<String>): Int {
        var twos = 0
        var threes = 0
        for (word in words) {
            val charCount = word.groupingBy { it }.eachCount()
            with(charCount.values) {
                if (contains(2)) twos++
                if (contains(3)) threes++
            }
        }
        return twos * threes
    }

    private fun idsAreClose(id1: String, id2: String): Boolean {
        return id1.zip(id2).count { it.first != it.second } == 1
    }

    private fun sameCharacters(id1: String, id2: String): String {
        return id1.filterIndexed { index, c -> c == id2[index] }
    }

    private fun findBoxes(ids: List<String>): String {
        for (i in ids.indices) {
            for (j in i + 1 until ids.size) {
                if (idsAreClose(ids[i], ids[j])) {
                    return sameCharacters(ids[i], ids[j])
                }
            }
        }
        return ""
    }

    fun solvePart1(): Int {
        return calculateChecksum(input)
    }

    fun solvePart2(): String {
        return findBoxes(input)
    }
}