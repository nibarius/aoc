package test.aoc2016

import aoc2016.Day20
import org.junit.Assert
import org.junit.Test

class Day20Test {
    private val exampleInput = listOf("5-8",
            "0-2",
            "4-7")

    @Test
    fun partOneExample() {
        Assert.assertEquals(3, Day20(exampleInput).solvePart1())
    }

    @Test
    fun testContainedRange() {
        val input = listOf("0-5", "3-4", "6-8")
        Assert.assertEquals(9, Day20(input).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        Assert.assertEquals(31053880, Day20(resourceAsList("2016/day20.txt")).solvePart1())
    }

    @Test
    fun partTwoRealInput() {
        Assert.assertEquals(117, Day20(resourceAsList("2016/day20.txt")).solvePart2())
    }
}