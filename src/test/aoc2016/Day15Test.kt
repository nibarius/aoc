package test.aoc2016

import aoc2016.Day15
import org.junit.Assert
import org.junit.Test
import resourceAsList

class Day15Test {
    private val day1TestInput = listOf(
            "Disc #1 has 5 positions; at time=0, it is at position 4.",
            "Disc #2 has 2 positions; at time=0, it is at position 1."
    )

    @Test
    fun partOneExample() {
        Assert.assertEquals(5, Day15(day1TestInput).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        Assert.assertEquals(148737, Day15(resourceAsList("2016/day15.txt")).solvePart1())
    }

    @Test
    fun partTwoRealInput() {
        Assert.assertEquals(2353212, Day15(resourceAsList("2016/day15.txt")).solvePart2())
    }
}