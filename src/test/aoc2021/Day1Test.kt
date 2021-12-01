package test.aoc2021

import aoc2021.Day1
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day1Test {
    private val exampleInput = """
        199
        200
        208
        210
        200
        207
        240
        269
        260
        263
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day1 = Day1(exampleInput)
        assertEquals(7, day1.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day1 = Day1(resourceAsList("2021/day1.txt"))
        assertEquals(1688, day1.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day1 = Day1(exampleInput)
        assertEquals(5, day1.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day1 = Day1(resourceAsList("2021/day1.txt"))
        assertEquals(1728, day1.solvePart2())
    }
}