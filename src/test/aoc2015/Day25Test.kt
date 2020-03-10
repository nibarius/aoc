package test.aoc2015

import aoc2015.Day25
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day25Test {
    @Test
    fun partOneRealInput() {
        assertEquals(19980801, Day25(resourceAsList("2015/day25.txt")).solvePart1())
    }
}