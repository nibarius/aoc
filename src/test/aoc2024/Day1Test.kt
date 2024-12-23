package test.aoc2024

import aoc2024.Day1
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day1Test {
    private val exampleInput = """
        3   4
        4   3
        2   5
        1   3
        3   9
        3   3
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day1 = Day1(exampleInput)
        assertEquals(11, day1.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day1 = Day1(resourceAsList("2024/day1.txt"))
        assertEquals(1258579, day1.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day1 = Day1(exampleInput)
        assertEquals(31, day1.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day1 = Day1(resourceAsList("2024/day1.txt"))
        assertEquals(23981443, day1.solvePart2())
    }
}