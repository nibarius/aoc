package test.aoc2018

import aoc2018.Day16
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day16Test {
    private val testInput = listOf(
            "Before: [3, 2, 1, 1]",
            "9 2 1 2",
            "After:  [3, 2, 2, 1]"
    )

    @Test
    fun testPart1Example() {
        assertEquals(1, Day16(testInput).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals(614, Day16(resourceAsList("2018/day16.txt")).solvePart1())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(656, Day16(resourceAsList("2018/day16.txt")).solvePart2())
    }
}




