package test.aoc2019

import aoc2019.Day22
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day22Test {
    @Test
    fun testPartOneExample1() {
        val input = """
            deal with increment 7
            deal into new stack
            deal into new stack
        """.trimIndent().split("\n")
        assertEquals(3L, Day22(input).finalPositionForCard(9, 10))
    }
    
    @Test
    fun testPartOneExample2() {
        val input = """
            cut 6
            deal with increment 7
            deal into new stack
        """.trimIndent().split("\n")
        assertEquals(5L, Day22(input).finalPositionForCard(8, 10))
    }

    @Test
    fun testPartOneExample3() {
        val input = """
            deal with increment 7
            deal with increment 9
            cut -2
        """.trimIndent().split("\n")
        assertEquals(0L, Day22(input).finalPositionForCard(6, 10))
    }

    @Test
    fun testPartOneExample4() {
        val input = """
            deal into new stack
            cut -2
            deal with increment 7
            cut 8
            cut -4
            deal with increment 7
            cut 3
            deal with increment 9
            deal with increment 3
            cut -1
        """.trimIndent().split("\n")
        assertEquals(7L, Day22(input).finalPositionForCard(10, 10))
    }

    @Test
    fun partOneRealInput() {
        assertEquals(7395, Day22(resourceAsList("2019/day22.txt")).solvePart1())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(32376123569821, Day22(resourceAsList("2019/day22.txt")).solvePart2())
    }
}