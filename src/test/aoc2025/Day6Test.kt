package test.aoc2025

import aoc2025.Day6
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day6Test {
    private val exampleInput = """
        123 328  51 64 
         45 64  387 23 
          6 98  215 314
        *   +   *   +  
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day6 = Day6(exampleInput)
        assertEquals(4277556, day6.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day6 = Day6(resourceAsList("2025/day6.txt"))
        assertEquals(4771265398012, day6.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day6 = Day6(exampleInput)
        assertEquals(3263827, day6.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day6 = Day6(resourceAsList("2025/day6.txt"))
        assertEquals(10695785245101, day6.solvePart2())
    }
}