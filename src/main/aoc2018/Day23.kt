package aoc2018

import java.util.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class Day23(input: List<String>) {

    data class Pos(val x: Int, val y: Int, val z: Int)
    data class Nanobot(val pos: Pos, val range: Int) {
        fun inRange(other: Nanobot): Boolean {
            return abs(pos.x - other.pos.x) +
                    abs(pos.y - other.pos.y) +
                    abs(pos.z - other.pos.z) <= range
        }
    }

    data class Cube(val xRange: IntRange, val yRange: IntRange, val zRange: IntRange,
                    val botsInRangeOfWholeParent: Int, val nanobotsToCheck: List<Nanobot>) {
        // The number of nanobots in range of the cube. If the cube needs further splitting
        // this is an upper limit estimate of maximum possible number of bots in range.
        val botsInRange: Int

        // If the cube doesn't have any partial intersections there is no need to split it
        val needsSplitting get() = botsWithPartialIntersection.isNotEmpty()

        private val botsInRangeOfWholeCube: Int
        private val botsWithPartialIntersection = mutableListOf<Nanobot>()

        init {
            var containedByCount = 0
            nanobotsToCheck.forEach { nanobot ->
                when {
                    nanobot.range >= distanceToFurthestPoint(nanobot.pos) -> {
                        // Case 1: Whole cube is within the range of the current bot
                        containedByCount++
                    }
                    nanobot.range < shortestDistance(nanobot.pos) -> {
                        // Case 2: Whole cube is out of range of the current bot
                        // Just ignore this bot.
                    }
                    else -> {
                        // Case 3: Part of the cube is within range of the current bot
                        botsWithPartialIntersection.add(nanobot)
                    }
                }
            }

            botsInRangeOfWholeCube = containedByCount
            botsInRange = botsInRangeOfWholeParent + botsInRangeOfWholeCube + botsWithPartialIntersection.size
        }


        // Split this cube in up to 8 smaller cubes. Can be fewer than 8 if the cube is
        // too small to split into 8.
        fun split(): List<Cube> {
            fun clamp(range: IntRange) = range.first..max(range.first, range.last)
            fun IntRange.size() = last - first + 1

            if (xRange.size() <= 1 && yRange.size() <= 1 && zRange.size() <= 1) {
                throw RuntimeException("Trying to split a cube too small to split: $this")
            }

            return setOf(
                    Cube(clamp(xRange.first until xRange.first + xRange.size() / 2),
                            clamp(yRange.first until yRange.first + yRange.size() / 2),
                            clamp(zRange.first until zRange.first + zRange.size() / 2),
                            botsInRangeOfWholeParent + botsInRangeOfWholeCube,
                            botsWithPartialIntersection
                    ),
                    Cube((xRange.first + xRange.size() / 2)..xRange.last,
                            clamp(yRange.first until yRange.first + yRange.size() / 2),
                            clamp(zRange.first until zRange.first + zRange.size() / 2),
                            botsInRangeOfWholeParent + botsInRangeOfWholeCube,
                            botsWithPartialIntersection
                    ),
                    Cube(clamp(xRange.first until xRange.first + xRange.size() / 2),
                            (yRange.first + yRange.size() / 2)..yRange.last,
                            clamp(zRange.first until zRange.first + zRange.size() / 2),
                            botsInRangeOfWholeParent + botsInRangeOfWholeCube,
                            botsWithPartialIntersection
                    ),
                    Cube(clamp(xRange.first until xRange.first + xRange.size() / 2),
                            clamp(yRange.first until yRange.first + yRange.size() / 2),
                            (zRange.first + zRange.size() / 2)..zRange.last,
                            botsInRangeOfWholeParent + botsInRangeOfWholeCube,
                            botsWithPartialIntersection
                    ),
                    Cube((xRange.first + xRange.size() / 2)..xRange.last,
                            (yRange.first + yRange.size() / 2)..yRange.last,
                            clamp(zRange.first until zRange.first + zRange.size() / 2),
                            botsInRangeOfWholeParent + botsInRangeOfWholeCube,
                            botsWithPartialIntersection
                    ),
                    Cube((xRange.first + xRange.size() / 2)..xRange.last,
                            clamp(yRange.first until yRange.first + yRange.size() / 2),
                            (zRange.first + zRange.size() / 2)..zRange.last,
                            botsInRangeOfWholeParent + botsInRangeOfWholeCube,
                            botsWithPartialIntersection
                    ),
                    Cube(clamp(xRange.first until xRange.first + xRange.size() / 2),
                            (yRange.first + yRange.size() / 2)..yRange.last,
                            (zRange.first + zRange.size() / 2)..zRange.last,
                            botsInRangeOfWholeParent + botsInRangeOfWholeCube,
                            botsWithPartialIntersection
                    ),
                    Cube((xRange.first + xRange.size() / 2)..xRange.last,
                            (yRange.first + yRange.size() / 2)..yRange.last,
                            (zRange.first + zRange.size() / 2)..zRange.last,
                            botsInRangeOfWholeParent + botsInRangeOfWholeCube,
                            botsWithPartialIntersection
                    )
            ).toList()
        }
        
        // Shortest distance from the given point to the cube, returns 0 if the point is inside the cube.
        fun shortestDistance(from: Pos): Int {
            fun IntRange.shortestDistance(point: Int) =
                    if (point in this) 0
                    else min(abs(point - last), abs(point - first))

            return xRange.shortestDistance(from.x) + yRange.shortestDistance(from.y) + zRange.shortestDistance(from.z)
        }

        // Distance from the given point to the point in the cube that is furthest away from the point
        private fun distanceToFurthestPoint(from: Pos): Int {
            fun IntRange.furthestDistance(point: Int) = max(abs(point - last), abs(point - first))
            return xRange.furthestDistance(from.x) + yRange.furthestDistance(from.y) + zRange.furthestDistance(from.z)
        }
    }

    private val nanobots = input.map { line ->
        val (x, y, z) = line.substringAfter("<").substringBefore(">").split(",")
        val pos = Pos(x.toInt(), y.toInt(), z.toInt())
        val range = line.substringAfter("r=").toInt()
        Nanobot(pos, range)
    }

    fun solvePart1(): Int {
        val strongest = nanobots.maxByOrNull { it.range }!!
        return nanobots.filter { strongest.inRange(it) }.size
    }


    fun solvePart2(): Int {
        val spaces = findSpacesWithMostBotsInRange()
        return spaces.map { it.shortestDistance(Pos(0, 0, 0)) }.minOrNull()!!
    }


    private fun findSpacesWithMostBotsInRange(): Set<Cube> {
        var largestSoFar = -1
        val cubesInRangeOfMostNanobots = mutableSetOf<Cube>()
        val boundingCube = Cube(
                nanobots.minByOrNull { it.pos.x }!!.pos.x..nanobots.maxByOrNull { it.pos.x }!!.pos.x,
                nanobots.minByOrNull { it.pos.y }!!.pos.y..nanobots.maxByOrNull { it.pos.y }!!.pos.y,
                nanobots.minByOrNull { it.pos.z }!!.pos.z..nanobots.maxByOrNull { it.pos.z }!!.pos.z,
                0,
                nanobots
        )

        val toSplit = PriorityQueue<Cube> { a, b -> b.botsInRange - a.botsInRange }
        toSplit.add(boundingCube)
        while (toSplit.isNotEmpty()) {
            val cube = toSplit.poll()
            if (cube.botsInRange >= largestSoFar) {
                // Only split cubes that have a chance of being better than the current record
                cube.split().forEach {subCube ->
                    if (subCube.botsInRange >= largestSoFar) {
                        // Only queue cubes for splitting that have a chance of being better than the current record
                        if (subCube.needsSplitting) {
                            toSplit.add(subCube)
                        } else {
                            if (subCube.botsInRange > largestSoFar) {
                                // New record, throw away previous data
                                cubesInRangeOfMostNanobots.clear()
                            }
                            cubesInRangeOfMostNanobots.add(subCube)
                            largestSoFar = subCube.botsInRange
                        }
                    }
                }
            }
        }
        return cubesInRangeOfMostNanobots
    }
}
