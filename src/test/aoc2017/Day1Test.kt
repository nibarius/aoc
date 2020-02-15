package test.aoc2017

import aoc2017.Day1
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day1Test {
    @Test
    fun testPartOneExample1() {
        val input = """""".trimIndent().split("\n")
        assertEquals(0, Day1(input).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals(0, Day1(resourceAsList("2017/day1.txt")).solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val input = """""".trimIndent().split("\n")
        assertEquals(0, Day1(input).solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(0, Day1(resourceAsList("2017/day1.txt")).solvePart2())
    }
}