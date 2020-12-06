package test.aoc2020

import aoc2020.Day7
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day7Test {
    private val exampleInput = """""".trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day7 = Day7(exampleInput)
        assertEquals(0, day7.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day7 = Day7(resourceAsList("2020/day7.txt"))
        assertEquals(0, day7.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day7 = Day7(exampleInput)
        assertEquals(0, day7.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day7 = Day7(resourceAsList("2020/day7.txt"))
        assertEquals(0, day7.solvePart2())
    }
}