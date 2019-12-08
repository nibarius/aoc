package test.aoc2019

import aoc2019.Day8
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsString

class Day8Test {
    @Test
    fun testPart1Examples() {
        val input = "123456789012"
        assertEquals(1, Day8(input, 3, 2).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals(1820, Day8(resourceAsString("2019/day8.txt"), 25, 6).solvePart1())
    }

    @Test
    fun testPart2Examples() {
        val input = "0222112222120000"
        assertEquals(listOf(0, 1, 1, 0), Day8(input, 2, 2).solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        Day8(resourceAsString("2019/day8.txt"), 25, 6).solvePart2()
    }
}




