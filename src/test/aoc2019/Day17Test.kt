package test.aoc2019

import aoc2019.Day17
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day17Test {

    @Test
    fun partOneRealInput() {
        assertEquals(4372, Day17(resourceAsList("2019/day17.txt", ",")).solvePart1())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(945911, Day17(resourceAsList("2019/day17.txt", ",")).solvePart2())
    }
}