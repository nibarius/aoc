package aoc2021

import Pos
import kotlin.math.max

class Day17(input: List<String>) {
    private val targetX: IntRange
    private val targetY: IntRange

    init {
        val (x1, x2, y1, y2) = """target area: x=(\d+)..(\d+), y=-(\d+)..-(\d+)"""
            .toRegex()
            .find(input.single())!!
            .destructured

        targetX = x1.toInt()..x2.toInt()
        targetY = -y1.toInt()..-y2.toInt()
    }

    /**
     * Check if the given x-velocity will hit the target before it either slows down and stops
     * moving forward or completely shoots past the target.
     */
    private fun xCanHitTarget(initialVelocity: Int): Boolean {
        return generateSequence(initialVelocity to 0) { (v, x) -> v - 1 to x + v }
            .takeWhile { (v, x) -> v >= 0 && x <= targetX.last }
            .any { it.second in targetX }
    }

    /**
     * Check if the given initial velocity will hit the target at some point.
     */
    private fun yCanHitTarget(initialVelocity: Pos): Boolean {
        var pos = Pos(0, 0)
        var velocity = initialVelocity
        while (pos.y >= targetY.first) { // Loop until it moves past the target
            if (pos.x in targetX && pos.y in targetY) {
                return true
            }
            pos += velocity
            velocity = Pos(max(0, velocity.x - 1), velocity.y - 1)
        }
        return false
    }

    /**
     * Return the maximum y value the given velocity will result in.
     */
    private fun maxY(initialVelocity: Int): Int {
        if (initialVelocity <= 0) {
            return initialVelocity
        }
        // Sum of consecutive numbers: n * (first number + last number) / 2.
        return initialVelocity * (initialVelocity + 1) / 2
    }


    /**
     * Return a list of all different shots that will hit the target
     */
    private fun findAllShots(): List<Pos> {
        // Velocity higher than the distance to target will go beyond the target at first step
        val possibleX = (1..targetX.last).filter { xCanHitTarget(it) }
        return possibleX.flatMap { x ->
            // Shooting downward the lower limit of y velocity is the bottom boundary of the
            // target for the same reason as for x.
            //
            // When shooting upwards the probe will have one step at y=0 on the way down.
            // At this point it will have the negative initial velocity at this point.
            // So the highest initial velocity allowed to be able to hit the target is
            // the same as the distance to the end of the target.
            (targetY.first..-targetY.first)
                .map { Pos(x, it) }
                .filter { yCanHitTarget(it) }
        }
    }

    fun solvePart1(): Int {
        return findAllShots().maxOf { maxY(it.y) }
    }

    fun solvePart2(): Int {
        return findAllShots().size
    }
}