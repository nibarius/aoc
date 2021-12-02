package test.aoc2021

import aoc2021.Day2
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day2Test {
    private val exampleInput = """
        forward 5
        down 5
        forward 8
        up 3
        down 8
        forward 2
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day2 = Day2(exampleInput)
        assertEquals(150, day2.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day2 = Day2(resourceAsList("2021/day2.txt"))
        assertEquals(1989014, day2.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day2 = Day2(exampleInput)
        assertEquals(900, day2.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day2 = Day2(resourceAsList("2021/day2.txt"))
        assertEquals(2006917119, day2.solvePart2())
    }
}