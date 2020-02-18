package test.aoc2015

import aoc2015.Day3
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsString

class Day3Test {
    @Test
    fun testPartOneExample1() {
        assertEquals(2, Day3("<").solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals(2565, Day3(resourceAsString("2015/day3.txt")).solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        assertEquals(3, Day3("^v").solvePart2())
    }

    @Test
    fun testPartTwoExample2() {
        assertEquals(3, Day3("^>v<").solvePart2())
    }

    @Test
    fun testPartTwoExample3() {
        assertEquals(11, Day3("^v^v^v^v^v").solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(2639, Day3(resourceAsString("2015/day3.txt")).solvePart2())
    }
}