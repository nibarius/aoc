package test.aoc2019

import aoc2019.Day2
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day2Test {
    @Test
    fun partOneRealInput() {
        assertEquals(6627023, Day2(resourceAsList("2019/day2.txt", ",")).solvePart1())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(4019, Day2(resourceAsList("2019/day2.txt", ",")).solvePart2())
    }
}
