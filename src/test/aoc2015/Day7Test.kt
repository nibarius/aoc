package test.aoc2015

import aoc2015.Day7
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

@ExperimentalUnsignedTypes
class Day7Test {
    @Test
    fun testPartOneExample1() {
        val input = """
            123 -> x
            456 -> y
            x AND y -> d
            x OR y -> e
            x LSHIFT 2 -> f
            y RSHIFT 2 -> g
            NOT x -> h
            NOT y -> a
        """.trimIndent().split("\n")
        assertEquals(65079, Day7(input).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals(3176, Day7(resourceAsList("2015/day7.txt")).solvePart1())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(14710, Day7(resourceAsList("2015/day7.txt")).solvePart2())
    }
}