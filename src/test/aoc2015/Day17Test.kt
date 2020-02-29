package test.aoc2015

import aoc2015.Day17
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day17Test {
    @Test
    fun testPartOneExample1() {
        val input = """20, 15, 10, 5, 5""".trimIndent().split(", ")
        assertEquals(4, Day17(input).solvePart1(25))
    }

    @Test
    fun partOneRealInput() {
        assertEquals(1304, Day17(resourceAsList("2015/day17.txt")).solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val input = """20, 15, 10, 5, 5""".trimIndent().split(", ")
        assertEquals(3, Day17(input).solvePart2(25))
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(18, Day17(resourceAsList("2015/day17.txt")).solvePart2())
    }
}