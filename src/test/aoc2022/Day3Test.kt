package test.aoc2022

import aoc2022.Day3
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day3Test {
    private val exampleInput = """
        vJrwpWtwJgWrhcsFMMfFFhFp
        jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
        PmmdzqPrVvPwwTWBwg
        wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
        ttgJtRGJQctTZtZT
        CrZsJsPPZsGzwwsLwLmpwMDw
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day3 = Day3(exampleInput)
        assertEquals(157, day3.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day3 = Day3(resourceAsList("2022/day3.txt"))
        assertEquals(7878, day3.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day3 = Day3(exampleInput)
        assertEquals(70, day3.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day3 = Day3(resourceAsList("2022/day3.txt"))
        assertEquals(2760, day3.solvePart2())
    }
}