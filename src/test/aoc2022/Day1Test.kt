package test.aoc2022

import aoc2022.Day1
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day1Test {
    private val exampleInput = """""".trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day1 = Day1(exampleInput)
        assertEquals(0, day1.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day1 = Day1(resourceAsList("2022/day1.txt"))
        assertEquals(0, day1.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day1 = Day1(exampleInput)
        assertEquals(0, day1.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day1 = Day1(resourceAsList("2022/day1.txt"))
        assertEquals(0, day1.solvePart2())
    }
}