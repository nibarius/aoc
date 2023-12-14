package test.aoc2023

import aoc2023.Day14
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day14Test {
    private val exampleInput = """
        O....#....
        O.OO#....#
        .....##...
        OO.#O....O
        .O.....O#.
        O.#..O.#.#
        ..O..#O..O
        .......O..
        #....###..
        #OO..#....
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day14 = Day14(exampleInput)
        assertEquals(136, day14.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day14 = Day14(resourceAsList("2023/day14.txt"))
        assertEquals(109466, day14.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day14 = Day14(exampleInput)
        assertEquals(64, day14.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day14 = Day14(resourceAsList("2023/day14.txt"))
        assertEquals(94585, day14.solvePart2())
    }
}