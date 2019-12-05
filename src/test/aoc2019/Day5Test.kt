package test.aoc2019

import aoc2019.Day5
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day5Test {

    @Test
    fun partOneRealInput() {
        assertEquals(7692125, Day5(resourceAsList("2019/day5.txt", ",")).solvePart1())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(14340395, Day5(resourceAsList("2019/day5.txt", ",")).solvePart2())
    }
}