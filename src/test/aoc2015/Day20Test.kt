package test.aoc2015

import aoc2015.Day20
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsString

class Day20Test {

    @Test
    fun partOneRealInput() {
        assertEquals(831600, Day20(resourceAsString("2015/day20.txt")).solvePart1())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(884520, Day20(resourceAsString("2015/day20.txt")).solvePart2())
    }
}