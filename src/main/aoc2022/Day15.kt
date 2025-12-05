package aoc2022

import Pos
import intersect
import size
import unionAll
import kotlin.math.abs


class Day15(input: List<String>) {

    data class Sensor(val pos: Pos, val beacon: Pos) {
        /**
         * Gives the range of x coordinates that is visible on the given y coordinate for this sensor. Returns
         * null if there are no x coordinates in range.
         */
        fun xRangeAt(y: Int): IntRange? {
            val xRange = pos.distanceTo(beacon) - abs(pos.y - y)
            if (xRange <= 0) {
                return null
            }
            return pos.x - xRange..pos.x + xRange
        }
    }

    private val sensors: Set<Sensor>
    private val beacons: Set<Pos>

    init {
        val regex = "Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)".toRegex()
        val s = mutableSetOf<Sensor>()
        val b = mutableSetOf<Pos>()
        input.forEach { line ->
            val (sx, sy, bx, by) = regex.find(line)!!.destructured
            val beacon = Pos(bx.toInt(), by.toInt())
            s.add(Sensor(Pos(sx.toInt(), sy.toInt()), beacon))
            b.add(beacon)
        }
        sensors = s
        beacons = b
    }

    fun solvePart1(row: Int): Int {
        val sensorCoverage = sensors
            .mapNotNull { it.xRangeAt(row) } // the ranges for all beacons
            .unionAll() // reduce to the minimal set of ranges

        // The distress beacon can not be where a detected beacon, or sensor is located, so subtract those
        return sensorCoverage.sumOf { it.size() } - beacons.count { it.y == row } - sensors.count { it.pos.y == row }
    }

    fun solvePart2(searchSpace: Int): Long {
        var beacon = Pos(-1, -1)
        val targetRange = 0..searchSpace
        for (y in searchSpace downTo 0) { // the correct location is close to the end, so search backwards
            val coverage = sensors
                .mapNotNull { it.xRangeAt(y) } // the ranges for all beacons
                .unionAll() // reduce to the minimal set of ranges
                .mapNotNull { it.intersect(targetRange) } // clamp the ranges to the area of interest

            if (coverage.size > 1) {
                // More than one range on the same row means that there is a hole and that's where the distress
                // beacon is located at
                beacon = Pos(coverage.first().last + 1, y)
                break
            }
        }
        return beacon.x * 4000000L + beacon.y
    }
}