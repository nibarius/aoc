package test.aoc2019

import aoc2019.Day9
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day9Test {
    @Test
    fun partOneRealInput() {
        assertEquals(3507134798, Day9(resourceAsList("2019/day9.txt", ",")).solvePart1())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(84513, Day9(resourceAsList("2019/day9.txt", ",")).solvePart2())
    }
}