package test.aoc2021

import aoc2021.Day5
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day5Test {
    private val exampleInput = """
        0,9 -> 5,9
        8,0 -> 0,8
        9,4 -> 3,4
        2,2 -> 2,1
        7,0 -> 7,4
        6,4 -> 2,0
        0,9 -> 2,9
        3,4 -> 1,4
        0,0 -> 8,8
        5,5 -> 8,2
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day5 = Day5(exampleInput)
        assertEquals(5, day5.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day5 = Day5(resourceAsList("2021/day5.txt"))
        assertEquals(6687, day5.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day5 = Day5(exampleInput)
        assertEquals(12, day5.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day5 = Day5(resourceAsList("2021/day5.txt"))
        assertEquals(19851, day5.solvePart2())
    }
}