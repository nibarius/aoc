package test.aoc2019

import aoc2019.Day4
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsString

class Day4Test {
    @Test
    fun testPart1Examples() {
        assertEquals(1, Day4("111111-111111").solvePart1())
        assertEquals(0, Day4("223450-223450").solvePart1())
        assertEquals(0, Day4("123789-123789").solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals(511, Day4(resourceAsString("2019/day4.txt")).solvePart1())
    }

    @Test
    fun testPart2Examples() {
        assertEquals(1, Day4("112233-112233").solvePart2())
        assertEquals(0, Day4("123444-123444").solvePart2())
        assertEquals(1, Day4("111122-111122").solvePart2())
    }

    @Test
    fun part2ValidThenInvalid() {
        assertEquals(1, Day4("112333-112333").solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(316, Day4(resourceAsString("2019/day4.txt")).solvePart2())
    }
}




