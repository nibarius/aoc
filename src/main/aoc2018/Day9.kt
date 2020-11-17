package aoc2018

import org.magicwerk.brownies.collections.GapList

class Day9(input: String) {
    private val players: Int
    private val marbles: Int

    init {
        // to parse: 10 players; last marble is worth 1618 points
        val parts = input.split(" ")
        players = parts.first().toInt()
        marbles = parts.dropLast(1).last().toInt()
    }

    private fun simulateGame(numMarbles: Int = marbles): Long {
        val circle = GapList<Int>(numMarbles)
        circle.add(0)
        var currentPlayer = 1
        var currentMarble = 0
        val scores = List(players) { Pair(it, 0.toLong()) }.toMap().toMutableMap()

        for (insertMarble in 1..numMarbles) {
            if (insertMarble % 23 == 0) {
                currentMarble = (currentMarble - 7 + circle.size) % circle.size
                scores[currentPlayer] = scores[currentPlayer]!! + insertMarble + circle[currentMarble]
                circle.remove(currentMarble, 1)
            } else {
                currentMarble = (currentMarble + 2) % circle.size
                circle.add(currentMarble, insertMarble)
            }
            currentPlayer = (currentPlayer + 1) % players
        }
        return scores.values.maxOrNull()!!
    }

    fun solvePart1(): Long {
        return simulateGame()
    }

    fun solvePart2(): Long {
        return simulateGame(marbles * 100)
    }
}