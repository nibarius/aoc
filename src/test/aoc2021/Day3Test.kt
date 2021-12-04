package test.aoc2021

import aoc2021.Day3
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day3Test {
    private val exampleInput = """
        00100
        11110
        10110
        10111
        10101
        01111
        00111
        11100
        10000
        11001
        00010
        01010
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day3 = Day3(exampleInput)
        assertEquals(198, day3.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day3 = Day3(resourceAsList("2021/day3.txt"))
        assertEquals(3958484, day3.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day3 = Day3(exampleInput)
        assertEquals(230, day3.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day3 = Day3(resourceAsList("2021/day3.txt"))
        assertEquals(1613181, day3.solvePart2())
    }
}