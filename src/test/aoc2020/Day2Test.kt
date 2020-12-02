package test.aoc2020

import aoc2020.Day2
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day2Test {
    private val testInput = """
            1-3 a: abcde
            1-3 b: cdefg
            2-9 c: ccccccccc
        """.trimIndent().split("\n")
    private val day2 = Day2(resourceAsList("2020/day2.txt"))
    private val exampleDay2 = Day2(testInput)

    @Test
    fun testPartOneExample1() {
        assertEquals(2, exampleDay2.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals(666, day2.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        assertEquals(1, exampleDay2.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(670, day2.solvePart2())
    }
}