package test.aoc2024

import aoc2024.Day6
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day6Test {
    private val exampleInput = """
        ....#.....
        .........#
        ..........
        ..#.......
        .......#..
        ..........
        .#..^.....
        ........#.
        #.........
        ......#...
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day6 = Day6(exampleInput)
        assertEquals(41, day6.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day6 = Day6(resourceAsList("2024/day6.txt"))
        assertEquals(5404, day6.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day6 = Day6(exampleInput)
        assertEquals(6, day6.solvePart2())
    }

    @Test
    fun testPartTwoExtraExample1() {
        val exampleInput = """
            .##..
            ....#
            .....
            .^.#.
        """.trimIndent().split("\n")
        val day6 = Day6(exampleInput)
        assertEquals(1, day6.solvePart2())
    }

    @Test
    fun testPartTwoExtraExample2() {
        val exampleInput = """
            ....
            #..#
            .^#.
        """.trimIndent().split("\n")
        val day6 = Day6(exampleInput)
        assertEquals(1, day6.solvePart2())
    }


    @Test
    fun testPartTwoTurnTwiceInARow() {
        val exampleInput = """
            .....
            ...#.
            .#^..
            ..#..
        """.trimIndent().split("\n")
        val day6 = Day6(exampleInput)
        assertEquals(1, day6.solvePart2())
    }

    @Test
    fun testPartTwoNoBlocksOutsideLab() {
        val exampleInput = """
            ###
            #.#
            #.#
            #^#
        """.trimIndent().split("\n")
        val day6 = Day6(exampleInput)
        assertEquals(0, day6.solvePart2())
    }


    @Test
    fun partTwoRealInput() {
        val day6 = Day6(resourceAsList("2024/day6.txt"))
        assertEquals(1984, day6.solvePart2())
    }
}