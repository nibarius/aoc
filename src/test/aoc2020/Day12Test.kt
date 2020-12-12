package test.aoc2020

import aoc2020.Day12
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day12Test {
    private val exampleInput = """
        F10
        N3
        F7
        R90
        F11
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day12 = Day12(exampleInput)
        assertEquals(25, day12.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day12 = Day12(resourceAsList("2020/day12.txt"))
        assertEquals(1294, day12.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day12 = Day12(exampleInput)
        assertEquals(286, day12.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day12 = Day12(resourceAsList("2020/day12.txt"))
        assertEquals(20592, day12.solvePart2())
    }
}