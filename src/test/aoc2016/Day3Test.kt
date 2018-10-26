package test.aoc2016

import aoc2016.Day3
import org.junit.Assert
import org.junit.Test

class Day3Test {
    @Test
    fun partOneExample() {
        Assert.assertEquals(0, Day3(listOf("  5  10  25")).solvePart1())
    }

    @Test
    fun partOneExampleDifferentOrder() {
        Assert.assertEquals(0, Day3(listOf("  25  10  5")).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        Assert.assertEquals(982, Day3(resourceAsList("2016/day3.txt")).solvePart1())
    }

    @Test
    fun partTwoSimpleInput() {
        Assert.assertEquals(1, Day3(listOf("  10  10  25", "  10  100 1000", "15  250  2500")).solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        Assert.assertEquals(1826, Day3(resourceAsList("2016/day3.txt")).solvePart2())
    }
}