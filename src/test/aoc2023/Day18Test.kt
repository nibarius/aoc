package test.aoc2023

import aoc2023.Day18
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day18Test {
    private val exampleInput = """
        R 6 (#70c710)
        D 5 (#0dc571)
        L 2 (#5713f0)
        D 2 (#d2c081)
        R 2 (#59c680)
        D 2 (#411b91)
        L 5 (#8ceee2)
        U 2 (#caa173)
        L 1 (#1b58a2)
        U 2 (#caa171)
        R 2 (#7807d2)
        U 3 (#a77fa3)
        L 2 (#015232)
        U 2 (#7a21e3)
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day18 = Day18(exampleInput)
        assertEquals(62, day18.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day18 = Day18(resourceAsList("2023/day18.txt"))
        assertEquals(52055, day18.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day18 = Day18(exampleInput)
        assertEquals(952408144115, day18.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day18 = Day18(resourceAsList("2023/day18.txt"))
        assertEquals(67622758357096, day18.solvePart2())
    }
}