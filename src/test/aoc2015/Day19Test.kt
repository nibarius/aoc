package test.aoc2015

import aoc2015.Day19
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day19Test {
    @Test
    fun testPartOneExample1() {
        val input = """
            H => HO
            H => OH
            O => HH
            
            HOH
        """.trimIndent().split("\n")
        assertEquals(4, Day19(input).solvePart1())
    }

    @Test
    fun testPartOneExample2() {
        val input = """
            H => HO
            H => OH
            O => HH
            
            HOHOHO
        """.trimIndent().split("\n")
        assertEquals(7, Day19(input).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals(576, Day19(resourceAsList("2015/day19.txt")).solvePart1())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(207, Day19(resourceAsList("2015/day19.txt")).solvePart2())
    }
}