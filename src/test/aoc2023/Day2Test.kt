package test.aoc2023

import aoc2023.Day2
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day2Test {
    private val exampleInput = """
        Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
        Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
        Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
        Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
        Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day2 = Day2(exampleInput)
        assertEquals(8, day2.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day2 = Day2(resourceAsList("2023/day2.txt"))
        assertEquals(2913, day2.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day2 = Day2(exampleInput)
        assertEquals(2286, day2.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day2 = Day2(resourceAsList("2023/day2.txt"))
        assertEquals(55593, day2.solvePart2())
    }
}