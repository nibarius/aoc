package aoc2024

import Direction
import Pos

class Day21(private val input: List<String>) {
    private val numericKeypad = mapOf(
        Pos(0, 0) to '7',
        Pos(1, 0) to '8',
        Pos(2, 0) to '9',
        Pos(0, 1) to '4',
        Pos(1, 1) to '5',
        Pos(2, 1) to '6',
        Pos(0, 2) to '1',
        Pos(1, 2) to '2',
        Pos(2, 2) to '3',
        Pos(1, 3) to '0',
        Pos(2, 3) to 'A'
    )
    private val directionalKeypad = mapOf(
        Pos(1, 0) to '^',
        Pos(2, 0) to 'A',
        Pos(0, 1) to '<',
        Pos(1, 1) to 'v',
        Pos(2, 1) to '>'
    )

    private val memory = mutableMapOf<State, Long>()
    private val posOfA = directionalKeypad.positionOf('A')

    private data class State(val at: Pos, val path: String, val robotsRemaining: Int) {
        fun isDone() = robotsRemaining == 0 || path.isEmpty()
        fun pathLength() = path.length.toLong()
    }

    /**
     * Find all different ways to move between the two given positions on the provided keypad
     */
    private fun findAllPaths(from: Pos, to: Pos, keypad: Map<Pos, Char>, soFar: String = ""): Set<String> {
        if (from == to) return setOf(soFar + "A")
        return buildSet {
            Direction.entries
                .filter { from.move(it).distanceTo(to) < from.distanceTo(to) && from.move(it) in keypad }
                .forEach { next ->
                    val nextSoFar = soFar + next.toString()
                    addAll(findAllPaths(from.move(next), to, keypad, nextSoFar))
                }
        }
    }

    private fun Map<Pos, Char>.positionOf(value: Char) = this.filter { it.value == value }.keys.single()

    private fun directionalInput(state: State): Long {
        if (state.isDone()) return state.pathLength()
        if (state in memory) return memory[state]!!
        val moveTo = directionalKeypad.positionOf(state.path.first())
        val firstButton = findAllPaths(state.at, moveTo, directionalKeypad)
            .minOf { directionalInput(State(posOfA, it, state.robotsRemaining - 1)) }
        return (firstButton + directionalInput(State(moveTo, state.path.drop(1), state.robotsRemaining)))
            .also { memory[state] = it }
    }

    private fun numericalInput(current: Char, next: Char, robots: Int): Long {
        return findAllPaths(numericKeypad.positionOf(current), numericKeypad.positionOf(next), numericKeypad)
            .minOf { path -> directionalInput(State(posOfA, path, robots)) }
    }

    private fun complexity(code: String, robots: Int): Long {
        val numeric = code.dropLast(1).toInt()
        val length = "A$code".zipWithNext().sumOf { (at, to) ->
            numericalInput(at, to, robots)
        }
        return numeric * length
    }

    fun solvePart1(): Long {
        return input.sumOf { complexity(it, 2) }
    }

    fun solvePart2(): Long {
        return input.sumOf { complexity(it, 25) }
    }
}