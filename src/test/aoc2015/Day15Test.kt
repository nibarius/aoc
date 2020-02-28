package test.aoc2015

import aoc2015.Day15
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day15Test {
    @Test
    fun testPartOneExample1() {
        val input = """
            Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8
            Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3
        """.trimIndent().split("\n")
        assertEquals(62842880, Day15(input).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals(18965440, Day15(resourceAsList("2015/day15.txt")).solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val input = """
            Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8
            Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3
        """.trimIndent().split("\n")
        assertEquals(57600000, Day15(input).solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(15862900, Day15(resourceAsList("2015/day15.txt")).solvePart2())
    }
}