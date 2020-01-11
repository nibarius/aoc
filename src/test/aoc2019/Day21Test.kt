package test.aoc2019

import aoc2019.Day21
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day21Test {

    @Test
    fun partOneRealInput() {
        assertEquals(19358870, Day21(resourceAsList("2019/day21.txt", ",")).solvePart1())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(1143356492, Day21(resourceAsList("2019/day21.txt", ",")).solvePart2())
    }
}