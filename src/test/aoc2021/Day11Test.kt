package test.aoc2021

import aoc2021.Day11
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day11Test {
    private val exampleInput = """
        5483143223
        2745854711
        5264556173
        6141336146
        6357385478
        4167524645
        2176841721
        6882881134
        4846848554
        5283751526
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day11 = Day11(exampleInput)
        assertEquals(1656, day11.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day11 = Day11(resourceAsList("2021/day11.txt"))
        assertEquals(1723, day11.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day11 = Day11(exampleInput)
        assertEquals(195, day11.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day11 = Day11(resourceAsList("2021/day11.txt"))
        assertEquals(327, day11.solvePart2())
    }
}