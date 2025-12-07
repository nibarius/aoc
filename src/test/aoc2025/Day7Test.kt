package test.aoc2025

import aoc2025.Day7
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day7Test {
    private val exampleInput = """
        .......S.......
        ...............
        .......^.......
        ...............
        ......^.^......
        ...............
        .....^.^.^.....
        ...............
        ....^.^...^....
        ...............
        ...^.^...^.^...
        ...............
        ..^...^.....^..
        ...............
        .^.^.^.^.^...^.
        ...............
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day7 = Day7(exampleInput)
        assertEquals(21, day7.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day7 = Day7(resourceAsList("2025/day7.txt"))
        assertEquals(1541, day7.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day7 = Day7(exampleInput)
        assertEquals(40, day7.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day7 = Day7(resourceAsList("2025/day7.txt"))
        assertEquals(80158285728929, day7.solvePart2())
    }
}