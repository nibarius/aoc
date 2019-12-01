package test.aoc2019

import aoc2019.Day1
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day1Test {
    @Test
    fun testPart1Examples() {
        assertEquals(2, Day1(listOf("12")).solvePart1())
        assertEquals(2, Day1(listOf("14")).solvePart1())
        assertEquals(654, Day1(listOf("1969")).solvePart1())
        assertEquals(33583, Day1(listOf("100756")).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals(3325342, Day1(resourceAsList("2019/day1.txt")).solvePart1())
    }

    @Test
    fun testPart2Examples() {
        assertEquals(2, Day1(listOf("14")).solvePart2())
        assertEquals(966, Day1(listOf("1969")).solvePart2())
        assertEquals(50346, Day1(listOf("100756")).solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(4985158, Day1(resourceAsList("2019/day1.txt")).solvePart2())
    }
}




