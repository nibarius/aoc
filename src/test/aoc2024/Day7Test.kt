package test.aoc2024

import aoc2024.Day7
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day7Test {
    private val exampleInput = """
        190: 10 19
        3267: 81 40 27
        83: 17 5
        156: 15 6
        7290: 6 8 6 15
        161011: 16 10 13
        192: 17 8 14
        21037: 9 7 18 13
        292: 11 6 16 20
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day7 = Day7(exampleInput)
        assertEquals(3749, day7.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day7 = Day7(resourceAsList("2024/day7.txt"))
        assertEquals(4364915411363, day7.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day7 = Day7(exampleInput)
        assertEquals(11387, day7.solvePart2())
    }

    @Test
    fun testPartTwoExtraExample1() {
        // An equation that reaches the correct value without using all input is wrong
        val exampleInput = """
        100: 10 10 10
        1000: 10 10 10
    """.trimIndent().split("\n")
        val day7 = Day7(exampleInput)
        assertEquals(1000, day7.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day7 = Day7(resourceAsList("2024/day7.txt"))
        assertEquals(38322057216320, day7.solvePart2())
    }
}