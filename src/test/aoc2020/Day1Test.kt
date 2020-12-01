package test.aoc2020

import aoc2020.Day1
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day1Test {
    private val example = """
            1721
            979
            366
            299
            675
            1456
        """.trimIndent().split("\n")

    private val day1 = Day1(resourceAsList("2020/day1.txt"))
    private val day1Example = Day1(example)

    @Test
    fun testPartOneExample1() {
        assertEquals(514579, day1Example.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals(437931, day1.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        assertEquals(241861950, day1Example.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(157667328, day1.solvePart2())
    }
}