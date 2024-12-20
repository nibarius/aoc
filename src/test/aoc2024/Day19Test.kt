package test.aoc2024

import aoc2024.Day19
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day19Test {
    private val exampleInput = """
        r, wr, b, g, bwu, rb, gb, br

        brwrr
        bggr
        gbbr
        rrbgbr
        ubwu
        bwurrg
        brgr
        bbrgwb
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day19 = Day19(exampleInput)
        assertEquals(6, day19.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day19 = Day19(resourceAsList("2024/day19.txt"))
        assertEquals(319, day19.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day19 = Day19(exampleInput)
        assertEquals(16, day19.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day19 = Day19(resourceAsList("2024/day19.txt"))
        assertEquals(692575723305545, day19.solvePart2())
    }
}