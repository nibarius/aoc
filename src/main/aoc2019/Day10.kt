package aoc2019

import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan2
import Pos

class Day10(input: List<String>) {

    data class Angle(val value: Double) {
        var pos = Pos(0, 0)
        var distance: Int = 0

        companion object {
            fun calculate(self: Pos, other: Pos): Angle {
                // Atan2 - PI gives angle 0 for straight up and negative angles for everything else
                // with angle decreasing further while rotating clockwise
                return Angle(atan2((other.x - self.x).toDouble(), (other.y - self.y).toDouble()) - PI)
                        .apply {
                            distance = self.distanceTo(other)
                            pos = other
                        }
            }
        }
    }

    private fun List<Pos>.inSightOf(other: Pos): Int {
        return this.filterNot { it == other }
                .map { Angle.calculate(other, it) }
                .toSet()
                .size
    }

    private val asteroids = parseInput(input)

    private fun parseInput(input: List<String>): List<Pos> {
        return mutableListOf<Pos>().apply {
            input.forEachIndexed { y, s ->
                s.forEachIndexed { x, c -> if (c == '#') this.add(Pos(x, y)) }
            }
        }
    }

    fun solvePart1(): Int {
        return asteroids.maxOfOrNull { asteroids.inSightOf(it) } ?: 0
    }

    fun solvePart2(): Int {
        val best = asteroids.map { it to asteroids.inSightOf(it) }.maxByOrNull { it.second }!!.first
        val targets: MutableMap<Double, MutableList<Angle>> = asteroids.filterNot { it == best }
                .map { Angle.calculate(best, it) }
                // smallest angle (from up) first, then smallest distance
                .sortedWith(compareBy({ abs(it.value) }, { it.distance }))
                .groupByTo(mutableMapOf()) { it.value }

        var key = 1.0
        var lastKill = best
        repeat(200) {
            // Keep moving clockwise to next angle each time, or wrap around if needed
            key = targets.keys.firstOrNull { it < key } ?: targets.keys.first()
            lastKill = targets[key]!!.first().pos
            if (targets[key]!!.size <= 1) { // No other asteroids hides behind this one
                targets.remove(key)
            } else { // Other asteroids hides behind this, only destroy the first one
                targets[key]!!.removeAt(0)
            }
            if (targets.isEmpty()) { // Incomplete test case only
                return -1
            }
        }

        return lastKill.x * 100 + lastKill.y
    }
}