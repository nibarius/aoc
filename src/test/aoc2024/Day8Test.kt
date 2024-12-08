package test.aoc2024

import aoc2024.Day8
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day8Test {
    private val exampleInput = """
        ............
        ........0...
        .....0......
        .......0....
        ....0.......
        ......A.....
        ............
        ............
        ........A...
        .........A..
        ............
        ............
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day8 = Day8(exampleInput)
        assertEquals(14, day8.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day8 = Day8(resourceAsList("2024/day8.txt"))
        assertEquals(289, day8.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day8 = Day8(exampleInput)
        assertEquals(34, day8.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day8 = Day8(resourceAsList("2024/day8.txt"))
        assertEquals(1030, day8.solvePart2())
    }
}