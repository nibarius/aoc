package aoc2018

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class Day12(input: List<String>) {
    private val initialState = input.first().substringAfter("initial state: ")
    private val rules = parseRules(input.drop(2))

    private fun parseRules(input: List<String>): Map<String, Char> {
        return input.associate { it.substringBefore(" => ") to it.last() }
    }


    //   #..#.#..##......###...###
    // ??#..#.#..##......###...###??
    private fun getRule(state: String, pos: Int): String {
        val range = pos - 2..pos + 2
        val numBeforeString = abs(min(range.first, 0))
        val numAfterString = max(range.last - (state.length - 1), 0)
        val rule = StringBuilder(range.count())

        repeat(numBeforeString) {
            rule.append('.')
        }
        rule.append(state.substring(max(0, range.first), min(range.last + 1, state.length)))
        repeat(numAfterString) {
            rule.append('.')
        }
        return rule.toString()
    }

    private fun nextState(state: String): String {
        // The amount of pots with plants in can theoretically grow at most 2 in each direction
        return Array(state.length + 4) {
            rules.getOrDefault(getRule(state, it - 2), '.')
        }.joinToString("")
    }

    private fun getValue(state: String, offset: Int): Int {
        return state.withIndex().sumOf { if (it.value == '#') it.index - offset else 0 }
    }

    private fun grow(): Int {
        val generations = 20
        var currentState = initialState
        var generation = 0
        repeat(generations) {
            currentState = nextState(currentState)
            generation++
        }
        val offset = 2 * generations
        return getValue(currentState, offset)
    }

    private data class StableGeneration(val generation: Int, val value: Int, val numPots: Int)

    private fun findFirstStableGeneration(): StableGeneration {
        var currentState = initialState
        var generation = 0
        var prevLength = 0
        var firstOfCurrentLength = 0
        var numSameLength = 0
        val stable = Array(2) { "" }

        while (true) {
            currentState = nextState(currentState)
            generation++
            val length = currentState.substringAfter("#").substringBeforeLast("#").length
            if (length == prevLength) {
                numSameLength++
                if (stable[1] == "") {
                    stable[1] = currentState
                }
                if (numSameLength >= 20) {
                    println("First stable:  ${stable[0].substring(2 * firstOfCurrentLength, stable[0].indexOfLast { it == '#' } + 1)}")
                    println("Second stable: ${stable[1].substring(2 * (firstOfCurrentLength + 1), stable[1].indexOfLast { it == '#' } + 1)}")
                    // There are a series of pots with some empty spaces between each. For each next generation the pots
                    // are shifted 1 steps to the right ==> value increases with number of pots for each generation
                    val numPots = stable[0].count { c -> c == '#' }
                    val scoreOfFirstStable = getValue(stable[0], 2 * firstOfCurrentLength)
                    return StableGeneration(firstOfCurrentLength, scoreOfFirstStable, numPots)
                }
            } else {
                firstOfCurrentLength = generation
                numSameLength = 0
                prevLength = length
                stable[0] = currentState
                stable[1] = ""
            }
        }
    }

    fun solvePart1(): Int {
        return grow()
    }

    fun solvePart2(): Long {
        val stable = findFirstStableGeneration()
        return (50000000000 - stable.generation) * stable.numPots + stable.value
    }
}