package aoc2025

import Grid
import Pos
import kotlin.time.Duration.Companion.seconds
import kotlin.time.TimeSource


class Day12(input: List<String>) {
    val shapes = input.map { it.split("\n") }.takeWhile { it.first().last() == ':' }
        .map {
            val shape = it.drop(1)
            Grid.parse(shape)
        }

    // list of Pair< Pair< x, y >, List<Presents> >
    val regions = input.last().split("\n")
        .map { line ->
            val size = line.substringBefore("x").toInt() to line.substringAfter("x").substringBefore(":").toInt()
            val presents = line.substringAfter(": ").split(" ").map { it.toInt() }
            size to presents
        }

    // all the different variants of the different shapes
    val allVariantsRaw = shapes.map { shape ->
        Grid.possibleTransformations.map { shape.transformed(it) }.toSet()
    }

    // List of all shapes, each shape has a list of all variants, each variant is a set of Pos
    val allVariants = allVariantsRaw.map {
        it.map { grid ->
            grid.keys.filter { pos -> grid[pos] == '#' }.toSet()
        }
    }


    // returns all possible ways of putting the variant
    private fun placeVariant(current: Set<Pos>, maxSize: Pair<Int, Int>, variant: Set<Pos>): List<Set<Pos>> {
        if (current.isEmpty()) {
            return listOf(variant)
        }
        val maxX = current.maxOf { it.x }
        val maxY = current.maxOf { it.y }
        val candidates = buildList {
            for (x in 0..maxX + 1) {
                if (x + 3 > maxSize.first) {
                    // Each present is 3 wide and high, must be room left for it
                    break
                }
                for (y in 0..maxY + 1) {
                    if (y + 3 > maxSize.second) {
                        break
                    }
                    val union = current + variant.map { Pos(it.x + x, it.y + y) }
                    if (union.size == current.size + variant.size) {
                        add(union)
                    }
                }
            }
        }

        return candidates
    }

    // Input: current grid, max size, remaining shapes
    // if remaining shapes are 0, return OK
    // try each remaining shape
    //    try each variant of the shape
    //       place it so that it makes the grid grow as little as possible
    //       if new grid is within max size:
    //         call again with the new grid and remaining pieces
    //         if OK return, else continue
    // return BAD
    private fun placePresents(current: Set<Pos>, maxSize: Pair<Int, Int>, remainingShapes: List<Int>, deadline: TimeSource.Monotonic.ValueTimeMark): Boolean {
        if (remainingShapes.all { it == 0 }) {
            return true
        }
        if (deadline.hasPassedNow()) {
            throw RuntimeException("Too much time spent")
        }
        remainingShapes.withIndex().filter { it.value != 0 }.forEach { (shape, numLeft) ->
            for (variant in allVariants[shape]) {
                val possiblePlacements = placeVariant(current, maxSize, variant)
                possiblePlacements.forEach { nextRegion ->
                    val nextRemainingShapes = remainingShapes.toMutableList().apply { this[shape] = numLeft - 1 }
                    val result = placePresents(nextRegion, maxSize, nextRemainingShapes, deadline)
                    if (result) {
                        return true
                    }
                }
            }
        }

        return false
    }

    private val timeSource = TimeSource.Monotonic

    // presents can be rotated and flipped ==> each present have 8 different variants
    // 6 different presents ==> 48 different variants in total
    // Algorithm:
    //  - have an initial shape
    //  - take one of the remaining shapes that makes it grow as little as possible
    //  - repeat until out of space or all is placed
    //  - if out of space, go back and try another shape (DFS)
    //  - likely good with memory of some kind
    fun solvePart1(): Int {
        return regions.count { region ->
            val deadline = timeSource.markNow() + 30.seconds
            try {
                placePresents(emptySet(), region.first, region.second, deadline)
            } catch (e: RuntimeException) {
                println("Timeout for $region")
                false
            } catch (e: OutOfMemoryError) {
                println("Out of memory for $region")
                false
            } catch (e: Exception) {
                println("some other exception for $region\n$e")
                false
            }

        }
    }

    // 253
    fun solvePart2(): Int {
        return -1
    }
}