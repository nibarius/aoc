package test.aoc2024

import aoc2024.Day11
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day11Test {
    private val exampleInput = """125 17""".trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day11 = Day11(exampleInput)
        assertEquals(55312, day11.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day11 = Day11(resourceAsList("2024/day11.txt"))
        assertEquals(203228, day11.solvePart1())
    }


    @Test
    fun partTwoRealInput() {
        val day11 = Day11(resourceAsList("2024/day11.txt"))
        assertEquals(240884656550923, day11.solvePart2())
    }
}