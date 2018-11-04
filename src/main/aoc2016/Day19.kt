package aoc2016

import org.magicwerk.brownies.collections.GapList
import java.util.*

class Day19(private val numElves: Int) {

    // Play the whole game with a normal list, too slow to solve part 1
    fun play(): Int {
        val elves = IntArray(numElves) { i -> i + 1 }.toMutableList()

        while (elves.size > 1) {
            var i = 0
            while (i < elves.size) {
                elves.removeAt((++i) % elves.size)
            }
        }
        return elves.first()
    }

    // Use a linked list as a queue. Remove elves from the start when they do their turn
    // and add them to the end of for their next turn.
    fun playOptimized(): Int {
        val elves = LinkedList<Int>()
        for (i in 1..numElves) {
            elves.add(i)
        }

        while (elves.size > 1) {
            val elfPlaying = elves.removeAt(0)
            elves.add(elfPlaying)
            elves.removeAt(0) // remove next player
        }
        return elves.first()
    }

    // Can't use a normal list, that's way to slow.
    // Most deletions happens close to each other so a gap list works well
    // https://dzone.com/articles/gaplist-lightning-fast-list
    // http://www.magicwerk.org/page-collections-overview.html
    fun playPart2(): Int {
        val elves = GapList<Int>()
        for (i in 1..numElves) {
            elves.add(i)
        }

        while (elves.size > 1) {
            var i = 0
            while (i < elves.size) {
                val toRemove = (i + elves.size / 2) % elves.size
                elves.removeAt(toRemove)
                if (toRemove > i) {
                    // Only advance if deleting after current position
                    i++
                }
            }
        }
        return elves.first()
    }

    private fun getPowReminder(i: Int): Int {
        var power = 0
        while (Math.pow(2.toDouble(), (power + 1).toDouble()) <= i) {
            power++
        }

        return i % Math.pow(2.toDouble(), power.toDouble()).toInt()
    }

    private fun getPowBaseAndReminder(i: Int): Pair<Int, Int> {
        var power = 0
        while (Math.pow(3.toDouble(), (power + 1).toDouble()) <= i) {
            power++
        }

        val base = Math.pow(3.toDouble(), power.toDouble()).toInt()
        return Pair(base, i - base)
    }

    /*
    Based on testing results for the first number of elves are:
    2^2 + 0 = 4 -> 1
    2^2 + 1 = 5 -> 3
    2^2 + 2 = 6 -> 5
    2^2 + 3 = 7 -> 7
    2^3 + 0 = 8 -> 1
    2^3 + 1 = 9 -> 3
    2^3 + 2 = 10 -> 5
    2^3 + 3 = 11 -> 7
    2^3 + 4 = 12 -> 9
    2^3 + 5 = 13 -> 11
    2^3 + 6 = 14 -> 13
    2^3 + 7 = 15 -> 15
    2^4 + 0 = 16 -> 1

    This means that winner is (number of players mod closest lower power) * 2 + 1
     */
    fun getWinner(i: Int): Int {
        return 2 * getPowReminder(i) + 1
    }

    /*
    Based on testing results for the first number of elves are:
    3^2 9 -> 9
    10 -> 1
    11 -> 2
    12 -> 3
    13 -> 4
    14 -> 5
    15 -> 6
    16 -> 7
    17 -> 8
    18 -> 9
    19 -> 11
    20 -> 13
    21 -> 15
    22 -> 17
    23 -> 19
    24 -> 21
    25 -> 23
    26 -> 25
    3^3 27 -> 27
    Which gives three cases: power with no remainder, half way to the next power and second half toward
    the next power.
    */
    fun getWinnerPart2(i: Int): Int {
        val pair = getPowBaseAndReminder(i)

        return when {
            pair.second == 0 -> pair.first
            i <= 2 * pair.first -> pair.second
            else -> 2 * pair.second - pair.first
        }
    }


    fun solvePart1(): Int {
        return getWinner(numElves)
    }

    fun solvePart2(): Int {
        return getWinnerPart2(numElves)
    }
}
