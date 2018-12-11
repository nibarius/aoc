package test.aoc2018

import aoc2018.Day11
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsString

class Day11Test {

    @Test
    fun testPowerLevel() {
        assertEquals(-5, Day11("57").getPowerLevel(122, 79))
        assertEquals(0, Day11("39").getPowerLevel(217, 196))
        assertEquals(4, Day11("71").getPowerLevel(101, 153))
    }

    @Test
    fun testPart1Examples() {
        assertEquals("33,45", Day11("18").solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals("235,16", Day11(resourceAsString("2018/day11.txt")).solvePart1())
    }


    @Test
    fun testPart2Examples() {
        assertEquals("90,269,16", Day11("18").solvePart2())
        assertEquals("232,251,12", Day11("42").solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals("236,227,14", Day11(resourceAsString("2018/day11.txt")).solvePart2())
    }
}




