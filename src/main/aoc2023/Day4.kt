package aoc2023

import kotlin.math.pow

class Day4(input: List<String>) {

    private val cards = input.map { line ->
        Card(
            line.substringAfter(": ").substringBefore(" |").split(" ")
                .filter { it.isNotEmpty() }.map { it.toInt() },
            line.substringAfter("| ").split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
        )
    }.withIndex().associate { it.index + 1 to it.value }

    private data class Card(val winningNumbers: List<Int>, val myNumbers: List<Int>) {
        val matchingNumbers = myNumbers.count { it in winningNumbers }
    }


    fun solvePart1(): Int {
        return cards.values.sumOf { card ->
            2.toDouble().pow(card.matchingNumbers - 1).toInt()
        }
    }

    fun solvePart2(): Int {
        val numCards = cards.keys.associateWith { 1 }.toMutableMap()
        cards.forEach { (cardNum, card) ->
            (1..card.matchingNumbers).forEach {
                numCards[cardNum + it] = numCards[cardNum + it]!! + numCards[cardNum]!!
            }
        }
        return numCards.values.sum()
    }
}