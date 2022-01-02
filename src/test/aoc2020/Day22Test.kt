package test.aoc2020

import aoc2020.Day22
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceSplitOnBlankLines

class Day22Test {
    private val exampleInput = """
        Player 1:
        9
        2
        6
        3
        1

        Player 2:
        5
        8
        4
        7
        10
    """.trimIndent().split("\n\n")

    @Test
    fun testPartOneExample1() {
        val day22 = Day22(exampleInput)
        assertEquals(306, day22.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day22 = Day22(resourceSplitOnBlankLines("2020/day22.txt"))
        assertEquals(33434, day22.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day22 = Day22(exampleInput)
        assertEquals(291, day22.solvePart2())
    }

    @Test
    fun testPartTwoInfiniteGame() {
        val exampleInput = """
            Player 1:
            43
            19

            Player 2:
            2
            29
            14
        """.trimIndent().split("\n\n")
        val day22 = Day22(exampleInput)
        assertEquals(105, day22.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day22 = Day22(resourceSplitOnBlankLines("2020/day22.txt"))
        assertEquals(31657, day22.solvePart2())
    }
}