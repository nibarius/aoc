package test.aoc2020

import aoc2020.Day9
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day9Test {
    private val exampleInput = """
        35
        20
        15
        25
        47
        40
        62
        55
        65
        95
        102
        117
        150
        182
        127
        219
        299
        277
        309
        576
    """.trimIndent().split("\n")


    @Test
    fun testPartOneExample1() {
        val day9 = Day9(exampleInput)
        assertEquals(127, day9.solvePart1(5))
    }

    @Test
    fun partOneRealInput() {
        val day9 = Day9(resourceAsList("2020/day9.txt"))
        assertEquals(675280050, day9.solvePart1(25))
    }

    @Test
    fun testPartTwoExample1() {
        val day9 = Day9(exampleInput)
        assertEquals(62, day9.solvePart2(127))
    }

    @Test
    fun partTwoRealInput() {
        val day9 = Day9(resourceAsList("2020/day9.txt"))
        assertEquals(96081673, day9.solvePart2(675280050))
    }
}