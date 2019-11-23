package test.aoc2019

import aoc2019.Day1
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day1Test {
    @Test
    fun testPart1Examples() {
        assertEquals(0, Day1(listOf()).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals(0, Day1(resourceAsList("2019/day1.txt")).solvePart1())
    }

    @Test
    fun testPart2Examples() {
        assertEquals(0, Day1(listOf()).solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(0, Day1(resourceAsList("2019/day1.txt")).solvePart2())
    }
}




