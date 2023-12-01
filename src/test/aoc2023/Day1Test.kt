package test.aoc2023

import aoc2023.Day1
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day1Test {
    private val exampleInput = """
        1abc2
        pqr3stu8vwx
        a1b2c3d4e5f
        treb7uchet
    """.trimIndent().split("\n")

    private val exampleInput2 = """
        two1nine
        eightwothree
        abcone2threexyz
        xtwone3four
        4nineeightseven2
        zoneight234
        7pqrstsixteen
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day1 = Day1(exampleInput)
        assertEquals(142, day1.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day1 = Day1(resourceAsList("2023/day1.txt"))
        assertEquals(54561, day1.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day1 = Day1(exampleInput2)
        assertEquals(281, day1.solvePart2())
    }
    @Test
    fun testPartTwoExtraTest() {
        // Index of the last three is before the two
        // 1threethree2three
        // 133two3
        // So generating 9 strings, each with one digit replaced and then looking for the
        // largest index won't work.
        val day1 = Day1(listOf("1threethreetwothree"))
        assertEquals(13, day1.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day1 = Day1(resourceAsList("2023/day1.txt"))
        assertEquals(54076, day1.solvePart2())
    }
}