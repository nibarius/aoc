package aoc2018

import increase
import kotlin.math.abs

class Day6(input: List<String>) {
    data class Location(val x: Int, val y: Int) {
        fun distanceTo(other: Location) = abs(x - other.x) + abs(y - other.y)
        fun withinFovUp(other: Location): Boolean {
            return other.y < y // above
                    && other.x - x >= other.y - y // within fov to the left (above the \ line)
                    && other.x - x <= y - other.y // within fov to the right (above the / line)
        }

        fun withinFovLeft(other: Location): Boolean {
            return other.x < x // to the left
                    && other.x - x <= other.y - y // within fov upward (below the \ line)
                    && other.x - x <= y - other.y // within fov downward (above the / line)
        }

        fun withinFovDown(other: Location): Boolean {
            return other.y > y // below
                    && other.x - x >= y - other.y // within fov to the left (below the / line)
                    && other.x - x <= other.y - y // within fov to the right (below the \ line)
        }

        fun withinFovRight(other: Location): Boolean {
            return other.x > x // to the right
                    && other.x - x >= y - other.y // within fov to the top (below the / line)
                    && other.x - x >= other.y - y // within fov to the bottom (above the \ line)
        }
    }

    private val locations = input.map {
        Location(it.substringBefore(",").toInt(), it.substringAfter(", ").toInt())
    }

    private fun findNonInfiniteLocations(locations: List<Location>): List<Location> {
        // To not be infinite there must be at least one other coordinate in each direction
        // within a 90 degree field of vision in the given direction
        return locations.filter { pos ->
            locations.any { pos.withinFovUp(it) }
                    && locations.any { pos.withinFovLeft(it) }
                    && locations.any { pos.withinFovDown(it) }
                    && locations.any { pos.withinFovRight(it) }
        }
    }

    fun solvePart1(): Int {
        val xRange = locations.minByOrNull { it.x }!!.x..locations.maxByOrNull { it.x }!!.x
        val yRange = locations.minByOrNull { it.y }!!.y..locations.maxByOrNull { it.y }!!.y
        val finiteLocations = findNonInfiniteLocations(locations)
        val areas = mutableMapOf<Location, Int>()
        for (y in yRange) {
            for (x in xRange) {
                val locationByDistance = locations.groupBy { it.distanceTo(Location(x, y)) }.toSortedMap()
                val closest = locationByDistance[locationByDistance.firstKey()]!!
                if (closest.size == 1 && finiteLocations.contains(closest.first())) {
                    // Current coordinate is closest to a single location and it has a finite area
                    // increase it's area by one.
                    areas.increase(closest.first())
                }
            }
        }
        return areas.toList().maxByOrNull { it.second }!!.second
    }

    fun solvePart2(totalDistance: Int): Int {
        val xRange = locations.minByOrNull { it.x }!!.x..locations.maxByOrNull { it.x }!!.x
        val yRange = locations.minByOrNull { it.y }!!.y..locations.maxByOrNull { it.y }!!.y
        var safeArea = 0
        for (y in yRange) {
            for (x in xRange) {
                if (locations.sumBy { it.distanceTo(Location(x, y)) } < totalDistance) {
                    safeArea++
                }
            }
        }
        return safeArea
    }
}