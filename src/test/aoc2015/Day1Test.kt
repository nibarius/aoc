package test.aoc2015

import aoc2015.Day1
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList
import resourceAsString

class Day1Test {
    @Test
    fun testPartOneExample1() {
        assertEquals(0, Day1("(())").solvePart1())
        assertEquals(0, Day1("()()").solvePart1())
    }

    @Test
    fun testPartOneExample2() {
        assertEquals(3, Day1("(((").solvePart1())
        assertEquals(3, Day1("(()(()(").solvePart1())
        assertEquals(3, Day1("))(((((").solvePart1())
    }

    @Test
    fun testPartOneExample3() {
        assertEquals(-1, Day1("())").solvePart1())
        assertEquals(-1, Day1("))(").solvePart1())
    }

    @Test
    fun testPartOneExample4() {
        assertEquals(-3, Day1(")))").solvePart1())
        assertEquals(-3, Day1(")())())").solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals(74, Day1(resourceAsString("2015/day1.txt")).solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        assertEquals(1, Day1(")").solvePart2())
    }

    @Test
    fun testPartTwoExample2() {
        assertEquals(5, Day1("()())").solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(1795, Day1(resourceAsString("2015/day1.txt")).solvePart2())
    }
}