package test.aoc2018

import aoc2018.Day5
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList
import resourceAsString

class Day5Test {
    private val testInput = "dabAcCaCBAcCcaDA"

    @Test
    fun testPart1Examples() {
        assertEquals(10, Day5(testInput).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals(10774, Day5(resourceAsString("2018/day5.txt")).solvePart1())
    }


    @Test
    fun testPart2Examples() {
        assertEquals(4, Day5(testInput).solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(5122, Day5(resourceAsString("2018/day5.txt")).solvePart2())
    }
}




