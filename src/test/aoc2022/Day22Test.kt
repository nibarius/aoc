package test.aoc2022

import aoc2022.Day22
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceSplitOnBlankLines

class Day22Test {
    private val exampleInput = """
        ...#
        .#..
        #...
        ....
...#.......#
........#...
..#....#....
..........#.
        ...#....
        .....#..
        .#......
        ......#.

10R5L5R10L4R5L5
    """.trimIndent().split("\n\n")

    @Test
    fun testPartOneExample1() {
        val day22 = Day22(exampleInput)
        assertEquals(6032, day22.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day22 = Day22(resourceSplitOnBlankLines("2022/day22.txt"))
        assertEquals(196134, day22.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day22 = Day22(exampleInput)
        assertEquals(5031, day22.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day22 = Day22(resourceSplitOnBlankLines("2022/day22.txt"))
        assertEquals(146011, day22.solvePart2(realInput = true))
    }
}