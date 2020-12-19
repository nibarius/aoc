package test.aoc2020

import aoc2020.Day18
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day18Test {
    private val exampleInput = """1 + 2 * 3 + 4 * 5 + 6""".trimIndent().split("\n")
    private val example2 = listOf("1 + (2 * 3) + (4 * (5 + 6))")
    private val example3 = listOf("2 * 3 + (4 * 5)")
    private val example4 = listOf("5 + (8 * 3 + 9 + 3 * 4 * 3)")
    private val example5 = listOf("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))")
    private val example6 = listOf("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2")

    @Test
    fun testPartOneExample1() {
        val day18 = Day18(exampleInput)
        assertEquals(71, day18.solvePart1())
    }

    @Test
    fun testPartOneExample2() {
        val day18 = Day18(example2)
        assertEquals(51, day18.solvePart1())
    }

    @Test
    fun testPartOneExample3() {
        val day18 = Day18(example3)
        assertEquals(26, day18.solvePart1())
    }

    @Test
    fun testPartOneExample4() {
        val day18 = Day18(example4)
        assertEquals(437, day18.solvePart1())
    }

    @Test
    fun testPartOneExample5() {
        val day18 = Day18(example5)
        assertEquals(12240, day18.solvePart1())
    }

    @Test
    fun testPartOneExample6() {
        val day18 = Day18(example6)
        assertEquals(13632, day18.solvePart1())
    }

    @Test
    fun testPartOneDoubleParenthesisLeftValue() {
        val day18 = Day18(listOf("(9 + 8) * 9 + ((9 * 3 * 7 + 9 + 5) * 2 * 7 * 7) + 3 * 5"))
        assertEquals(100250, day18.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day18 = Day18(resourceAsList("2020/day18.txt"))
        assertEquals(12918250417632, day18.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day18 = Day18(exampleInput)
        assertEquals(231, day18.solvePart2())
    }

    @Test
    fun testPartTwoExample2() {
        val day18 = Day18(example2)
        assertEquals(51, day18.solvePart2())
    }

    @Test
    fun testPartTwoExample3() {
        val day18 = Day18(example3)
        assertEquals(46, day18.solvePart2())
    }

    @Test
    fun testPartTwoExample4() {
        val day18 = Day18(example4)
        assertEquals(1445, day18.solvePart2())
    }

    @Test
    fun testPartTwoExample5() {
        val day18 = Day18(example5)
        assertEquals(669060, day18.solvePart2())
    }

    @Test
    fun testPartTwoExample6() {
        val day18 = Day18(example6)
        assertEquals(23340, day18.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day18 = Day18(resourceAsList("2020/day18.txt"))
        assertEquals(171259538712010, day18.solvePart2())
    }
}