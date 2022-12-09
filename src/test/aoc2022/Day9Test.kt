package test.aoc2022

import aoc2022.Day9
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day9Test {
    private val exampleInput = """
        R 4
        U 4
        L 3
        D 1
        R 4
        D 1
        L 5
        R 2
    """.trimIndent().split("\n")

    private val exampleInput2= """
        R 5
        U 8
        L 8
        D 3
        R 17
        D 10
        L 25
        U 20
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day9 = Day9(exampleInput)
        assertEquals(13, day9.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day9 = Day9(resourceAsList("2022/day9.txt"))
        assertEquals(5619, day9.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day9 = Day9(exampleInput)
        assertEquals(1, day9.solvePart2())
    }

    @Test
    fun testPartTwoExample2() {
        val day9 = Day9(exampleInput2)
        assertEquals(36, day9.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day9 = Day9(resourceAsList("2022/day9.txt"))
        assertEquals(2376, day9.solvePart2())
    }
}