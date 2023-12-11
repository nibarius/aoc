package test.aoc2023

import aoc2023.Day11
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day11Test {
    private val exampleInput = """
        ...#......
        .......#..
        #.........
        ..........
        ......#...
        .#........
        .........#
        ..........
        .......#..
        #...#.....
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day11 = Day11(exampleInput)
        assertEquals(374, day11.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day11 = Day11(resourceAsList("2023/day11.txt"))
        assertEquals(9648398, day11.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day11 = Day11(exampleInput)
        assertEquals(8410, day11.solvePart2(100))
    }

    @Test
    fun partTwoRealInput() {
        val day11 = Day11(resourceAsList("2023/day11.txt"))
        assertEquals(618800410814, day11.solvePart2())
    }
}