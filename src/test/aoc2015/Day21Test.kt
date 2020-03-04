package test.aoc2015

import aoc2015.Day21
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day21Test {

    @Test
    fun partOneRealInput() {
        assertEquals(121, Day21(resourceAsList("2015/day21.txt")).solvePart1())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(201, Day21(resourceAsList("2015/day21.txt")).solvePart2())
    }
}