package test.aoc2020

import aoc2020.Day5
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day5Test {
    private val exampleInput = """FBFBBFFRLR""".trimIndent().split("\n")
    private val exampleDay5 = Day5(exampleInput)
    private val day5 = Day5(resourceAsList("2020/day5.txt"))

    @Test
    fun testPartOneExample1() {
        assertEquals(357, exampleDay5.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals(880, day5.solvePart1())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(731, day5.solvePart2())
    }
}