package test.aoc2022

import aoc2022.Day24
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day24Test {
    private val exampleInput = """
        #.######
        #>>.<^<#
        #.<..<<#
        #>v.><>#
        #<^v^^>#
        ######.#
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day24 = Day24(exampleInput)
        assertEquals(18, day24.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day24 = Day24(resourceAsList("2022/day24.txt"))
        assertEquals(257, day24.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day24 = Day24(exampleInput)
        assertEquals(54, day24.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day24 = Day24(resourceAsList("2022/day24.txt"))
        assertEquals(828, day24.solvePart2())
    }
}