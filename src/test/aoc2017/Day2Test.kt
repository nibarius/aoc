package test.aoc2017

import aoc2017.Day2
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day2Test {
    @Test
    fun testPartOneExample1() {
        val exampleInput = """
            5 1 9 5
            7 5 3
            2 4 6 8
        """.trimIndent().split("\n")
        val day2 = Day2(exampleInput)
        assertEquals(18, day2.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day2 = Day2(resourceAsList("2017/day2.txt"))
        assertEquals(45972, day2.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val exampleInput = """
            5 9 2 8
            9 4 7 3
            3 8 6 5
        """.trimIndent().split("\n")
        val day2 = Day2(exampleInput)
        assertEquals(9, day2.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day2 = Day2(resourceAsList("2017/day2.txt"))
        assertEquals(326, day2.solvePart2())
    }
}