package test.aoc2019

import aoc2019.Day13
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day13Test {

    @Test
    fun partOneRealInput() {
        assertEquals(258, Day13(resourceAsList("2019/day13.txt", ",")).solvePart1())
    }


    @Test
    fun partTwoRealInput() {
        assertEquals(12765, Day13(resourceAsList("2019/day13.txt", ",")).solvePart2())
    }
}