package test.aoc2020

import aoc2020.Day11
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day11Test {
    private val exampleInput = """
        L.LL.LL.LL
        LLLLLLL.LL
        L.L.L..L..
        LLLL.LL.LL
        L.LL.LL.LL
        L.LLLLL.LL
        ..L.L.....
        LLLLLLLLLL
        L.LLLLLL.L
        L.LLLLL.LL
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day11 = Day11(exampleInput)
        assertEquals(37, day11.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day11 = Day11(resourceAsList("2020/day11.txt"))
        assertEquals(2211, day11.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day11 = Day11(exampleInput)
        assertEquals(26, day11.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day11 = Day11(resourceAsList("2020/day11.txt"))
        assertEquals(1995, day11.solvePart2())
    }
}