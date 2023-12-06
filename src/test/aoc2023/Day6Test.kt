package test.aoc2023

import aoc2023.Day6
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day6Test {
    private val exampleInput = """
        Time:      7  15   30
        Distance:  9  40  200
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day6 = Day6(exampleInput)
        assertEquals(288, day6.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day6 = Day6(resourceAsList("2023/day6.txt"))
        assertEquals(771628, day6.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day6 = Day6(exampleInput)
        assertEquals(71503, day6.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day6 = Day6(resourceAsList("2023/day6.txt"))
        assertEquals(27363861, day6.solvePart2())
    }
}