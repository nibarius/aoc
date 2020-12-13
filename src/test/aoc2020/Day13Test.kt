package test.aoc2020

import aoc2020.Day13
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day13Test {
    private val exampleInput = """
        939
        7,13,x,x,59,x,31,19
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day13 = Day13(exampleInput)
        assertEquals(295, day13.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day13 = Day13(resourceAsList("2020/day13.txt"))
        assertEquals(138, day13.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day13 = Day13(exampleInput)
        assertEquals(1068781, day13.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day13 = Day13(resourceAsList("2020/day13.txt"))
        assertEquals(226845233210288, day13.solvePart2())
    }
}