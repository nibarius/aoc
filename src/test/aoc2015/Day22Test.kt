package test.aoc2015

import aoc2015.Day22
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day22Test {
    @Test
    fun partOneRealInput() {
        assertEquals(953, Day22(resourceAsList("2015/day22.txt")).solvePart1())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(1289, Day22(resourceAsList("2015/day22.txt")).solvePart2())
    }
}