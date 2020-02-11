package aoc2019

import Direction
import Pos
import kotlin.math.min

class Day17(input: List<String>) {
    val parsedInput = input.map { it.toLong() }

    private fun Map<Pos, Char>.xRange() = keys.minBy { it.x }!!.x..keys.maxBy { it.x }!!.x
    private fun Map<Pos, Char>.yRange() = keys.minBy { it.y }!!.y..keys.maxBy { it.y }!!.y
    // Used for debugging
    @Suppress("unused")
    private fun printArea(map: Map<Pos, Char>): String {
        return (map.yRange()).joinToString("\n") { y ->
            (map.xRange()).joinToString("") { x ->
                map.getOrDefault(Pos(x, y), '#').toString()
            }
        }
    }

    private fun countIntersections(map: Map<Pos, Char>): Int {
        var sum = 0
        for (y in 1 until map.yRange().last) {
            for (x in 1 until map.xRange().last) {
                val current = Pos(x, y)
                if (map[current] == '#' &&
                        Direction.values().all { dir -> map[dir.from(current)] == '#' }) {
                    sum += x * y
                }
            }
        }
        return sum
    }

    private fun generateMap(): Map<Pos, Char> {
        val c = Intcode(parsedInput)
        c.run()
        var x = 0
        var y = 0
        val map = mutableMapOf<Pos, Char>()
        c.output.forEach {
            val current = it.toChar()
            if (current == '\n') {
                y++
                x = 0
            } else {
                map[Pos(x++, y)] = current
            }
        }
        return map
    }

    private fun findPath(map: Map<Pos, Char>): List<String> {
        var facing = Direction.Up // Robot is facing up at first
        var pos = map.filterValues { it == '^' }.keys.first()
        val path = mutableListOf<String>()
        while (true) {
            val code: String
            val toLeft = map.getOrDefault(facing.turnLeft().from(pos), '.')
            val toRight = map.getOrDefault(facing.turnRight().from(pos), '.')
            if (toLeft == '.' && toRight == '.') {
                // Found the end
                break
            } else if (toLeft == '#') {
                facing = facing.turnLeft()
                code = "L"
            } else {
                facing = facing.turnRight()
                code = "R"
            }

            var steps = 0
            do {
                pos = facing.from(pos)
                steps++
            } while (map.getOrDefault(facing.from(pos), '.') == '#') // Move until the pipe turns
            path.add("$code,$steps")
        }
        return path
    }

    private fun List<String>.startsWith(prefix: List<String>): Boolean {
        return subList(0, min(size, prefix.size)) == prefix
    }

    // Drop all occurrences of the given prefixes from the given list.
    private fun dropPrefixes(list: List<String>, prefixes: List<List<String>>): List<String> {
        var offset = 0
        while (offset < list.size) {
            val current = list.subList(offset, list.size)

            // Find first prefix that the list starts with, if it exist advance offset and continue
            // If there is no prefix all prefixes have been dropped, return the remainder
            prefixes.firstOrNull { current.startsWith(it) }
                    .let { prefix ->
                        if (prefix == null) {
                            return current
                        }
                        offset += prefix.size
                    }
        }
        return emptyList()
    }

    // The path needs to be split up in three functions with maximum 20 characters
    // in each function. Find the three functions that can be repeated several times
    // to make up the whole path.
    private fun findFunctions(path: List<String>): List<Pair<String, List<String>>> {
        // Max 20 characters per function means max 5 instructions per function
        val maxInstructions = 5
        val minInstructions = 1

        for (i in minInstructions..maxInstructions) {
            // Candidate instructions for the first function
            val candidate1 = path.subList(0, i)
            val remaining1 = dropPrefixes(path, listOf(candidate1))

            for (j in minInstructions..maxInstructions) {
                // candidate instructions for the second function
                val candidate2 = remaining1.subList(0, j)
                val remaining2 = dropPrefixes(remaining1, listOf(candidate2, candidate1))

                for (k in minInstructions..maxInstructions) {
                    // candidate instructions for the third function
                    val candidate3 = remaining2.subList(0, k)
                    val remaining3 = dropPrefixes(remaining2, listOf(candidate3, candidate2, candidate1))

                    if (remaining3.isEmpty()) {
                        return listOf("A" to candidate1, "B" to candidate2, "C" to candidate3)
                    } // else: these candidates didn't work, try with the next candidate
                }
            }
        }
        return emptyList()
    }

    private fun makeMainMovementRoutine(patterns: List<Pair<String, List<String>>>, path: List<String>): String {
        var ret = path.joinToString(",")
        patterns.forEach { (functionName, instructionList) ->
            val instructions = instructionList.joinToString(",")
            // Replace the identified movement functions in the path with the function names
            ret = ret.replace(instructions, functionName)
        }
        return ret
    }

    fun solvePart1(): Int {
        return countIntersections(generateMap())
    }

    fun solvePart2(): Long {
        val map = generateMap()
        val path = findPath(map)
        val functions = findFunctions(path)
        val mainRoutine = makeMainMovementRoutine(functions, path)
                .map { it.toLong() }
                .toMutableList()
                .apply { add('\n'.toLong()) }
        val movementFunctions = functions
                .map { (_, instructions) ->
                    instructions.joinToString(",")
                            .map { it.toLong() }
                            .toMutableList()
                            .apply { add('\n'.toLong()) }
                }

        // Change the value at address 0 to 2 to wake the robot up
        val actualProgram = parsedInput.toMutableList().apply { this[0] = 2L }.toList()

        Intcode(actualProgram).run {
            input.addAll(mainRoutine)
            movementFunctions.forEach { input.addAll(it) }
            input.add('n'.toLong()) // No video feed please
            input.add('\n'.toLong())
            run()
            return output.last()
        }
    }
}