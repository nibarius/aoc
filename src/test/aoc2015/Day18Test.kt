package test.aoc2015

import aoc2015.Day18
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day18Test {
    @Test
    fun testPartOneExample1() {
        val input = """
            .#.#.#
            ...##.
            #....#
            ..#...
            #.#..#
            ####..
        """.trimIndent().split("\n")
        assertEquals(4, Day18(input).solvePart1(4))
    }

    @Test
    fun partOneRealInput() {
        assertEquals(821, Day18(resourceAsList("2015/day18.txt")).solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val input = """
            .#.#.#
            ...##.
            #....#
            ..#...
            #.#..#
            ####..
        """.trimIndent().split("\n")
        assertEquals(17, Day18(input).solvePart2(5))
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(886, Day18(resourceAsList("2015/day18.txt")).solvePart2())
    }
}