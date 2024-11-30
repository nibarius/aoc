package aoc2017

import Direction
import Pos
import kotlin.math.abs

class Day3(input: String) {
    val number = input.toInt()

    // The memory looks like this:
    // 17  16  15  14  13
    // 18   5   4   3  12
    // 19   6   1   2  11
    // 20   7   8   9  10
    // 21  22  23---> ...
    private fun calculateDistance(target: Int): Int {
        // A new layer is started in the memory when the square goes beyond
        // 1^2, 3^2, 5^2, etc. Find which layer the target number is on
        val seq = generateSequence(1) { it + 2 }
        val layer = seq.first { it * it >= target }

        // Calculate how far into the layer the target number is
        val offsetIntoLayer = target - (layer - 2) * (layer - 2)

        // Calculate how far from the next corner this is and then
        // convert it to the distance from the center of the layer.
        val distanceToNextCorner = offsetIntoLayer % (layer - 1)
        val distanceToCenterOfLayer = abs(distanceToNextCorner - layer / 2)

        // Finally return the distance to origin. The distance from the
        // center of a side to origin is half the size of the layer.
        // E.g a square with side 5 has  distance 2 to origin
        return distanceToCenterOfLayer + layer / 2
    }


    fun solvePart1(): Int {
        return calculateDistance(number)
    }

    private fun sumOfNeighbours(memory: Map<Pos, Int>, pos: Pos): Int {
        return pos.allNeighbours(includeDiagonals = true).sumOf {
            memory.getOrDefault(it, 0)
        }
    }

    fun solvePart2(): Int {
        val memory = mutableMapOf(Pos(0, 0) to 1, Pos(1, 0) to 1)
        var currentSquare = 2
        var facing = Direction.Up
        var currentPos = Pos(1, 0)
        while (currentSquare <= number) {
            if (!memory.containsKey(currentPos.move(facing.turnLeft()))) {
                facing = facing.turnLeft()
            }
            currentPos = currentPos.move(facing)
            currentSquare = sumOfNeighbours(memory, currentPos)
            memory[currentPos] = currentSquare
        }

        return currentSquare
    }
}