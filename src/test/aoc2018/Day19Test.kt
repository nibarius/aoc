package test.aoc2018

import aoc2018.Day19
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day19Test {
    private val testInput = listOf(
            "#ip 0",
            "seti 5 0 1",
            "seti 6 0 2",
            "addi 0 1 0",
            "addr 1 2 3",
            "setr 1 0 0",
            "seti 8 0 4",
            "seti 9 0 5"
    )

    @Test
    fun testPart1Example() {
        assertEquals(6, Day19(testInput).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals(1620, Day19(resourceAsList("2018/day19.txt")).solvePart1())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(15827082, Day19(resourceAsList("2018/day19.txt")).solvePart2())
    }
}




