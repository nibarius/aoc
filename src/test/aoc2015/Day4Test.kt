package test.aoc2015

import aoc2015.Day4
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsString

class Day4Test {
    @Test
    fun testPartOneExample1() {
        val input = """abcdef"""
        assertEquals(609043, Day4(input).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals(282749, Day4(resourceAsString("2015/day4.txt")).solvePart1())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(0, Day4(resourceAsString("2015/day4.txt")).solvePart2())
    }
}