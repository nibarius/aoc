package test.aoc2015

import aoc2015.Day9
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day9Test {
    @Test
    fun testPartOneExample1() {
        val input = """
            London to Dublin = 464
            London to Belfast = 518
            Dublin to Belfast = 141
        """.trimIndent().split("\n")
        assertEquals(605, Day9(input).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals(251, Day9(resourceAsList("2015/day9.txt")).solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val input = """
            London to Dublin = 464
            London to Belfast = 518
            Dublin to Belfast = 141
            """.trimIndent().split("\n")
        assertEquals(982, Day9(input).solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(898, Day9(resourceAsList("2015/day9.txt")).solvePart2())
    }
}