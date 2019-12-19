package test.aoc2019

import aoc2019.Day19
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day19Test {
    @Test
    fun partOneRealInput() {
        assertEquals(166, Day19(resourceAsList("2019/day19.txt", ",")).solvePart1())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(3790981, Day19(resourceAsList("2019/day19.txt", ",")).solvePart2())
    }
}