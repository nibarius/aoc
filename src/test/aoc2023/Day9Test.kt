package test.aoc2023

import aoc2023.Day9
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day9Test {
    private val exampleInput = """
        0 3 6 9 12 15
        1 3 6 10 15 21
        10 13 16 21 30 45
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day9 = Day9(exampleInput)
        assertEquals(114, day9.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day9 = Day9(resourceAsList("2023/day9.txt"))
        assertEquals(1882395907, day9.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day9 = Day9(exampleInput)
        assertEquals(2, day9.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day9 = Day9(resourceAsList("2023/day9.txt"))
        assertEquals(1005, day9.solvePart2())
    }
}