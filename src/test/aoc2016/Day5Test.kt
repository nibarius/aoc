package test.aoc2016

import aoc2016.Day5
import org.junit.Assert
import org.junit.Test
import resourceAsString

class Day5Test {
    @Test
    fun partOneExample() {
        Assert.assertEquals("18f47a30", Day5("abc").solvePart1())
    }

    @Test
    fun partOneRealInput() {
        Assert.assertEquals("f97c354d", Day5(resourceAsString("2016/day5.txt")).solvePart1())
    }

    @Test
    fun partTwoExample() {
        Assert.assertEquals("05ace8e3", Day5("abc").solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        Assert.assertEquals("863dde27", Day5(resourceAsString("2016/day5.txt")).solvePart2())
    }
}