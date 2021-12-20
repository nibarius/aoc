package aoc2021

import AMap
import Pos

class Day20(input: List<String>) {

    private val algorithm = input.first()
    private val initialImage = AMap.parse(input.drop(2))

    private val allDeltas = buildList {
        for (y in -1..1) {
            for (x in -1..1) {
                add(Pos(x, y))
            }
        }
    }

    private fun lookupPixelFor(pos: Pos, image: AMap, default: Char): Char {
        val offset = allDeltas.map { image.getOrDefault(pos + it, default) }
            .joinToString("") { if (it == '#') "1" else "0" }
            .toInt(2)

        return algorithm[offset]
    }

    private fun enhance(image: AMap, default: Char): AMap {
        val yr = image.yRange().first - 1..image.yRange().last + 1
        val xr = image.xRange().first - 1..image.xRange().last + 1
        val enhancedImage = AMap()

        for (y in yr) {
            for (x in xr) {
                enhancedImage[Pos(x, y)] = lookupPixelFor(Pos(x, y), image, default)
            }
        }
        return enhancedImage
    }

    private fun repeatedEnhance(times: Int): Int {
        var next = initialImage
        repeat(times) {
            // Example input algorithm starts with . which means 9 blank pixels results in a blank pixel
            // Real input starts with # and ends with . which means that 9 blank pixels becomes a lit pixel
            // and 9 lit pixels becomes a blank pixel. So everything toward infinity flips color with each
            // iteration. This will not work with an algorithm that start and ends with '#', but there is no
            // such input so that doesn't matter.
            val default = if (algorithm.first() == '#' && it % 2 == 1) '#' else '.'
            next = enhance(next, default)
        }
        return next.values.count { it == '#' }
    }

    // reddit test starts with #, example input starts with .
    fun solvePart1(): Int {
        return repeatedEnhance(2)
    }

    fun solvePart2(): Int {
        return repeatedEnhance(50)
    }
}