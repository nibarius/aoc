package aoc2025

import kotlin.math.max
import kotlin.math.min

class Day1(input: List<String>) {
    private val start = 50
    private val turns = input.map {
        if (it.startsWith("L")) {
            -it.drop(1).toInt()
        } else
            it.drop(1).toInt()
    }

    /**
     * Recursively turn the dial the given amount of steps by first turning to
     * 0 or 99, then turning one step to wrap around and finally continue with
     * the remaining steps.
     */
    private fun turn22(from: Int, amount: Int, zeroVisits: Int): Pair<Int, Int> {
        if (amount == 0) {
            return from to zeroVisits
        } else if (from == 0 && amount < 0) {
            return turn22(99, amount + 1, zeroVisits)
        } else if (from == 99 && amount > 0) {
            return turn22(0, amount - 1, zeroVisits + 1)
        }

        val turningThisTime = if (amount < 0) {
            max(amount, 0 - from) // turn at most down to zero
        } else {
            min(amount, 99 - from) // turn at most up to 99
        }
        val nextFrom = from + turningThisTime
        return turn22(
            nextFrom,
            amount - turningThisTime,
            zeroVisits + if (nextFrom == 0) 1 else 0
        )
    }

    fun solvePart1(): Int {
        var current = start
        return turns.count { turn ->
            turn22(current, turn, 0).let { (nextPos, _) ->
                current = nextPos
            }
            current == 0
        }
    }

    fun solvePart2(): Int {
        var current = start
        var zeroVisits = 0
        turns.forEach { turn ->
            turn22(current, turn, zeroVisits).let { (nextPos, zeroVisitsSoFar) ->
                current = nextPos
                zeroVisits = zeroVisitsSoFar
            }
        }
        return zeroVisits
    }
}