package aoc2015

import com.marcinmoskala.math.permutations

class Day13(input: List<String>) {

    private val parsedInput = parseInput(input)
    private val persons = parsedInput.map { it.key.first }.distinct().toSet()

    private fun parseInput(input: List<String>): Map<Pair<String, String>, Int> {
        return input.map {
            val parts = it.split(" ")
            val who = parts.first()
            val other = parts.last().dropLast(1)
            val gain = if (parts[2] == "gain") 1 else -1
            val amount = gain * parts[3].toInt()
            Pair(who, other) to amount
        }.toMap()
    }

    private fun totalHappiness(order: List<String>): Int {
        var sum = 0
        for (i in order.indices) {
            val prev = order[(i - 1 + order.size) % order.size]
            val self = order[i]
            val next = order[(i + 1) % order.size]
            sum += parsedInput.getOrDefault(Pair(self, prev), 0) +
                    parsedInput.getOrDefault(Pair(self, next), 0)
        }
        return sum
    }

    private fun happinessOfBestSeatingOrder(persons: Set<String>): Int {
        return persons.permutations()
                // all permutations contains a lot of duplicate seating orders since
                // the seating is cyclic and it doesn't matter where at the table you sit.
                // Remove a bunch of duplicates (but far from all) by only looking at the
                // ones where one given person is is first.
                .filter { it.first() == persons.first() }
                .map { totalHappiness(it) }
                .max()!!

        // A much better approach would be to implement Sawada's algorithm for generating
        // all unique necklaces (seating orders). But since this solution runs in less than
        // a second for both parts, this is good enough.
        // https://byorgey.wordpress.com/2010/03/12/math-combinatorics-multiset-and-sawadas-algorithm/
    }

    fun solvePart1(): Int {
        return happinessOfBestSeatingOrder(persons)
    }

    fun solvePart2(): Int {
        val withMe = persons.toMutableSet().apply { add("me") }
        return happinessOfBestSeatingOrder(withMe)
    }
}