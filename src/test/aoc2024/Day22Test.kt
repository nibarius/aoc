package test.aoc2024

import aoc2024.Day22
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day22Test {

    @Test
    fun testPartOneExample1() {
        val exampleInput = """
            1
            10
            100
            2024
        """.trimIndent().split("\n")
        val day22 = Day22(exampleInput)
        assertEquals(37327623, day22.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day22 = Day22(resourceAsList("2024/day22.txt"))
        assertEquals(12664695565, day22.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val exampleInput = """
            1
            2
            3
            2024
    """.trimIndent().split("\n")
        val day22 = Day22(exampleInput)
        assertEquals(23, day22.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day22 = Day22(resourceAsList("2024/day22.txt"))
        assertEquals(1444, day22.solvePart2())
    }
}