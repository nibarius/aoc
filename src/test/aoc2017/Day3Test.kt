package test.aoc2017

import aoc2017.Day3
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsString

class Day3Test {

    @Test
    fun testPartOneExample2() {
        val day3 = Day3("12")
        assertEquals(3, day3.solvePart1())
    }

    @Test
    fun testPartOneExample3() {
        val day3 = Day3("23")
        assertEquals(2, day3.solvePart1())
    }

    @Test
    fun testPartOneExample4() {
        val day3 = Day3("1024")
        assertEquals(31, day3.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day3 = Day3(resourceAsString("2017/day3.txt"))
        assertEquals(480, day3.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day3 = Day3("54")
        assertEquals(57, day3.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day3 = Day3(resourceAsString("2017/day3.txt"))
        assertEquals(349975, day3.solvePart2())
    }
}