package test.aoc2023

import aoc2023.Day7
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day7Test {
    private val exampleInput = """
        32T3K 765
        T55J5 684
        KK677 28
        KTJJT 220
        QQQJA 483
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day7 = Day7(exampleInput)
        assertEquals(6440, day7.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day7 = Day7(resourceAsList("2023/day7.txt"))
        assertEquals(250120186, day7.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day7 = Day7(exampleInput)
        assertEquals(5905, day7.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day7 = Day7(resourceAsList("2023/day7.txt"))
        assertEquals(250665248, day7.solvePart2())
    }
}