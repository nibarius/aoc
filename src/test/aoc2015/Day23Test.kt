package test.aoc2015

import aoc2015.Day23
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day23Test {

    @Test
    fun partOneRealInput() {
        assertEquals(184, Day23(resourceAsList("2015/day23.txt")).solvePart1())
    }
    
    @Test
    fun partTwoRealInput() {
        assertEquals(231, Day23(resourceAsList("2015/day23.txt")).solvePart2())
    }
}