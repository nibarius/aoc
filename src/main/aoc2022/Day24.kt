package aoc2022

import Pos
import Search

class Day24(input: List<String>) {

    private val width = input[0].length - 2
    private val height = input.size - 2
    private val map = parseInput(input)

    private fun parseInput(input: List<String>): Map<Pos, Char> {
        return buildMap {
            for (y in 1 until input.size - 1) {
                for (x in 1 until input[y].length - 1) {
                    this[Pos(x - 1, y - 1)] = input[y][x]
                }
            }
        }
    }

    private data class State(val time: Int, val pos: Pos)

    private class Graph(val map: Map<Pos, Char>, val mapWidth: Int, val mapHeight: Int) : Search.WeightedGraph<State> {

        private fun Pos.isStartOrGoal() = this in setOf(Pos(0, -1), Pos(mapWidth - 1, mapHeight))

        // Check, in each direction if there is a blizzard that will end up on this spot at the given time.
        private fun Pos.isASafeSpot(atTime: Int): Boolean {
            return this in map &&
                    map[Pos((x - atTime).mod(mapWidth), y)] != '>' &&
                    map[Pos((x + atTime).mod(mapWidth), y)] != '<' &&
                    map[Pos(x, (y - atTime).mod(mapHeight))] != 'v' &&
                    map[Pos(x, (y + atTime).mod(mapHeight))] != '^'
        }

        override fun neighbours(id: State): List<State> {
            return (mutableListOf(id.pos) + id.pos.allNeighbours()) // either stay or move
                .filter { it.isStartOrGoal() || it.isASafeSpot(id.time + 1) }
                .map { State(id.time + 1, it) }
        }

        override fun cost(from: State, to: State) = 1f
    }

    private val graph = Graph(map, width, height)

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