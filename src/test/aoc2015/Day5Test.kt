package test.aoc2015

import aoc2015.Day5
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day5Test {
    @Test
    fun testPartOneExample1() {
        val input = """ugknbfddgicrmopn""".trimIndent().split("\n")
        assertEquals(1, Day5(input).solvePart1())
    }

    @Test
    fun testPartOneExample2() {
        val input = """aaa""".trimIndent().split("\n")
        assertEquals(1, Day5(input).solvePart1())
    }

    @Test
    fun testPartOneExample3() {
        val input = """jchzalrnumimnmhp""".trimIndent().split("\n")
        assertEquals(0, Day5(input).solvePart1())
    }

    @Test
    fun testPartOneExample4() {
        val input = """haegwjzuvuyypxyu""".trimIndent().split("\n")
        assertEquals(0, Day5(input).solvePart1())
    }

    @Test
    fun testPartOneExample5() {
        val input = """dvszwmarrgswjxmb""".trimIndent().split("\n")
        assertEquals(0, Day5(input).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals(255, Day5(resourceAsList("2015/day5.txt")).solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val input = """qjhvhtzxzqqjkmpb""".trimIndent().split("\n")
        assertEquals(1, Day5(input).solvePart2())
    }

    @Test
    fun testPartTwoExample2() {
        val input = """xxyxx""".trimIndent().split("\n")
        assertEquals(1, Day5(input).solvePart2())
    }

    @Test
    fun testPartTwoExample3() {
        val input = """uurcxstgmygtbstg""".trimIndent().split("\n")
        assertEquals(0, Day5(input).solvePart2())
    }

    @Test
    fun testPartTwoExample4() {
        val input = """ieodomkazucvgmuy""".trimIndent().split("\n")
        assertEquals(0, Day5(input).solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(0, Day5(resourceAsList("2015/day5.txt")).solvePart2())
    }
}