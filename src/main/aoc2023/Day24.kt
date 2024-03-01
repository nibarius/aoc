package aoc2023

import MyMath
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.math.sign

class Day24(input: List<String>) {

    private val hailstones = input.map { line ->
        val regex = """(\d+), (\d+), (\d+) @ (-?\d+), (-?\d+), (-?\d+)""".toRegex()
        val (x, y, z, dx, dy, dz) = regex.matchEntire(line)!!.destructured
        Hailstone(
            x.toBigInteger(), y.toBigInteger(), z.toBigInteger(),
            dx.toBigInteger(), dy.toBigInteger(), dz.toBigInteger()
        )
    }

    private data class Hailstone(
        val x: BigInteger, val y: BigInteger, val z: BigInteger,
        val dx: BigInteger, val dy: BigInteger, val dz: BigInteger
    ) {
        val line2d = Line2D(x.toBigDecimal(), y.toBigDecimal(), (x + dx).toBigDecimal(), (y + dy).toBigDecimal())
        fun isInFuture(point: Pair<Float, Float>): Boolean {
            return (point.first - x.toFloat()).sign.toInt() == dx.signum()
        }
    }

    private data class Line2D(val x1: BigDecimal, val y1: BigDecimal, val x2: BigDecimal, val y2: BigDecimal)

    /**
     * Return the intersection of two lines in two-dimensional space where the first line is defined
     * by the two points (x1, y1) and (x2, y2) and the second line by the points (x3, y3) and (x4, y4)
     * Parallel lines throws a division by zero exception.
     *
     * From: https://en.wikipedia.org/wiki/Line–line_intersection#Given_two_points_on_each_line
     */
    private fun get2dIntersection(l1: Line2D, l2: Line2D): Pair<Float, Float> {
        val (x1, y1, x2, y2) = l1
        val (x3, y3, x4, y4) = l2
        val xNumerator = (x1 * y2 - y1 * x2) * (x3 - x4) - (x1 - x2) * (x3 * y4 - y3 * x4)
        val denominator = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4)
        val yNumerator = (x1 * y2 - y1 * x2) * (y3 - y4) - (y1 - y2) * (x3 * y4 - y3 * x4)
        return Pair((xNumerator / denominator).toFloat(), (yNumerator / denominator).toFloat())
    }

    // count number of hailstones that intersect each others paths in the future
    private fun intersections(min: Long, max: Long): Int {
        val lines = hailstones
        var count = 0
        for (i in lines.indices) {
            for (j in i + 1 until lines.size) {
                val a = lines[i]
                val b = lines[j]
                try {
                    val res = get2dIntersection(a.line2d, b.line2d)
                    if (a.isInFuture(res) && b.isInFuture(res) && isInTestArea(res, min, max)) {
                        count++
                    }
                } catch (e: ArithmeticException) {
                    // lines are parallel or overlapping
                }
            }
        }
        return count
    }

    private fun isInTestArea(point: Pair<Float, Float>, min: Long, max: Long): Boolean {
        return point.first >= min && point.second >= min && point.first <= max && point.second <= max
    }

    fun solvePart1(min: Long = 200_000_000_000_000, max: Long = 400_000_000_000_000): Int {
        return intersections(min, max)
    }

    // Set up three equations (with 6 unknowns) for two hailstones as described in
    // https://www.reddit.com/r/adventofcode/comments/18q40he/2023_day_24_part_2_a_straightforward_nonsolver/
    // Calling this with h1/h2 and h2/h3 gives in total 6 equations and 6 unknown which can be solved.
    private fun getEquationsFor(h1: Hailstone, h2: Hailstone): List<Array<BigInteger>> {
        return listOf(
            arrayOf(
                h2.dy - h1.dy,
                h1.dx - h2.dx,
                BigInteger.ZERO,
                h1.y - h2.y,
                h2.x - h1.x,
                BigInteger.ZERO,
                h2.x * h2.dy - h2.y * h2.dx - h1.x * h1.dy + h1.y * h1.dx
            ),
            arrayOf(
                h2.dz - h1.dz,
                BigInteger.ZERO,
                h1.dx - h2.dx,
                h1.z - h2.z,
                BigInteger.ZERO,
                h2.x - h1.x,
                h2.x * h2.dz - h2.z * h2.dx - h1.x * h1.dz + h1.z * h1.dx
            ),
            arrayOf(
                BigInteger.ZERO,
                h1.dz - h2.dz,
                h2.dy - h1.dy,
                BigInteger.ZERO,
                h2.z - h1.z,
                h1.y - h2.y,
                h2.z * h2.dy - h2.y * h2.dz - h1.z * h1.dy + h1.y * h1.dz
            )
        )
    }

    fun solvePart2(): Long {
        val biEquations = getEquationsFor(hailstones[0], hailstones[1]) +
                getEquationsFor(hailstones[1], hailstones[2])
        // Solve the equations using Gaussian elimination
        return MyMath.gaussianElimination(biEquations).take(3).sum()
    }

}