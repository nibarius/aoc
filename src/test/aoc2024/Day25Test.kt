package test.aoc2024

import aoc2024.Day25
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceSplitOnBlankLines

class Day25Test {
    private val exampleInput = """
        #####
        .####
        .####
        .####
        .#.#.
        .#...
        .....

        #####
        ##.##
        .#.##
        ...##
        ...#.
        ...#.
        .....

        .....
        #....
        #....
        #...#
        #.#.#
        #.###
        #####

        .....
        .....
        #.#..
        ###..
        ###.#
        ###.#
        #####

        .....
        .....
        .....
        #....
        #.#..
        #.#.#
        #####
    """.trimIndent().split("\n\n")

    @Test
    fun testPartOneExample1() {
        val day25 = Day25(exampleInput)
        assertEquals(3, day25.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day25 = Day25(resourceSplitOnBlankLines("2024/day25.txt"))
        assertEquals(3483, day25.solvePart1())
    }
}