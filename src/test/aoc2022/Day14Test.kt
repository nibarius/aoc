package test.aoc2022

import aoc2022.Day14
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day14Test {
    private val exampleInput = """
        498,4 -> 498,6 -> 496,6
        503,4 -> 502,4 -> 502,9 -> 494,9
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day14 = Day14(exampleInput)
        assertEquals(24, day14.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day14 = Day14(resourceAsList("2022/day14.txt"))
        assertEquals(979, day14.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day14 = Day14(exampleInput)
        assertEquals(93, day14.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day14 = Day14(resourceAsList("2022/day14.txt"))
        assertEquals(29044, day14.solvePart2())
    }
}