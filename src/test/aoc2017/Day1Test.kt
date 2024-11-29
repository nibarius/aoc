package test.aoc2017

import aoc2017.Day1
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsString

class Day1Test {
    @Test
    fun testPartOneExample1() {
        assertEquals(3, Day1("1122").solvePart1())
    }

    @Test
    fun testPartOneExample2() {
        assertEquals(4, Day1("1111").solvePart1())
    }

    @Test
    fun testPartOneExample3() {
        assertEquals(0, Day1("1234").solvePart1())
    }

    @Test
    fun testPartOneExample4() {
        assertEquals(9, Day1("91212129").solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals(1228, Day1(resourceAsString("2017/day1.txt")).solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        assertEquals(6, Day1("1212").solvePart2())
    }

    @Test
    fun testPartTwoExample2() {
        assertEquals(0, Day1("1221").solvePart2())
    }

    @Test
    fun testPartTwoExample3() {
        assertEquals(4, Day1("123425").solvePart2())
    }

    @Test
    fun testPartTwoExample4() {
        assertEquals(12, Day1("123123").solvePart2())
    }

    @Test
    fun testPartTwoExample5() {
        assertEquals(4, Day1("12131415").solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(1238, Day1(resourceAsString("2017/day1.txt")).solvePart2())
    }
}