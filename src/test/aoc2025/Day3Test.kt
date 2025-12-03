package test.aoc2025

import aoc2025.Day3
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day3Test {
    private val exampleInput = """
        987654321111111
        811111111111119
        234234234234278
        818181911112111
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day3 = Day3(exampleInput)
        assertEquals(357, day3.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day3 = Day3(resourceAsList("2025/day3.txt"))
        assertEquals(17085, day3.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day3 = Day3(exampleInput)
        assertEquals(3121910778619, day3.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day3 = Day3(resourceAsList("2025/day3.txt"))
        assertEquals(169408143086082, day3.solvePart2())
    }
}