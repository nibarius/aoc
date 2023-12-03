package aoc2023

import AMap
import Pos
import increase

class Day3(input: List<String>) {
    private val map = AMap.parse(input, listOf('.'))

    private val numbers = findNumbers()
    private val symbols = map.toMap().filterValues { it !in '0'..'9' }

    // Map each number to the position of the first digit in the number
    private fun findNumbers(): Map<Pos, String> {
        val rawNumbers = map.toMap().filterValues { it in '0'..'9' }
        var currentNumber = ""
        var previousPos = Pos(-1, -1)
        val numbers = mutableMapOf<Pos, String>()
        rawNumbers.forEach { (pos, value) ->
            if (pos != previousPos.move(Direction.Right) && currentNumber != "") {
                numbers[previousPos.move(Direction.Left, currentNumber.length - 1)] = currentNumber
                currentNumber = ""
            }
            currentNumber += value
            previousPos = pos
        }
        numbers[previousPos.move(Direction.Left, currentNumber.length - 1)] = currentNumber
        return numbers
    }

    // Number of neighbours (which are symbols) each number have
    private val numNeighboursForNumbers = mutableMapOf<Pos, Int>()
    // Map of symbols to all their neighbours (which are numbers)
    private val neighboursOfSymbols = mutableMapOf<Pos, MutableList<String>>()

    init {
        numbers.forEach { (pos, number) ->
            // All neighbouring positions to the current number
            val neighbours = mutableSetOf<Pos>()
            (number.indices).forEach {
                neighbours.addAll(pos.move(Direction.Right, it).allNeighbours(true))
            }

            symbols.filterKeys { it in neighbours }.forEach { (symbolPos, _) ->
                numNeighboursForNumbers.increase(pos)
                if (neighboursOfSymbols[symbolPos] == null) {
                    neighboursOfSymbols[symbolPos] = mutableListOf()
                }
                neighboursOfSymbols[symbolPos]!!.add(number)
            }
        }
    }

    fun solvePart1(): Int {
        return numbers
            .filterKeys { numNeighboursForNumbers.getOrDefault(it, 0) > 0 }
            .values
            .sumOf { it.toInt() }
    }

    fun solvePart2(): Int {
        return neighboursOfSymbols
            .filter { symbols[it.key] == '*' && it.value.size == 2 }
            .values
            .sumOf { it.map { number -> number.toInt() }.reduce(Int::times) }
    }
}