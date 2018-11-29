package test.aoc2018

import aoc2018.Day1
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsString

class Day1Test {
    @Test
    fun partOneExampleOne() {
        assertEquals(-1, Day1("?").solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals(-1, Day1(resourceAsString("2018/day1.txt")).solvePart1())
    }

    @Test
    fun partTwoExample() {
        assertEquals(-1, Day1("?").solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(-1, Day1(resourceAsString("2018/day1.txt")).solvePart2())
    }


}




