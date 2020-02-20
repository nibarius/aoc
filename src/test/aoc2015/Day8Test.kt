package test.aoc2015

import aoc2015.Day8
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day8Test {
    @Test
    fun testPartOneExample1() {
        val input = """
            ""
            "abc"
            "aaa\"aaa"
            "\x27"
        """.trimIndent().split("\n")
        assertEquals(12, Day8(input).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals(1371, Day8(resourceAsList("2015/day8.txt")).solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val input = """
            ""
            "abc"
            "aaa\"aaa"
            "\x27"
        """.trimIndent().split("\n")
        assertEquals(19, Day8(input).solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(2117, Day8(resourceAsList("2015/day8.txt")).solvePart2())
    }
}