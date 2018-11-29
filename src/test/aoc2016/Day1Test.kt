package test.aoc2016

import aoc2016.Day1
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsString

class Day1Test {
    @Test
    fun partOneExampleOne() {
        assertEquals(5, Day1("R2, L3").solvePart1())
    }

    @Test
    fun partOneExampleTwo() {
        assertEquals(2, Day1("R2, R2, R2").solvePart1())
    }

    @Test
    fun partOneExampleThree() {
        assertEquals(12, Day1("R5, L5, R5, R3").solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals(298, Day1(resourceAsString("2016/day1.txt")).solvePart1())
    }

    @Test
    fun partTwoExample() {
        assertEquals(4, Day1("R8, R4, R4, R8").solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(158, Day1(resourceAsString("2016/day1.txt")).solvePart2())
    }


}




