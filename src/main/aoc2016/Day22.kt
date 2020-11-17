package aoc2016

import java.util.*

class Day22(input: List<String>) {
    data class Node(val x: Int, val y: Int, var size: Int, var used: Int)
    data class State(val fileSystem: List<List<Node>>, val goalDataAt: Pair<Int, Int>) {
        var steps = 0
    }

    private val fileSystem = parseInput(input)

    private fun List<List<Node>>.deepCopy() = List(size) { get(it).copy() }
    private fun List<Node>.copy() = List(size) {
        val orig = get(it)
        Node(orig.x, orig.y, orig.size, orig.used)
    }

    private fun parseInput(input: List<String>): List<List<Node>> {
        val nodes = mutableListOf<Node>()
        input.drop(2).forEach {
            val parts = it.split("\\s+".toRegex())
            nodes.add(Node(
                    parts[0].substringAfter("x").substringBefore("-").toInt(),
                    parts[0].substringAfter("y").toInt(),
                    parts[1].dropLast(1).toInt(),
                    parts[2].dropLast(1).toInt()))
        }
        return List(nodes.last().y + 1) { y ->
            List(nodes.last().x + 1) { x ->
                nodes.find { it.x == x && it.y == y }!!
            }
        }
    }

    private fun getViablePairs(fileSystem: List<List<Node>>): List<Pair<Node, Node>> {
        val ret = mutableListOf<Pair<Node, Node>>()
        val height = fileSystem.size
        val width = fileSystem[0].size

        for (ay in 0 until height) {
            for (ax in 0 until width) {
                for (by in 0 until height) {
                    for (bx in 0 until width) {
                        if (fileSystem[ay][ax].used > 0 &&
                                fileSystem[ay][ax] != fileSystem[by][bx] &&
                                fileSystem[ay][ax].used <= fileSystem[by][bx].size - fileSystem[by][bx].used) {
                            ret.add(Pair(fileSystem[ay][ax], fileSystem[by][bx]))
                        }
                    }
                }
            }
        }
        return ret
    }

    private fun getPossibleMoves(fileSystem: List<List<Node>>): List<Pair<Node, Node>> {
        val ret = mutableListOf<Pair<Node, Node>>()
        val height = fileSystem.size
        val width = fileSystem[0].size
        for (ay in 0 until height) {
            for (ax in 0 until width) {
                listOf(Pair(ax, ay - 1), Pair(ax, ay + 1), Pair(ax - 1, ay), Pair(ax + 1, ay)).forEach {
                    val (bx, by) = it
                    if (bx in 0 until width && by in 0 until height &&
                            fileSystem[ay][ax].used > 0 &&
                            fileSystem[ay][ax].used <= fileSystem[by][bx].size - fileSystem[by][bx].used) {
                        ret.add(Pair(fileSystem[ay][ax], fileSystem[by][bx]))
                    }
                }
            }
        }
        return ret
    }

    private val alreadyChecked = mutableSetOf<State>()

    private fun moveData(initialFileSystem: List<List<Node>>): Int {
        val toCheck = ArrayDeque<State>()
        var state = State(initialFileSystem, Pair(initialFileSystem[0].size - 1, 0)) // Initially data is at xMax, y0
        var i = 0

        do {
            val possibleMoves = getPossibleMoves(state.fileSystem)
            for (move in possibleMoves) {
                val goalDataAt = if (move.first.x == state.goalDataAt.first && move.first.y == state.goalDataAt.second) {
                    // moving goal data
                    Pair(move.second.x, move.second.y)
                } else {
                    state.goalDataAt
                }
                if (goalDataAt == Pair(0, 0)) {
                    return state.steps + 1
                }
                val fs = state.fileSystem.deepCopy()
                fs[move.second.y][move.second.x].used += fs[move.first.y][move.first.x].used
                fs[move.first.y][move.first.x].used = 0
                val nextState = State(fs, goalDataAt)
                nextState.steps = state.steps + 1
                if (!alreadyChecked.contains(nextState)) {
                    alreadyChecked.add(nextState)
                    toCheck.add(nextState)
                }
            }
            i++
            if (i % 10000 == 0) {
                println("iteration: $i, Steps: ${state.steps}, Goal data at ${state.goalDataAt} Queue size: ${toCheck.size}")
            }
            if (toCheck.size == 0) {
                println("no more queue, steps: ${state.steps}")
            }
            state = toCheck.removeFirst()
        } while (true)
    }


    fun solvePart1(): Int {
        return getViablePairs(fileSystem).size
    }

    // Too many possibilities to try all possible solutions. Works fine on example input, but
    // can't be done on the real input.
    fun solvePart2(): Int {
        return moveData(fileSystem)
    }

    // Investigate the map to learn more about what it looks like to see what kind of
    // generalizations can be done in my case to be able to solve the problem.
    fun investigateMap() {
        val pairs = getViablePairs(fileSystem)
        val minDataToMove = pairs.minByOrNull { it.first.used }!!.first.used
        val nodeWithMostFree = pairs.maxByOrNull { it.first.size - it.first.used }!!.first
        val maxFreeSpace = nodeWithMostFree.size - nodeWithMostFree.used
        val numEmptyNodes = pairs.flatMap { listOf(it.second) }.toSet().size
        println("Min data: $minDataToMove, Max free: $maxFreeSpace, Empty nodes: $numEmptyNodes")
        // With my input: Min data: 64, Max free: 30, Empty nodes: 1
        // Which means there is only one empty node which data can be moved to. There are no
        // two nodes with data who's data can fit into each others.

        val maxMovableCapacity = pairs.maxByOrNull { it.first.size }!!.first.size
        val bigNodes = fileSystem.flatten().filter { it.used > maxMovableCapacity }
        val minX = bigNodes.minByOrNull { it.x }!!.x
        val maxX = bigNodes.maxByOrNull { it.x }!!.x
        val minY = bigNodes.minByOrNull { it.y }!!.y
        val maxY = bigNodes.maxByOrNull { it.y }!!.y
        val hole = pairs.flatMap { listOf(it.second) }.toSet().first()
        println("minX: $minX, maxX: $maxX, minY: $minY, maxY: $maxY, holeX: ${hole.x}, holeY: ${hole.y}")
        // With my input: minX: 9, maxX: 31, minY: 12, maxY: 12, holeX: 24, holeY: 22
        // Which means there is a solid wall of huge objects that can't be moved at
        // Y = 12, 9 <= X <= 31
    }

    // Need to find a specialized solution that does a lot of generalizations and simplifies the
    // problem to something solvable.
    fun solveMyPart2(): Int {
        val pairs = getViablePairs(fileSystem)
        val hole = pairs.flatMap { listOf(it.second) }.toSet().first()
        val maxMovableCapacity = pairs.maxByOrNull { it.first.size }!!.first.size
        val bigNodes = fileSystem.flatten().filter { it.used > maxMovableCapacity }
        val leftOfWall = bigNodes.minByOrNull { it.x }!!.x - 1
        val goalData = Pair(31, 0)

        var steps = 0

        // move hole left past the wall
        steps += hole.x - leftOfWall

        // move hole to the top
        steps += hole.y

        // move hole to the left of the goal data
        steps += (goalData.first - 1) - leftOfWall

        // to move the goal data one step left and to position the hole in front of it
        // requires 5 steps (move data, then circle around it)
        // move hole from position 31 to 1
        steps += 5 * 30

        // Move the goal data to the goal
        steps += 1
        return steps
    }
}
