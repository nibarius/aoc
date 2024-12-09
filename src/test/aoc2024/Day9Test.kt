package test.aoc2024

import aoc2024.Day9
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day9Test {
    private val exampleInput = """2333133121414131402""".trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day9 = Day9(exampleInput)
        assertEquals(1928, day9.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day9 = Day9(resourceAsList("2024/day9.txt"))
        assertEquals(6359213660505, day9.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day9 = Day9(exampleInput)
        assertEquals(2858, day9.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day9 = Day9(resourceAsList("2024/day9.txt"))
        assertEquals(6381624803796, day9.solvePart2())
    }
}