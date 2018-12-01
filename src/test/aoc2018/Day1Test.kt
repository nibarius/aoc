package test.aoc2018

import aoc2018.Day1
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day1Test {
    @Test
    fun testPart1Examples() {
        assertEquals(3, Day1(listOf("+1", "+1", "+1")).solvePart1())
        assertEquals(0, Day1(listOf("+1", "+1", "-2")).solvePart1())
        assertEquals(-6, Day1(listOf("-1", "-2", "-3")).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals(472, Day1(resourceAsList("2018/day1.txt")).solvePart1())
    }

    @Test
    fun testPart2Examples() {
        assertEquals(0, Day1(listOf("+1", "-1")).solvePart2())
        assertEquals(10, Day1(listOf("+3", "+3", "+4", "-2", "-4")).solvePart2())
        assertEquals(5, Day1(listOf("-6", "+3", "+8", "+5", "-6")).solvePart2())
        assertEquals(14, Day1(listOf("+7", "+7", "-2", "-7", "-4")).solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(66932, Day1(resourceAsList("2018/day1.txt")).solvePart2())
    }
}




