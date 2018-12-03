package test.aoc2018

import aoc2018.Day3
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day3Test {
    private val testInput = listOf(
            "#1 @ 1,3: 4x4",
            "#2 @ 3,1: 4x4",
            "#3 @ 5,5: 2x2"
    )

    @Test
    fun testPart1Examples() {
        assertEquals(4, Day3(testInput).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals(118223, Day3(resourceAsList("2018/day3.txt")).solvePart1())
    }


    @Test
    fun testPart2Examples() {
        assertEquals(3, Day3(testInput).solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(412, Day3(resourceAsList("2018/day3.txt")).solvePart2())
    }
}




