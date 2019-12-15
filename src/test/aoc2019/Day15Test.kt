package test.aoc2019

import aoc2019.Day15
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day15Test {

    @Test
    fun partOneRealInput() {
        assertEquals(214, Day15(resourceAsList("2019/day15.txt", ",")).solvePart1())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(344, Day15(resourceAsList("2019/day15.txt", ",")).solvePart2())
    }
}