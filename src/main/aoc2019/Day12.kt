package aoc2019

import java.math.BigInteger
import kotlin.math.abs

class Day12(input: List<String>) {

    data class Moon(val pos: MutableList<Int>, val vel: MutableList<Int>) {
        fun applyGravity(other: Moon) {
            (0..2).forEach {
                vel[it] += if (pos[it] < other.pos[it]) 1 else if (pos[it] > other.pos[it]) -1 else 0
            }
        }

        fun applyVelocity() {
            (0..2).forEach { pos[it] += vel[it] }
        }

        private fun potentialEnergy() = pos.sumBy { abs(it) }
        private fun kineticEnergy() = vel.sumBy { abs(it) }
        fun energy() = potentialEnergy() * kineticEnergy()
    }

    private fun parseInput(input: List<String>): List<Moon> {
        return input.map { line ->
            Moon(mutableListOf(
                    line.substringAfter("x=").substringBefore(",").toInt(),
                    line.substringAfter("y=").substringBefore(",").toInt(),
                    line.substringAfter("z=").substringBefore(">").toInt()),
                    mutableListOf(0, 0, 0))
        }
    }

    private val system = parseInput(input)

    private fun doOneStep() {
        system.forEach { first ->
            system.forEach { second ->
                if (first != second) {
                    first.applyGravity(second)
                }
            }
        }
        system.forEach { it.applyVelocity() }
    }

    fun solvePart1(steps: Int): Int {
        repeat(steps) {
            doOneStep()
        }
        return system.sumBy { it.energy() }

    }

    /*
     (x1, y1, z1),     (x1, x2, x3),
     (x2, y2, z2), ==> (y1, y2, y3),
     (x3, y3, z3)      (z1, z2, z3)
     */
    private fun transpose(input: List<List<Int>>): List<List<Int>> {
        val out = List(3) { IntArray(3) { 0 } }
        (0..2).forEach { y ->
            (0..2).forEach { x ->
                out[y][x] = input[x][y]
            }
        }
        return out.map { it.toList() }
    }


    fun solvePart2(): Long {
        // Due to the inherent math in the problem there are loops and the very first state
        // will be the first state that repeats it self.
        // https://www.reddit.com/r/adventofcode/comments/e9vfod/2019_day_12_i_see_everyone_solving_it_with_an/

        // x, y, z axis are completely independent so search for the loop length of each one
        // But important to look for loops for each axis as a whole system
        // https://www.reddit.com/r/adventofcode/comments/e9ufz7/2019_day_12_part_2_intermediate_data_from_the/

        val startPos = transpose(system.map { it.pos })
        // start velocity is 0

        // Map of Axis to Loop length
        val loops = mutableMapOf<Int, Int>()
        var iterations = 0
        while (loops.size < 3) {
            doOneStep()
            iterations++
            (0..2).forEach { axis ->
                if (!loops.containsKey(axis) && // Loop already found for this axis
                        system.all { it.vel[axis] == 0 } && // Initial state for velocity
                        transpose(system.map { it.pos })[axis] == startPos[axis]) { // Initial state for position
                    loops[axis] = iterations
                }
            }
        }
        return MyMath.lcm(loops.values)
    }
}