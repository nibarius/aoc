package test.aoc2018

import aoc2018.Day8
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsString

class Day8Test {
    private val testInput = "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2"


    @Test
    fun testPart1Examples() {
        assertEquals(138, Day8(testInput).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals(36027, Day8(resourceAsString("2018/day8.txt")).solvePart1())
    }


    @Test
    fun testPart2Examples() {
        assertEquals(66, Day8(testInput).solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(23960, Day8(resourceAsString("2018/day8.txt")).solvePart2())
    }
}




