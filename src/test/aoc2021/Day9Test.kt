package test.aoc2021

import aoc2021.Day9
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day9Test {
    private val exampleInput = """
        2199943210
        3987894921
        9856789892
        8767896789
        9899965678
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day9 = Day9(exampleInput)
        assertEquals(15, day9.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day9 = Day9(resourceAsList("2021/day9.txt"))
        assertEquals(585, day9.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day9 = Day9(exampleInput)
        assertEquals(1134, day9.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day9 = Day9(resourceAsList("2021/day9.txt"))
        assertEquals(827904, day9.solvePart2())
    }
}