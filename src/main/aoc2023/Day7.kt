package aoc2023

class Day7(val input: List<String>) {

    private data class Hand2(val cards: String, val bid: Int, private val useJokers: Boolean) {
        // The type of the hand, higher number means better hand
        val type: Int

        // The strength of the hand, used for comparisons when the type of two hands are the same.
        // The larger the string the stronger the hand.
        val strength = cards.map { it.cardStrength() }.joinToString("")

        init {
            val groups = cards.groupingBy { it }.eachCount()
            val jokers = if (useJokers) groups['J'] ?: 0 else 0
            val nonJokers = groups.filterKeys { !useJokers || it != 'J' }
            type = when {
                jokers == 5 -> 6 // All jokers, nonJokers is empty and max() would throw an exception.
                nonJokers.values.max() + jokers == 5 -> 6 // 5 of a kind
                nonJokers.values.max() + jokers == 4 -> 5 // 4 of a kind
                nonJokers.size == 2 && jokers in 0..1 -> 4 // full house (more than one joker would make a better hand)
                nonJokers.values.max() + jokers == 3 -> 3 // 3 of a kind
                nonJokers.size == 3 && jokers == 0 -> 2 // two pairs (jokers would make a better hand)
                nonJokers.values.max() + jokers == 2 -> 1 // one pair
                else -> 0 // high card
            }
        }

        private fun Char.cardStrength(): Char {
            val x = when (this) {
                'A' -> 14
                'K' -> 13
                'Q' -> 12
                'J' -> if (useJokers) 1 else 11
                'T' -> 10
                else -> digitToInt()
            }
            return 'a' + x
        }

        companion object {
            fun parse(rawHand: String, useJokers: Boolean): Hand2 {
                return rawHand.split(" ").let { Hand2(it.first(), it.last().toInt(), useJokers) }
            }
        }

    }

    private fun totalWinnings(useJokers: Boolean): Int {
        return input.map { Hand2.parse(it, useJokers) }
            .sortedWith(compareBy({ it.type }, { it.strength }))
            .withIndex()
            .sumOf { (index, hand) -> (index + 1) * hand.bid }
    }

    fun solvePart1(): Int {
        return totalWinnings(false)
    }

    fun solvePart2(): Int {
        return totalWinnings(true)
    }
}
