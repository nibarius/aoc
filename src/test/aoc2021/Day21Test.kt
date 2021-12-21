package test.aoc2021

import aoc2021.Day21
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day21Test {
    private val exampleInput = """
        Player 1 starting position: 4
        Player 2 starting position: 8
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day21 = Day21(exampleInput)
        assertEquals(739785, day21.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day21 = Day21(resourceAsList("2021/day21.txt"))
        assertEquals(504972, day21.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day21 = Day21(exampleInput)
        assertEquals(444356092776315, day21.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day21 = Day21(resourceAsList("2021/day21.txt"))
        assertEquals(446968027750017, day21.solvePart2())
    }
}