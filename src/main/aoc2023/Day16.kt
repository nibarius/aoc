package aoc2023

import AMap
import Direction
import Pos
import size

class Day16(input: List<String>) {
    private val contraption = AMap.parse(input)

    private data class PosAndFacing(val pos: Pos, val facing: Direction)

    /**
     * Possibly turn once in the given direction, then move one step. Return null if outside the contraption
     * otherwise return a PosAndFacing for the new position.
     */
    private fun moveAndTurn(current: PosAndFacing, turn: Direction?): PosAndFacing? {
        val nextFacing = if (turn == null) current.facing else current.facing.turn(turn)
        val nextPos = current.pos.move(nextFacing)
        return if (contraption.contains(nextPos)) {
            PosAndFacing(nextPos, nextFacing)
        } else {
            null
        }
    }

    /**
     * Move the beam one step forward. Returns a pair where the first is the current beam and the second
     * is a new beam. First is null if the beam leave the contraption and second is non-null if the beam
     * hit a splitter and generated a new beam that inside the contraption
     */
    private fun doOneStep(c: PosAndFacing): Pair<PosAndFacing?, PosAndFacing?> {
        val symbol = contraption[c.pos]
        return when {
            symbol == '.' -> moveAndTurn(c, null) to null
            symbol == '-' && c.facing.isHorizontal() -> moveAndTurn(c, null) to null
            symbol == '|' && c.facing.isVertical() -> moveAndTurn(c, null) to null
            symbol == '\\' -> moveAndTurn(c, if (c.facing.isHorizontal()) Direction.Right else Direction.Left) to null
            symbol == '/' -> moveAndTurn(c, if (c.facing.isHorizontal()) Direction.Left else Direction.Right) to null
            symbol == '|' -> moveAndTurn(c, Direction.Left) to moveAndTurn(c, Direction.Right)
            symbol == '-' -> moveAndTurn(c, Direction.Right) to moveAndTurn(c, Direction.Left)
            else -> error("invalid state")
        }
    }

    /**
     * Trace the given beam and return the number of energized tiles
     */
    private fun traceBeams(start: PosAndFacing): Int {
        val inProgress = mutableListOf(start)
        val seen = mutableSetOf<PosAndFacing>()
        while (inProgress.isNotEmpty()) {
            var current: PosAndFacing? = inProgress.removeFirst()
            while (current != null) {
                seen.add(current)
                val (next, newBeam) = doOneStep(current)
                current = if (next != null && next !in seen) next else null
                if (newBeam != null) {
                    inProgress.add(newBeam)
                }

            }
        }
        return seen.map { it.pos }.toSet().size
    }

    fun solvePart1(): Int {
        val start = PosAndFacing(Pos(0, 0), Direction.Right)
        return traceBeams(start)
    }

    fun solvePart2(): Int {
        val xSize = contraption.xRange().size()
        val ySize = contraption.yRange().size()
        val edges = buildList {
            addAll(List(xSize) { Pos(it, 0) to Direction.Down })
            addAll(List(ySize) { Pos(0, it) to Direction.Right })
            addAll(List(xSize) { Pos(it, ySize - 1) to Direction.Down })
            addAll(List(ySize) { Pos(xSize - 1, it) to Direction.Left })
        }
        return edges.maxOf { (pos, facing) ->
            traceBeams(PosAndFacing(pos, facing))
        }
    }
}