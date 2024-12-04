package test.aoc2024

import aoc2024.Day4
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day4Test {
    private val exampleInput = """
        MMMSXXMASM
        MSAMXMSMSA
        AMXSXMAAMM
        MSAMASMSMX
        XMASAMXAMM
        XXAMMXXAMA
        SMSMSASXSS
        SAXAMASAAA
        MAMMMXMMMM
        MXMXAXMASX
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day4 = Day4(exampleInput)
        assertEquals(18, day4.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day4 = Day4(resourceAsList("2024/day4.txt"))
        assertEquals(2462, day4.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day4 = Day4(exampleInput)
        assertEquals(9, day4.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day4 = Day4(resourceAsList("2024/day4.txt"))
        assertEquals(1877, day4.solvePart2())
    }
}