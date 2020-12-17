package test.aoc2020

import aoc2020.Day17
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day17Test {
    private val exampleInput = """
        .#.
        ..#
        ###
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day17 = Day17(exampleInput)
        assertEquals(112, day17.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day17 = Day17(resourceAsList("2020/day17.txt"))
        assertEquals(230, day17.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day17 = Day17(exampleInput)
        assertEquals(848, day17.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day17 = Day17(resourceAsList("2020/day17.txt"))
        assertEquals(1600, day17.solvePart2())
    }
}