package aoc2021

import java.util.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class Day23(private val rawInput: List<String>, roomSizeToUse: Int = 2) {
    init {
        roomSize = roomSizeToUse
    }

    val initialMap = parseInput(rawInput)

    private fun Char.getType(): Int = when (this) {
        'A' -> 0
        'B' -> 1
        'C' -> 2
        'D' -> 3
        '.' -> -1
        else -> error("invalid character")
    }

    /*
    The playing field is represented as an IntArray where the 7 first items is the positions
    in the corridor that can be occupied. The remaining items are the 4 different rooms.
    Indexes are illustrated as follows (using the part 1 layout)

             #############
             #01 2 3 4 56#
             ###7#9#1#3###
               #8#0#2#4#
               #########

     The value indicates who is in the position, -1 means empty, A is 0, B is 1 and so on.
     */
    fun parseInput(input: List<String>): List<Int> {
        val ret = MutableList(15) { -1 }
        ret[0] = input[1][1].getType()
        ret[1] = input[1][2].getType()
        ret[2] = input[1][4].getType()
        ret[3] = input[1][6].getType()
        ret[4] = input[1][8].getType()
        ret[5] = input[1][10].getType()
        ret[6] = input[1][11].getType()
        ret[7] = input[2][3].getType()
        ret[8] = input[3][3].getType()
        ret[9] = input[2][5].getType()
        ret[10] = input[3][5].getType()
        ret[11] = input[2][7].getType()
        ret[12] = input[3][7].getType()
        ret[13] = input[2][9].getType()
        ret[14] = input[3][9].getType()
        return ret
    }

    // Parse part2 input (used by tests)
    private fun parseInput2(input: List<String>): List<Int> {
        val ret = MutableList(23) { -1 }
        ret[0] = input[1][1].getType()
        ret[1] = input[1][2].getType()
        ret[2] = input[1][4].getType()
        ret[3] = input[1][6].getType()
        ret[4] = input[1][8].getType()
        ret[5] = input[1][10].getType()
        ret[6] = input[1][11].getType()
        ret[7] = input[2][3].getType()
        ret[8] = input[3][3].getType()
        ret[9] = input[4][3].getType()
        ret[10] = input[5][3].getType()
        ret[11] = input[2][5].getType()
        ret[12] = input[3][5].getType()
        ret[13] = input[4][5].getType()
        ret[14] = input[5][5].getType()
        ret[15] = input[2][7].getType()
        ret[16] = input[3][7].getType()
        ret[17] = input[4][7].getType()
        ret[18] = input[5][7].getType()
        ret[19] = input[2][9].getType()
        ret[20] = input[3][9].getType()
        ret[21] = input[4][9].getType()
        ret[22] = input[5][9].getType()
        return ret
    }

    data class Move(val destination: Int, val steps: Int)

    data class GameState(val positions: List<Int>) {
        var totalCost = 0
        val amphipodPositions = positions.indices.filter { positions[it].isAmphipod() }
        val estimatedRemainingCost = heuristic()
        fun isDone(): Boolean {
            return (0..3).all { type ->
                homePositionsFor(type).all { positions[it] == type }
            }
        }

        fun move(from: Int, to: Int, stepsRequired: Int): GameState {
            val newPositions = positions.toMutableList().apply {
                this[to] = this[from]
                this[from] = -1
            }
            return GameState(newPositions).also {
                it.totalCost = totalCost + stepsRequired * energyFor(positions[from])
            }
        }

        // Estimates the minimum cost to finish the game. Just the shortest distance for everyone disregarding any
        // blockage. Only counts the steps to the first slot in the room
        private fun heuristic(): Int {
            return amphipodPositions.sumOf { pos ->
                val type = positions[pos]
                val ownHomePositions = homePositionsFor(type)
                if (pos in ownHomePositions) {
                    0 // already home, cost to move is zero
                } else if (pos > 6) {
                    // In another room, must estimate room to room distance (countSteps can't handle it)
                    abs(exitForPosition(pos) - homeEntranceFor(type)) * 2 + // distance between entrances
                            distanceIntoRoom(pos) + // number of steps needed to get out of the current room
                            1 // one step to move down into the own room
                } else {
                    countSteps(pos, ownHomePositions[0]) * energyFor(type)
                }
            }
        }

        // Index of upper slot in home is odd, lower slot is even
        // Find all the positions that can be reached for 'toMove'
        fun allPossibleMoves(toMove: Int): List<Move> {
            val type = positions[toMove]
            val ownHomePositions = homePositionsFor(type)
            if (toMove < 7) { // Moving back to the own room
                if (ownHomePositions.any { positions[it].isAmphipod() && positions[it] != type }) {
                    return listOf() // There are other types in the room, can't enter
                }
                val entrance = homeEntranceFor(type)
                if (toMove <= entrance && (toMove + 1..entrance).any { positions[it].isAmphipod() }) {
                    return listOf() // The room is to the right, but path is blocked
                } else if (toMove > entrance && (entrance + 1 until toMove).any { positions[it].isAmphipod() }) {
                    return listOf() // The room is to the left, but path is blocked
                }
                // Move as far as possible into the room
                val destination = ownHomePositions.last { positions[it].isEmpty() }
                return listOf(Move(destination, countSteps(toMove, destination)))
            } else { // Moving out of a home
                if (toMove in ownHomePositions && ownHomePositions.all { positions[it].isEmpty() || positions[it] == type }) {
                    return listOf() // Not possible to move out of a completed home
                } else if (positionsOfTheHomeWith(toMove).any { it < toMove && positions[it].isAmphipod() }) {
                    return listOf() // There's someone higher up in the home blocking the way out
                }
                val exit = exitForPosition(toMove)
                val leftBlocker = amphipodPositions.lastOrNull { it <= exit } ?: -1
                val rightBlocker = amphipodPositions.firstOrNull { it in exit + 1..6 } ?: 7
                return (leftBlocker + 1 until rightBlocker).map { to -> Move(to, countSteps(toMove, to)) }
            }
        }

        // For debugging purposes
        fun print() {
            println("#############")
            println(
                "#${positions[0]}${positions[1]}.${positions[2]}.${positions[3]}.${positions[4]}.${positions[5]}${positions[6]}#"
                    .replace("-1", ".")
            )

            if (positions.size <= 14) {
                println("###${positions[7]}#${positions[9]}#${positions[11]}#${positions[13]}###".replace("-1", "."))
                println("###${positions[8]}#${positions[10]}#${positions[12]}#${positions[14]}###".replace("-1", "."))
            } else {
                println("###${positions[7]}#${positions[11]}#${positions[15]}#${positions[19]}###".replace("-1", "."))
                println("###${positions[8]}#${positions[12]}#${positions[16]}#${positions[20]}###".replace("-1", "."))
                println("###${positions[9]}#${positions[13]}#${positions[17]}#${positions[21]}###".replace("-1", "."))
                println("###${positions[10]}#${positions[14]}#${positions[18]}#${positions[22]}###".replace("-1", "."))
            }
            println("#############")
        }
    }

    companion object {
        // Not nice with a static variable shared between all instances that varies between instances
        // but works in this case and saves me having to pass around roomSize as parameter all the time.
        var roomSize = 2 // Set to 4 by part 2 for bigger rooms

        // The home positions are accessed fairly frequently, create them only once.
        private val homePositions2 by lazy { buildHomePositions(2) }
        private val homePositions4 by lazy { buildHomePositions(4) }

        private fun Int.isEmpty() = this == -1
        private fun Int.isAmphipod() = this != -1

        private fun buildHomePositions(size: Int) = buildMap {
            (0..3).forEach { type ->
                this[type] = (0 until size).map { 7 + it + size * type }
            }
        }

        // Return the positions in home room for the given type of amphipod
        private fun homePositionsFor(type: Int): List<Int> {
            return if (roomSize == 2) homePositions2.getValue(type) else homePositions4.getValue(type)
        }

        private val energyMap = mapOf(0 to 1, 1 to 10, 2 to 100, 3 to 1000)
        // Return the energy required for each step for the given type of amphipod
        fun energyFor(type: Int): Int {
            return energyMap.getValue(type)
        }

        // Returns all positions in the home that 'position' is located in
        private fun positionsOfTheHomeWith(position: Int): List<Int> {
            return homePositionsFor((position - 7) / roomSize)
        }

        // Returns how far into the room the given position is (the distance to the exit)
        private fun distanceIntoRoom(position: Int): Int {
            return ((position - 7) % roomSize) + 1
        }

        // Return where the exit to the home of the specified position
        // 7-8 => 1, 9-10 => 2, ...
        private fun exitForPosition(position: Int): Int {
            return (position - 7) / roomSize + 1
        }

        // Return where the entrance to the home is. The entrance is after the given index, but before the next.
        private fun homeEntranceFor(type: Int): Int {
            return type + 1 // Entrance for type 0 is after 1, for type 1 after 2 and so on
        }

        private fun countSteps(from: Int, to: Int): Int {
            val hallway = min(from, to)
            val room = max(from, to)
            val exit = exitForPosition(room)
            val stepsToExit = if (hallway <= exit) { // moving to the right
                // two steps for each index, one step to move from exit offset to the real offset, but one less for pos 0
                (exit - hallway) * 2 + if (hallway > 0) 1 else 0
            } else {
                // two steps for each index, but one less since exit is actually one step after the index, two less last pos.
                (hallway - exit) * 2 - if (hallway < 6) 1 else 2
            }
            return stepsToExit + distanceIntoRoom(room)
        }
    }

    private fun play(initial: GameState): Int {
        val toCheck = PriorityQueue(compareBy<GameState> { it.totalCost + it.estimatedRemainingCost })
        toCheck.add(initial)
        val alreadyChecked = mutableSetOf<GameState>()
        while (toCheck.isNotEmpty()) {
            val current = toCheck.remove()
            current.amphipodPositions.forEach { amphipodPosition ->
                current.allPossibleMoves(amphipodPosition).forEach { move ->
                    val nextState = current.move(amphipodPosition, move.destination, move.steps)
                    if (nextState.isDone()) {
                        return nextState.totalCost
                    } else if (nextState !in alreadyChecked) {
                        alreadyChecked.add(nextState)
                        toCheck.add(nextState)
                    }
                }
            }
        }
        return -1
    }

    fun solvePart1(): Int {
        return play(GameState(initialMap))
    }

    private fun createPart2Input(): List<Int> {
        val part2 = MutableList(23) { -1 }
        (0..7).forEach { part2[it] = initialMap[it] }
        part2[8] = 3
        part2[9] = 3
        part2[10] = initialMap[8]
        part2[11] = initialMap[9]
        part2[12] = 2
        part2[13] = 1
        part2[14] = initialMap[10]
        part2[15] = initialMap[11]
        part2[16] = 1
        part2[17] = 0
        part2[18] = initialMap[12]
        part2[19] = initialMap[13]
        part2[20] = 0
        part2[21] = 2
        part2[22] = initialMap[14]
        return part2
    }

    fun solvePart2(): Int {
        val input = if (rawInput.size == 5) createPart2Input() else parseInput2(rawInput)
        return play(GameState(input))
    }
}