package test.aoc2020

import aoc2020.Day6
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceSplitOnBlankLines

class Day6Test {
    private val exampleInput = """
        abc

        a
        b
        c

        ab
        ac

        a
        a
        a
        a

        b
    """.trimIndent().split("\n\n")

    @Test
    fun testPartOneExample1() {
        val exampleDay6 = Day6(exampleInput)
        assertEquals(11, exampleDay6.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day6 = Day6(resourceSplitOnBlankLines("2020/day6.txt"))
        assertEquals(7120, day6.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val exampleDay6 = Day6(exampleInput)
        assertEquals(6, exampleDay6.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day6 = Day6(resourceSplitOnBlankLines("2020/day6.txt"))
        assertEquals(3570, day6.solvePart2())
    }
}