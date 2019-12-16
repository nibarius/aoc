package test.aoc2019

import aoc2019.Day16
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsString

class Day16Test {

    @Test
    fun testPartOneExample0() {
        val input = "12345678"
        assertEquals("23845678", Day16(input).solvePart1().substring(0, 8))
    }

    @Test
    fun testPartOneExample1() {
        val input = "80871224585914546619083218645595"
        assertEquals("24176176", Day16(input).solvePart1().substring(0, 8))
    }

    @Test
    fun testPartOneExample2() {
        val input = "19617804207202209144916044189917"
        assertEquals("73745418", Day16(input).solvePart1().substring(0, 8))
    }

    @Test
    fun testPartOneExample3() {
        val input = "69317163492948606335995924319873"
        assertEquals("52432133", Day16(input).solvePart1().substring(0, 8))
    }

    @Test
    fun partOneRealInput() {
        assertEquals("73127523", Day16(resourceAsString("2019/day16.txt")).solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val input = "03036732577212944063491565474664"
        assertEquals("84462026", Day16(input).solvePart2())
    }

    @Test
    fun testPartTwoExample2() {
        val input = "02935109699940807407585447034323"
        assertEquals("78725270", Day16(input).solvePart2())
    }

    @Test
    fun testPartTwoExample3() {
        val input = "03081770884921959731165446850517"
        assertEquals("53553731", Day16(input).solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals("80284420", Day16(resourceAsString("2019/day16.txt")).solvePart2())
    }
}