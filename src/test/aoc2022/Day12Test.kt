package test.aoc2022

import aoc2022.Day12
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day12Test {
    private val exampleInput = """
        Sabqponm
        abcryxxl
        accszExk
        acctuvwj
        abdefghi
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day12 = Day12(exampleInput)
        assertEquals(31, day12.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day12 = Day12(resourceAsList("2022/day12.txt"))
        assertEquals(504, day12.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day12 = Day12(exampleInput)
        assertEquals(29, day12.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day12 = Day12(resourceAsList("2022/day12.txt"))
        assertEquals(500, day12.solvePart2())
    }
}