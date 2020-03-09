package test.aoc2015

import aoc2015.Day24
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day24Test {
    @Test
    fun testPartOneExample1() {
        val input = """
            1
            2
            3
            4
            5
            7
            8
            9
            10
            11
        """.trimIndent().split("\n")
        assertEquals(99, Day24(input).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals(11266889531, Day24(resourceAsList("2015/day24.txt")).solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val input = """
            1
            2
            3
            4
            5
            7
            8
            9
            10
            11
        """.trimIndent().split("\n")
        assertEquals(44, Day24(input).solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(77387711, Day24(resourceAsList("2015/day24.txt")).solvePart2())
    }
}