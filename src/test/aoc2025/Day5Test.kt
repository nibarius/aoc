package test.aoc2025

import aoc2025.Day5
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceSplitOnBlankLines

class Day5Test {
    private val exampleInput = """
        3-5
        10-14
        16-20
        12-18

        1
        5
        8
        11
        17
        32
    """.trimIndent().split("\n\n")

    @Test
    fun testPartOneExample1() {
        val day5 = Day5(exampleInput)
        assertEquals(3, day5.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day5 = Day5(resourceSplitOnBlankLines("2025/day5.txt"))
        assertEquals(513, day5.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day5 = Day5(exampleInput)
        assertEquals(14, day5.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day5 = Day5(resourceSplitOnBlankLines("2025/day5.txt"))
        assertEquals(339668510830757, day5.solvePart2())
    }
}