package test.aoc2015

import aoc2015.Day2
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day2Test {
    @Test
    fun testPartOneExample1() {
        val input = """2x3x4""".trimIndent().split("\n")
        assertEquals(58, Day2(input).solvePart1())
    }

    @Test
    fun testPartOneExample2() {
        val input = """1x1x10""".trimIndent().split("\n")
        assertEquals(43, Day2(input).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals(1606483, Day2(resourceAsList("2015/day2.txt")).solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val input = """2x3x4""".trimIndent().split("\n")
        assertEquals(34, Day2(input).solvePart2())
    }

    @Test
    fun testPartTwoExample2() {
        val input = """1x1x10""".trimIndent().split("\n")
        assertEquals(14, Day2(input).solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(3842356, Day2(resourceAsList("2015/day2.txt")).solvePart2())
    }
}