package aoc2025

import java.util.*

class Day8(input: List<String>) {
    private val junctionBoxes = input.map { coordinates ->
        val (x, y, z) = coordinates.split(",").map { it.toLong() }
        Pos3D(x, y, z)
    }

    private data class Pos3D(val x: Long, val y: Long, val z: Long) {
        // use squared Euclidean distance since we are only using the distance for sorting
        fun distanceTo(other: Pos3D): Long {
            return (other.x - x) * (other.x - x) +
                    (other.y - y) * (other.y - y) +
                    (other.z - z) * (other.z - z)
        }
    }

    // Use a priority queue to keep the list of distances sorted while building it
    // Then return a sequence to only take the once needed. Significantly increases
    // performance compared to creating a normal list and then sorting it.
    private fun measureDistances(): Sequence<Pair<Pos3D, Pos3D>> {
        val priorityQueue = PriorityQueue<Pair<Pair<Pos3D, Pos3D>, Long>> { a, b -> a.second.compareTo(b.second) }
        for (i in junctionBoxes.indices) {
            for (j in i + 1 until junctionBoxes.size) {
                val distance = junctionBoxes[i].distanceTo(junctionBoxes[j])
                priorityQueue.add(Pair(junctionBoxes[i], junctionBoxes[j]) to distance)
            }
        }
        return generateSequence { priorityQueue.poll().first }
    }

    private val sortedJunctionBoxPairs = measureDistances()

    /**
     * Connects the closest junction boxes until there is only one circuit
     * or until the limit is made. If the limit is reached the return value
     * will be the sizes of the three larges circuits multiplied with each other.
     * If there is only one circuit remaining the return value will be the
     * x coordinates of the two junction boxes that made the circuit complete.
     */
    private fun makeConnections(howMany: Int): Long {
        val circuits = junctionBoxes.map { mutableSetOf(it) }.toMutableList()
        var numConnections = 0
        for ((a, b) in sortedJunctionBoxPairs) {
            val aIn = circuits.indexOfFirst { a in it }
            val bIn = circuits.indexOfFirst { b in it }
            if (aIn != bIn) {
                circuits[aIn].addAll(circuits[bIn])
                circuits.removeAt(bIn)
                if (circuits.size == 1) {
                    return a.x * b.x
                }
            }
            if (++numConnections == howMany) {
                break
            }
        }
        return circuits.map { it.size.toLong() }.sortedDescending().take(3).reduce(Long::times)
    }

    fun solvePart1(howMany: Int): Long {
        return makeConnections(howMany)
    }

    fun solvePart2(): Long {
        return makeConnections(Int.MAX_VALUE)
    }
}