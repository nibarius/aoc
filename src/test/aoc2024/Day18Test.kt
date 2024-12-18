package test.aoc2024

import aoc2024.Day18
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day18Test {
    private val exampleInput = """
        5,4
        4,2
        4,5
        3,0
        2,1
        6,3
        2,4
        1,5
        0,6
        3,3
        2,6
        5,1
        1,2
        5,5
        2,5
        6,5
        1,4
        0,4
        6,4
        1,1
        6,1
        1,0
        0,5
        1,6
        2,0
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day18 = Day18(exampleInput, 6)
        assertEquals(22, day18.solvePart1(12))
    }

    @Test
    fun partOneRealInput() {
        val day18 = Day18(resourceAsList("2024/day18.txt"))
        assertEquals(302, day18.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day18 = Day18(exampleInput, 6)
        assertEquals("6,1", day18.solvePart2(12))
    }

    @Test
    fun partTwoRealInput() {
        val day18 = Day18(resourceAsList("2024/day18.txt"))
        assertEquals("24,32", day18.solvePart2())
    }
}