package aoc2021

class Day3(val input: List<String>) {

    private fun getOneCounts(numbers: List<String>): List<Int> {
        val len = numbers.first().length
        val oneCounts = Array(len) { 0 }
        numbers.forEach { number ->
            number.forEachIndexed { index, c ->
                if (c == '1') {
                    oneCounts[index] += 1
                }
            }
        }
        return oneCounts.toList()
    }

    fun solvePart1(): Int {
        val oneCounts = getOneCounts(input)
        val numbers = input.size
        val most = oneCounts.joinToString("") {
            if (it > numbers / 2) "1" else "0"
        }.toInt(2)

        // Inverting the binary representation of most gives fewest
        // xor:ing with only ones flips all bits.
        val mask = "1".repeat(input.first().length).toInt(2)
        val fewest = most xor mask

        return most * fewest
    }

    /**
     * Get the oxygen/co2 rating. Returns the oxygen generation
     * rating when numberToKeep is 1 and the co2 scrubbing rating
     * when numberToKeep is 0.
     */
    private fun getRating(numberToKeep: Int): Int {
        val keep = numberToKeep.toString().first()
        val discard = (1 - numberToKeep).toString().first()
        var remainingNumbers = input
        var offset = 0

        while (remainingNumbers.size > 1) {
            val ones = getOneCounts(remainingNumbers)
            // If ones are most common at the current offset, keep the 'keep' digits.
            // For oxygen keep is '1' which means keeping 1 when 1 is most common (or keeping the most common value)
            // For co2 keep is '0' which means keeping 0 when 1 is most common (or keeping the least common value)
            val toKeep = if (ones[offset] >= remainingNumbers.size - ones[offset]) keep else discard
            remainingNumbers = remainingNumbers.filter { it[offset] == toKeep }
            offset++
        }
        return remainingNumbers.first().toInt(2)
    }

    fun solvePart2(): Int {
        return getRating(0) * getRating(1)
    }
}