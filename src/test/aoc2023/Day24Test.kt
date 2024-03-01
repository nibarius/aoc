package test.aoc2023

import aoc2023.Day24
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day24Test {
    private val exampleInput = """
        19, 13, 30 @ -2, 1, -2
        18, 19, 22 @ -1, -1, -2
        20, 25, 34 @ -2, -2, -4
        12, 31, 28 @ -1, -2, -1
        20, 19, 15 @ 1, -5, -3
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day24 = Day24(exampleInput)
        assertEquals(2, day24.solvePart1(7, 27))
    }

    @Test
    fun partOneRealInput() {
        val day24 = Day24(resourceAsList("2023/day24.txt"))
        assertEquals(15107, day24.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day24 = Day24(exampleInput)
        assertEquals(47, day24.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day24 = Day24(resourceAsList("2023/day24.txt"))
        assertEquals(856642398547748, day24.solvePart2())
    }
}