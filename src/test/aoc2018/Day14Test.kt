package test.aoc2018

import aoc2018.Day14
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsString

class Day14Test {

    @Test
    fun testPart1Examples() {
        assertEquals("5158916779", Day14("9").solvePart1())
        assertEquals("0124515891", Day14("5").solvePart1())
        assertEquals("9251071085", Day14("18").solvePart1())
        assertEquals("5941429882", Day14("2018").solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals("6521571010", Day14(resourceAsString("2018/day14.txt")).solvePart1())
    }

    @Test
    fun testPart2Examples() {
        assertEquals(9, Day14("51589").solvePart2())
        assertEquals(5, Day14("01245").solvePart2())
        assertEquals(18, Day14("92510").solvePart2())
        assertEquals(2018, Day14("59414").solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(20262967, Day14(resourceAsString("2018/day14.txt")).solvePart2())
    }
}




