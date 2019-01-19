package test.aoc2018

import aoc2018.Day21
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day21Test {

    @Test
    fun partOneRealInput() {
        assertEquals(3345459, Day21(resourceAsList("2018/day21.txt")).solvePart1())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(5857354, Day21(resourceAsList("2018/day21.txt")).solvePart2())
    }
}




