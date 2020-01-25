package test.aoc2019

import aoc2019.Day24
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day24Test {

    @Test
    fun testPartOneExample1() {
        val input = """
            ....#
            #..#.
            #..##
            ..#..
            #....
        """.trimIndent().split("\n")
        assertEquals(2129920, Day24(input).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals(1151290, Day24(resourceAsList("2019/day24.txt")).solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val input = """
            ....#
            #..#.
            #..##
            ..#..
            #....
        """.trimIndent().split("\n")
        assertEquals(99, Day24(input).solvePart2(10))
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(1953, Day24(resourceAsList("2019/day24.txt")).solvePart2(200))
    }
}