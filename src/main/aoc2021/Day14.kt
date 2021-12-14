package aoc2021

/**
 * Very similar solution to day 6.
 * @see Day6
 */
class Day14(input: List<String>) {

    private val template = input.first()

    // Map of conversions. Example: "NN -> C" is transformed into rules[NN]: listOf(NC, CN)
    private val rules = input.drop(2).map { it.split(" -> ") }.associate { (a, b) ->
        a to listOf("${a[0]}$b", "$b${a[1]}")
    }

    /**
     * Increase the value of the item under 'key' with 'howMuch' with a default value of 0
     */
    private fun <T> MutableMap<T, Long>.add(key: T, howMuch: Long) {
        this[key] = getOrDefault(key, 0) + howMuch
    }

    private fun solve(steps: Int): Long {
        // Initialize element pair counts based on the template
        val numElementPairs = template.zipWithNext()
            .groupingBy { (a, b) -> "$a$b" }
            .eachCount()
            .mapValues { it.value.toLong() }
            .toMutableMap()

        repeat(steps) {
            numElementPairs.toMap().forEach { (element, numElements) ->
                // The original element pair is destroyed
                numElementPairs.add(element, -numElements)

                // And two new element pairs are created instead
                rules[element]!!.forEach { newElement -> numElementPairs.add(newElement, numElements) }
            }
        }

        val numElements = mutableMapOf<Char, Long>()

        // The first character in each two string element contributes to character count
        // (the second character in the pair will be counted as the first character of the next pair)
        numElementPairs.forEach { (key, value) -> numElements.add(key.first(), value) }

        // However, this leaves out the very last character in the final polymer. But the last (and first) character
        // remains the same during the whole process, so we can just look at the initial template to see what the
        // last character is.
        numElements.add(template.last(), 1)

        return numElements.values.maxOf { it } - numElements.values.minOf { it }
    }


    fun solvePart1(): Long {
        return solve(10)
    }

    fun solvePart2(): Long {
        return solve(40)
    }
}