package test.aoc2025

import aoc2025.Day10
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day10Test {
    private val exampleInput = """
        [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
        [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
        [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day10 = Day10(exampleInput)
        assertEquals(7, day10.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day10 = Day10(resourceAsList("2025/day10.txt"))
        assertEquals(417, day10.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day10 = Day10(exampleInput)
        assertEquals(33, day10.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day10 = Day10(resourceAsList("2025/day10.txt"))
        assertEquals(16765, day10.solvePart2())
    }
}