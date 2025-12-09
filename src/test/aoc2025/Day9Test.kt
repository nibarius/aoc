package test.aoc2025

import aoc2025.Day9
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day9Test {
    private val exampleInput = """
        7,1
        11,1
        11,7
        9,7
        9,5
        2,5
        2,3
        7,3
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day9 = Day9(exampleInput)
        assertEquals(50, day9.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day9 = Day9(resourceAsList("2025/day9.txt"))
        assertEquals(4771508457, day9.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day9 = Day9(exampleInput)
        assertEquals(24, day9.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day9 = Day9(resourceAsList("2025/day9.txt"))
        assertEquals(1539809693, day9.solvePart2())
    }
}