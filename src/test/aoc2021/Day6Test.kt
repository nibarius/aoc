package test.aoc2021

import aoc2021.Day6
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day6Test {
    private val exampleInput = """3,4,3,1,2""".trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day6 = Day6(exampleInput)
        assertEquals(5934, day6.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day6 = Day6(resourceAsList("2021/day6.txt"))
        assertEquals(393019, day6.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day6 = Day6(exampleInput)
        assertEquals(26984457539, day6.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day6 = Day6(resourceAsList("2021/day6.txt"))
        assertEquals(1757714216975, day6.solvePart2())
    }
}