package test.aoc2015

import aoc2015.Day6
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day6Test {
    @Test
    fun testPartOneExample1() {
        val input = """turn on 0,0 through 999,999""".trimIndent().split("\n")
        assertEquals(1_000_000, Day6(input).solvePart1())
    }

    @Test
    fun testPartOneExample2() {
        val input = """toggle 0,0 through 999,0""".trimIndent().split("\n")
        assertEquals(1000, Day6(input).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals(377891, Day6(resourceAsList("2015/day6.txt")).solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val input = """turn on 0,0 through 0,0""".trimIndent().split("\n")
        assertEquals(1, Day6(input).solvePart2())
    }

    @Test
    fun testPartTwoExample2() {
        val input = """toggle 0,0 through 999,999""".trimIndent().split("\n")
        assertEquals(2000000, Day6(input).solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(14110788, Day6(resourceAsList("2015/day6.txt")).solvePart2())
    }
}