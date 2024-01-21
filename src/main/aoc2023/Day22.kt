package aoc2023

import java.util.*

class Day22(input: List<String>) {
    private data class SnapshotBrick(val id: Int, val x: IntRange, val y: IntRange, val z: IntRange)

    private data class Brick(val id: Int, val z: Int, val restingOn: Set<Int>, val supporting: Set<Int>)

    private fun range(a: String, b: String): IntRange {
        return a.toInt().. b.toInt()
    }

    private val allBricks: Map<Int, Brick>

    // Shift a range downward by subtracting from both the start and the end.
    private operator fun IntRange.minus(amount: Int): IntRange {
        return first - amount..last - amount
    }

    init {
        val snapshot = input.withIndex().map {
            val regex = """(\d+),(\d+),(\d+)~(\d+),(\d+),(\d+)""".toRegex()
            val (x1, y1, z1, x2, y2, z2) = regex.matchEntire(it.value)!!.destructured
            SnapshotBrick(it.index, range(x1, x2), range(y1, y2), range(z1, z2))
        }.sortedBy { it.z.first } // Process bricks from bottom and up

        // brick id is key, value are all the bricks that is laying on top of this brick
        val supporting = snapshot.map { it.id }.associateWith { mutableSetOf<Int>() }
        // Map of which other brick one brick is supported by
        val restingOn = mutableMapOf<Int, Set<Int>>()

        val settled = mutableListOf<SnapshotBrick>()
        for (falling in snapshot) {
            if (falling.z.first == 1) {
                // Brick is already on the ground
                settled.add(falling)
                continue
            }
            val bricksBelow = settled.filter { resting ->
                resting.x.intersect(falling.x).isNotEmpty() && resting.y.intersect(falling.y).isNotEmpty()
            }

            // Brick falls down and lands on another brick or on the ground
            val highestZ = bricksBelow.maxOfOrNull { it.z.last } ?: 0 //ground is at height zero
            // lands one higher than the brick it will rest on
            val fallDistance = falling.z.first - highestZ - 1
            settled.add(SnapshotBrick(falling.id, falling.x, falling.y, falling.z - fallDistance))

            // Store information about which bricks this rests on for those bricks that
            // this brick is being supported by them.
            val bricksDirectlyBelow = bricksBelow.filter { it.z.last == highestZ }
            restingOn[falling.id] = bricksDirectlyBelow.map { it.id }.toSet()
            bricksDirectlyBelow.forEach { restingBrick ->
                supporting[restingBrick.id]!!.add(falling.id)
            }
        }

        // Make a bidirectional tree of all the bricks. The ID is used to look up any node.
        allBricks = snapshot.map {
            val id = it.id
            Brick(id, it.z.first, restingOn[id] ?: setOf(), supporting[id] ?: setOf())
        }.associateBy { it.id }
    }

    fun solvePart1(): Int {
        // All bricks that are supporting bricks together with someone else might be safe to remove
        val candidate = allBricks.filter { it.value.restingOn.size > 1 }.flatMap { it.value.restingOn }.toSet()
        // But only if they don't also are the only supporter for some other brick
        val soleSupporter = allBricks.filter { it.value.restingOn.size == 1 }.flatMap { it.value.restingOn }.toSet()
        val safeToDisintegrate = candidate - soleSupporter

        // All bricks that are not supporting any other bricks (at the top of the stack) can be removed
        val atTop = allBricks.filter { it.value.supporting.isEmpty() }
        return safeToDisintegrate.size + atTop.size
    }


    private fun numberOfBricksThatWouldFall(initialRemoval: Int): Int {
        // Use a priority queue to process bricks from the lowest position to the highest
        val toCheck = PriorityQueue<Brick> { a, b -> a.z - b.z }
            .apply {
                allBricks[initialRemoval]!!.supporting.forEach { add(allBricks[it]!!) }
            }
        val bricksThatFalls = mutableSetOf(initialRemoval)
        while (toCheck.isNotEmpty()) {
            val current = toCheck.poll()
            if (current.restingOn.all { it in bricksThatFalls }) {
                // This brick will also fall since all bricks it's resting on will fall
                bricksThatFalls.add(current.id)
                current.supporting.forEach { above ->
                    val brickAbove = allBricks[above]
                    if (brickAbove !in toCheck) {
                        toCheck.add(brickAbove)
                    }
                }
            }
        }
        bricksThatFalls.remove(initialRemoval) // don't count the initial deleted brick
        return bricksThatFalls.size
    }

    fun solvePart2(): Int {
        return allBricks.values.sumOf { numberOfBricksThatWouldFall(it.id) }
    }
}