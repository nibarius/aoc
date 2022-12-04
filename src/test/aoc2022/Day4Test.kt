package test.aoc2022

import aoc2022.Day4
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day4Test {
    private val exampleInput = """
        2-4,6-8
        2-3,4-5
        5-7,7-9
        2-8,3-7
        6-6,4-6
        2-6,4-8
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day4 = Day4(exampleInput)
        assertEquals(2, day4.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day4 = Day4(resourceAsList("2022/day4.txt"))
        assertEquals(562, day4.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day4 = Day4(exampleInput)
        assertEquals(4, day4.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day4 = Day4(resourceAsList("2022/day4.txt"))
        assertEquals(924, day4.solvePart2())
    }
}