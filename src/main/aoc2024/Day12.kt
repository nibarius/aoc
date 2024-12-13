package aoc2024

import AMap
import Direction
import Pos
import next
import prev

class Day12(input: List<String>) {
    private val garden = AMap.parse(input)

    private fun findRegion(toProcess: MutableSet<Pos>, result: MutableSet<Pos>, current: Pos): MutableSet<Pos> {
        result.add(current)
        toProcess.remove(current)
        current.allNeighbours()
            .filter { it in toProcess && garden[current] == garden[it] }
            .forEach { findRegion(toProcess, result, it) }
        return result
    }

    private fun findRegions(): MutableSet<Set<Pos>> {
        val toProcess = garden.keys.toMutableSet()
        val regions = mutableSetOf<Set<Pos>>()
        while (toProcess.isNotEmpty()) {
            regions.add(findRegion(toProcess, mutableSetOf(), toProcess.first()))
        }
        return regions
    }

    private fun Set<Pos>.cost(): Int {
        return size * sumOf { pos -> 4 - pos.allNeighbours().count { it in this } }
    }

    fun solvePart1(): Int {
        return findRegions().sumOf { it.cost() }
    }

    private fun Set<Pos>.cost2(): Int {
        val area = size
        val sides = numberOfSides()
        return area * sides
    }

    private class Walker(var pos: Pos, var facing: Direction) {
        // The position to the side of the walker.
        fun toTheSideOf() = pos.move(facing.next())
        fun move() {
            pos = pos.move(facing)
        }
        fun moveAroundOuterCorner() {
            facing = facing.next()
            move()
        }
        fun moveAroundInnerCorner() {
            pos = pos.move(facing.opposite())
            facing = facing.prev()
        }
    }

    // walk on the outside with the wall to the right and count number of turns (one turn per side).
    private fun Set<Pos>.numberOfSides(): Int {
        val onTheEdge = filter { it.allNeighbours().any { n -> n !in this } }.toMutableSet()
        var sides = 0
        while (onTheEdge.isNotEmpty()) {
            // Find starting position and direction for the walk around the edge. If there are any remaining
            // edges after the first lap it means there are holes inside that also must be counted.
            val startingPoint = onTheEdge.first()
            val outsideDirection = Direction.entries.first { startingPoint.move(it) !in this }
            val walker = Walker(startingPoint.move(outsideDirection), outsideDirection.next())
            val walked = mutableSetOf<Pair<Pos, Direction>>()

            // Walk around the whole edge until getting back where we started
            while (walker.pos to walker.facing !in walked) {
                walked.add(walker.pos to walker.facing)
                onTheEdge.remove(walker.toTheSideOf()) // remove parts of the edge as we walk past them

                walker.move()
                if (walker.toTheSideOf() !in this) { // we lost the edge, it's an outer corner.
                    walker.moveAroundOuterCorner()
                    sides++
                } else if (walker.pos in this) { // we walked into a wall, it's an inner corner.
                    walker.moveAroundInnerCorner()
                    sides++
                }
            }
        }
        return sides
    }

    fun solvePart2(): Int {
        return findRegions().sumOf { it.cost2() }
    }
}