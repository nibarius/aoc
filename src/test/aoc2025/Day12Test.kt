package test.aoc2025

import aoc2025.Day12
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList
import resourceSplitOnBlankLines

class Day12Test {
    private val exampleInput = """
        0:
        ###
        ##.
        ##.

        1:
        ###
        ##.
        .##

        2:
        .##
        ###
        ##.

        3:
        ##.
        ###
        ##.

        4:
        ###
        #..
        ###

        5:
        ###
        .#.
        ###

        4x4: 0 0 0 0 2 0
        12x5: 1 0 1 0 2 2
        12x5: 1 0 1 0 3 2
    """.trimIndent().split("\n\n")

    @Test
    fun testPartOneExample1() {
        val day12 = Day12(exampleInput)
        assertEquals(2, day12.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day12 = Day12(resourceSplitOnBlankLines("2025/day12.txt"))
        assertEquals(0, day12.solvePart1())
    }
/*
    @Test
    fun testPartTwoExample1() {
        val day12 = Day12(exampleInput)
        assertEquals(0, day12.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day12 = Day12(resourceAsList("2025/day12.txt"))
        assertEquals(0, day12.solvePart2())
    }*/
}