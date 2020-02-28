package test.aoc2015

import aoc2015.Day16
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day16Test {

    @Test
    fun partOneRealInput() {
        assertEquals(213, Day16(resourceAsList("2015/day16.txt")).solvePart1())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(323, Day16(resourceAsList("2015/day16.txt")).solvePart2())
    }
}