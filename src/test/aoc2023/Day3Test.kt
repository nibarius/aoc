package test.aoc2023

import aoc2023.Day3
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day3Test {
    private val exampleInput = """
        467..114..
        ...*......
        ..35..633.
        ......#...
        617*......
        .....+.58.
        ..592.....
        ......755.
        ...${'$'}.*....
        .664.598..
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day3 = Day3(exampleInput)
        assertEquals(4361, day3.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day3 = Day3(resourceAsList("2023/day3.txt"))
        assertEquals(540131, day3.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day3 = Day3(exampleInput)
        assertEquals(467835, day3.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day3 = Day3(resourceAsList("2023/day3.txt"))
        assertEquals(86879020, day3.solvePart2())
    }
}