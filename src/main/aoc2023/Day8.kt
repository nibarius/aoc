package aoc2023

import MyMath.lcm

class Day8(input: List<String>) {
    private val instructions = input.first()

    private val theMap = input.drop(2).map { line ->
        val key = line.substringBefore(" = ")
        val left = line.substringAfter("(").substringBefore(",")
        val right = line.substringAfter(", ").substringBefore(")")
        key to (mapOf('L' to left, 'R' to right))
    }.toMap()

    fun solvePart1(): Int {
        var steps = 0
        var current = "AAA"
        while (current != "ZZZ") {
            current = theMap[current]!![instructions[steps % instructions.length]]!!
            steps++
        }
        return steps
    }

    // All paths in the input have friendly loops. Directly after a goal node it
    // moves back to the beginning, so the goal is the last step of the goal.
    fun solvePart2(): Long {
        val startingPoints = theMap.keys.filter { it.endsWith("A") }
        val loops = startingPoints.map { startingPoint  ->
            var steps = 0
            var current = startingPoint
            while (!current.endsWith("Z")) {
                current = theMap[current]!![instructions[steps % instructions.length]]!!
                steps++
            }
            steps
        }
        return lcm(loops)
    }
}