package test.aoc2022

import aoc2022.Day8
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day8Test {
    private val exampleInput = """
        30373
        25512
        65332
        33549
        35390
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day8 = Day8(exampleInput)
        assertEquals(21, day8.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day8 = Day8(resourceAsList("2022/day8.txt"))
        assertEquals(1827, day8.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day8 = Day8(exampleInput)
        assertEquals(8, day8.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day8 = Day8(resourceAsList("2022/day8.txt"))
        assertEquals(335580, day8.solvePart2())
    }
}