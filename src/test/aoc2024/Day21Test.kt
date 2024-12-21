package test.aoc2024

import aoc2024.Day21
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day21Test {
    private val exampleInput = """
        029A
        980A
        179A
        456A
        379A
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day21 = Day21(exampleInput)
        assertEquals(126384, day21.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day21 = Day21(resourceAsList("2024/day21.txt"))
        assertEquals(163920, day21.solvePart1())
    }

    @Test
    fun partTwoRealInput() {
        val day21 = Day21(resourceAsList("2024/day21.txt"))
        assertEquals(204040805018350, day21.solvePart2())
    }
}