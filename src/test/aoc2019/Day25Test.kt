package test.aoc2019

import aoc2019.Day25
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day25Test {
    @Test
    fun partOneRealInput() {
        assertEquals(16810049, Day25(resourceAsList("2019/day25.txt", ",")).solvePart1())
    }
}