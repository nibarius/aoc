package test.aoc2023

import aoc2023.Day13
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceSplitOnBlankLines

class Day13Test {
    private val exampleInput = """
        #.##..##.
        ..#.##.#.
        ##......#
        ##......#
        ..#.##.#.
        ..##..##.
        #.#.##.#.

        #...##..#
        #....#..#
        ..##..###
        #####.##.
        #####.##.
        ..##..###
        #....#..#
    """.trimIndent().split("\n\n")

    @Test
    fun testPartOneExample1() {
        val day13 = Day13(exampleInput)
        assertEquals(405, day13.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day13 = Day13(resourceSplitOnBlankLines("2023/day13.txt"))
        assertEquals(36448, day13.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day13 = Day13(exampleInput)
        assertEquals(400, day13.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day13 = Day13(resourceSplitOnBlankLines("2023/day13.txt"))
        assertEquals(35799, day13.solvePart2())
    }
}