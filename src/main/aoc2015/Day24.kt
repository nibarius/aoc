package aoc2015

import kotlin.math.min

class Day24(input: List<String>) {
    private val parsedInput = input.map { it.toInt() }.sortedBy { -it } // Largest first

    private fun List<Int>.weight(): Int = sum()
    private fun List<Int>.qe(): Long = fold(1L) { acc, i -> i * acc }
    private var smallestFound = input.size

    /**
     * Recursively make the first group by picking the largest numbers first until the group weight
     * is at target weight. Abort if group weight is above target or if a smaller group than current
     * group size have already been found.
     */
    private fun makeGroup(group1: List<Int>, remaining: List<Int>, foundGroups: MutableList<List<Int>>, target: Int) {
        when {
            group1.weight() == target -> {
                // Found a group of correct weight
                foundGroups.add(group1)
                smallestFound = min(smallestFound, group1.size)
                return
            }
            group1.weight() > target -> return // Weight is too high, this is no good
            remaining.isEmpty() -> return // Ran out of items to pick
            group1.size >= smallestFound -> return // There are other smaller groups already, don't continue with this
        }

        val first = remaining.first()
        val rest = remaining.drop(1)
        // Either take a number
        makeGroup(group1.toMutableList().apply { add(first) }, rest, foundGroups, target)

        // or don't
        makeGroup(group1, rest, foundGroups, target)
    }


    private fun balance(target: Int): Long {
        val candidates = mutableListOf<List<Int>>()
        makeGroup(listOf(), parsedInput, candidates, target)
        return candidates.minOf { it.qe() }
    }

    fun solvePart1(): Long {
        return balance(parsedInput.sum() / 3)
    }

    fun solvePart2(): Long {
        return balance(parsedInput.sum() / 4)
    }
}