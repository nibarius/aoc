package test.aoc2019

import aoc2019.Day23
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day23Test {
    @Test
    fun partOneRealInput() {
        assertEquals(23266, Day23(resourceAsList("2019/day23.txt", ",")).solvePart1())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(17493, Day23(resourceAsList("2019/day23.txt", ",")).solvePart2())
    }
}