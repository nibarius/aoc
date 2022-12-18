package test.aoc2022

import aoc2022.Day18
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day18Test {
    private val exampleInput = """
        2,2,2
        1,2,2
        3,2,2
        2,1,2
        2,3,2
        2,2,1
        2,2,3
        2,2,4
        2,2,6
        1,2,5
        3,2,5
        2,1,5
        2,3,5
    """.trimIndent().split("\n")

    private val smallExample = """
        1,1,1
        2,1,1
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample2() {
        val day18 = Day18(smallExample)
        assertEquals(10, day18.solvePart1())
    }

    @Test
    fun testPartOneExample1() {
        val day18 = Day18(exampleInput)
        assertEquals(64, day18.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day18 = Day18(resourceAsList("2022/day18.txt"))
        assertEquals(3650, day18.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day18 = Day18(exampleInput)
        assertEquals(58, day18.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day18 = Day18(resourceAsList("2022/day18.txt"))
        assertEquals(2118, day18.solvePart2())
    }
}