package aoc2022

import AMap
import Pos
import size

class Day23(input: List<String>) {

    enum class Direction(private val adjacent: Pos) {
        North(Pos(0, -1)), South(Pos(0, 1)), West(Pos(-1, 0)), East(Pos(1, 0));

        fun neighboursAreAdjacentInThisDirection(from: Pos, to: Pos): Boolean {
            return when (this) {
                North -> from.y > to.y
                South -> from.y < to.y
                West -> from.x > to.x
                East -> from.x < to.x
            }
        }

        fun moveFrom(from: Pos) = from + adjacent
    }

    // set of positions for all elves
    private val elves = AMap.parse(input, listOf('.')).keys.toMutableSet()

    private fun List<Pos>.noneInDirectionFrom(direction: Direction, from: Pos): Boolean {
        return none { direction.neighboursAreAdjacentInThisDirection(from, it) }
    }

    /**
     * Simulates and returns the number of turns after there were no movement at all
     */
    private fun simulate(maxTurns: Int): Int {
        val directions = Direction.entries

        repeat(maxTurns) { turn ->
            // proposed position to origin position
            val proposedPositions = mutableMapOf<Pos, Pos>()

            // Propose where to move
            elves.forEach { elfPos ->
                val neighbours = elfPos.allNeighbours(true).filter { it in elves }
                if (neighbours.isNotEmpty()) {
                    for (dir in 0..3) {
                        val direction = directions[(dir + turn) % 4]
                        if (neighbours.noneInDirectionFrom(direction, elfPos)) {
                            val moveTo = direction.moveFrom(elfPos)
                            if (proposedPositions.putIfAbsent(moveTo, elfPos) != null) {
                                // There can be at most two elves moving to the same location. If there is a
                                // collision, remove the one that wanted to move here
                                proposedPositions.remove(moveTo)
                            }
                            break
                        }
                    }
                }
            }

            // Move
            proposedPositions.ifEmpty { return turn + 1 }.forEach { (movingTo, movingFrom) ->
                elves.remove(movingFrom)
                elves.add(movingTo)
            }

        }
        return maxTurns
    }

    private fun MutableSet<Pos>.boundingBox(): Pair<IntRange, IntRange> {
        val x = minByOrNull { it.x }!!.x..maxByOrNull { it.x }!!.x
        val y = minByOrNull { it.y }!!.y..maxByOrNull { it.y }!!.y
        return x to y
    }

    private fun Pair<IntRange, IntRange>.area() = first.size() * second.size()
    @Suppress("unused") // Useful for testing
    private fun MutableSet<Pos>.print() {
        val bb = boundingBox()
        for (y in bb.second) {
            for (x in bb.first) {
                val ch = if (Pos(x, y) in this) '#' else '.'
                print(ch)
            }
            println()
        }
    }

    fun solvePart1(): Int {
        simulate(10)
        return elves.boundingBox().area() - elves.size
    }

    fun solvePart2(): Int {
        return simulate(1000)
    }
}