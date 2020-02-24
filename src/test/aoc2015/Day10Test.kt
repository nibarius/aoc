package test.aoc2015

import aoc2015.Day10
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsString

class Day10Test {
    @Test
    fun testPartOneExample1() {
        val input = """1"""
        assertEquals("11".length, Day10(input).solvePart1(1))
    }

    @Test
    fun testPartOneExample2() {
        val input = """11"""
        assertEquals("21".length, Day10(input).solvePart1(1))
    }

    @Test
    fun testPartOneExample3() {
        val input = """21"""
        assertEquals("1211".length, Day10(input).solvePart1(1))
    }

    @Test
    fun testPartOneExample4() {
        val input = """1211"""
        assertEquals("111221".length, Day10(input).solvePart1(1))
    }

    @Test
    fun testPartOneExample5() {
        val input = """111221"""
        assertEquals("312211".length, Day10(input).solvePart1(1))
    }

    @Test
    fun partOneRealInput() {
        assertEquals(492982, Day10(resourceAsString("2015/day10.txt")).solvePart1())
    }


    @Test
    fun partTwoRealInput() {
        assertEquals(6989950, Day10(resourceAsString("2015/day10.txt")).solvePart2())
    }
}