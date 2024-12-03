package test.aoc2024

import aoc2024.Day3
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day3Test {
    private val exampleInput =
        """xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))""".trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day3 = Day3(exampleInput)
        assertEquals(161, day3.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day3 = Day3(resourceAsList("2024/day3.txt"))
        assertEquals(173731097, day3.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day3 = Day3(listOf("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"))
        assertEquals(48, day3.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day3 = Day3(resourceAsList("2024/day3.txt"))
        assertEquals(93729253, day3.solvePart2())
    }
}