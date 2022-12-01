package test.aoc2022

import aoc2022.Day1
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList
import resourceSplitOnBlankLines

class Day1Test {
    private val exampleInput = """
        1000
        2000
        3000

        4000

        5000
        6000

        7000
        8000
        9000

        10000
    """.trimIndent().split("\n\n")

    @Test
    fun testPartOneExample1() {
        val day1 = Day1(exampleInput)
        assertEquals(24000, day1.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day1 = Day1(resourceSplitOnBlankLines("2022/day1.txt"))
        assertEquals(69836, day1.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day1 = Day1(exampleInput)
        assertEquals(45000, day1.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day1 = Day1(resourceSplitOnBlankLines("2022/day1.txt"))
        assertEquals(207968, day1.solvePart2())
    }
}