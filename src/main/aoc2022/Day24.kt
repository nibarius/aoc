package aoc2022

import MyMath
import Pos
import Search

class Day24(input: List<String>) {

    // There can be up to 4 blizzards at the same spot ==> use bitfield to represent each spot  (bit 1-4 are blizzards),
    // and bitwise operations for differentiate between the different blizzards in the same location.

    private val width = input[0].length - 2
    private val height = input.size - 2
    private val initialState = parseInput(input)

    private fun parseInput(input: List<String>): List<IntArray> {
        val ret = mutableListOf<IntArray>()
        for (y in 1 until input.size - 1) {
            val row = IntArray(width)
            ret.add(row)
            for (x in row.indices) {
                row[x] = when (input[y][x + 1]) {
                    '.' -> 0
                    '>' -> 1
                    'v' -> 2
                    '<' -> 4
                    '^' -> 8
                    else -> error("Unsupported character")
                }
            }
        }
        return ret
    }

    private class BlizzardCache(private val states: MutableList<List<IntArray>>) {
        val width = states[0][0].size
        val height = states[0].size

        private operator fun List<IntArray>.get(pos: Pos) = this[pos.y][pos.x]
        private operator fun List<IntArray>.set(pos: Pos, value: Int) {
            this[pos.y][pos.x] = value
        }

        /**
         * Get the position that a blizzard moving in "this" direction will end up on the next turn assuming it has
         * the given position now. Moves one step in the desired direction and wraps around if needed.
         */
        private fun Int.nextPosFrom(pos: Pos): Pos {
            return when (this) {
                0 -> pos // no blizzard
                1 -> if (pos.x == width - 1) Pos(0, pos.y) else Pos(pos.x + 1, pos.y)  // right
                2 -> if (pos.y == height - 1) Pos(pos.x, 0) else Pos(pos.x, pos.y + 1) // down
                4 -> if (pos.x == 0) Pos(width - 1, pos.y) else Pos(pos.x - 1, pos.y)  // left
                8 -> if (pos.y == 0) Pos(pos.x, height - 1) else Pos(pos.x, pos.y - 1) // up
                else -> error("unsupported direction")
            }
        }

        /**
         * Uses bitwise operations to move all blizzards to their correct location in the next state.
         */
        private fun List<IntArray>.nextState(): List<IntArray> {
            val ret = List(height) { IntArray(width) }
            for (y in 0 until height) {
                for (x in 0 until width) {
                    listOf(1, 2, 4, 8).forEach { direction ->
                        if (this[y][x] and direction > 0) {
                            // if this position contains a blizzard with the current direction, put it
                            // in the correct location in the resulting blizzard state. Use bitwise or
                            // so that it doesn't overwrite any other blizzard that's located at the same place.
                            direction.nextPosFrom(Pos(x, y)).let { ret[it] = ret[it] or direction }
                        }
                    }
                }
            }
            return ret
        }

        // Given enough time the blizzard returns to the initial state, no point in calculating blizzard
        // states further than that. Example input returns to the start state for horizontally moving blizzards
        // every 6 minutes (width is 6) and for vertically moving blizzards every 4 minutes (height is 4).
        // So every 12 minutes (LCM of 6 and 4) the blizzard is back at the original state.
        private val cycleLength = MyMath.lcm(setOf(width, height)).toInt()

        /**
         * Get the blizzard state at the given time. Returns a cached version if available, or calculates a
         * new state if it hasn't been calculated before.
         */
        operator fun get(rawTime: Int): List<IntArray> {
            val time = rawTime % cycleLength
            while (states.size <= time) {
                states.add(states.last().nextState())
            }
            return states[time]
        }
    }

    private data class State(val time: Int, val pos: Pos)

    private class Graph(val blizzardCache: BlizzardCache) : Search.WeightedGraph<State> {
        private val mapWidth = blizzardCache.width
        private val mapHeight = blizzardCache.height

        private fun Pos.isStartOrGoal() = this in setOf(Pos(0, -1), Pos(mapWidth - 1, mapHeight))
        private fun Pos.isNotABlizzardLocation(blizzard: List<IntArray>) =
            y in 0 until mapHeight && x in 0 until mapWidth && blizzard[y][x] == 0

        override fun neighbours(id: State): List<State> {
            return (mutableListOf(id.pos) + id.pos.allNeighbours()) // either stay or move
                .filter { it.isStartOrGoal() || it.isNotABlizzardLocation(blizzardCache[id.time + 1]) }
                .map { State(id.time + 1, it) }
        }

        override fun cost(from: State, to: State) = 1f
    }

    private val graph = Graph(BlizzardCache(mutableListOf(initialState)))

    /**
     * Walk through the valley avoiding blizzards. Returns the current time when reaching the goal.
     */
    private fun dodgeBlizzards(initialTime: Int, from: Pos, to: Pos): Int {
        val (last, _) = Search.aStar(
            graph,
            start = State(initialTime, from),
            goalFn = { it.pos == to },
            heuristic = { it.pos.distanceTo(to).toFloat() })
        return last.time
    }

    fun solvePart1(): Int {
        return dodgeBlizzards(0, Pos(0, -1), Pos(width - 1, height))
    }

    fun solvePart2(): Int {
        val goal = Pos(width - 1, height)
        val start = Pos(0, -1)

        val first = dodgeBlizzards(0, start, goal)
        val second = dodgeBlizzards(first, goal, start)
        return dodgeBlizzards(second, start, goal)
    }
}