package test.aoc2025

import aoc2025.Day4
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day4Test {
    private val exampleInput = """
        ..@@.@@@@.
        @@@.@.@.@@
        @@@@@.@.@@
        @.@@@@..@.
        @@.@@@@.@@
        .@@@@@@@.@
        .@.@.@.@@@
        @.@@@.@@@@
        .@@@@@@@@.
        @.@.@@@.@.
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day4 = Day4(exampleInput)
        assertEquals(13, day4.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day4 = Day4(resourceAsList("2025/day4.txt"))
        assertEquals(1435, day4.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day4 = Day4(exampleInput)
        assertEquals(43, day4.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day4 = Day4(resourceAsList("2025/day4.txt"))
        assertEquals(8623, day4.solvePart2())
    }
}