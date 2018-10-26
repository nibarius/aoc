package test.aoc2016

import aoc2016.Day10
import org.junit.Assert
import org.junit.Test

class Day10Test {
    private val exampleInput = listOf("value 5 goes to bot 2",
            "bot 2 gives low to bot 1 and high to bot 0",
            "value 3 goes to bot 1",
            "bot 1 gives low to output 1 and high to bot 0",
            "bot 0 gives low to output 2 and high to output 0",
            "value 2 goes to bot 2")

    @Test
    fun partOneExample() {
        Assert.assertEquals(2, Day10(exampleInput).solvePart1(2, 5))
    }

    @Test
    fun partOneRealInput() {
        Assert.assertEquals(47, Day10(resourceAsList("2016/day10.txt")).solvePart1(17, 61))
    }

    @Test
    fun partTwoExample() {
        Assert.assertEquals(30, Day10(exampleInput).solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        Assert.assertEquals(2666, Day10(resourceAsList("2016/day10.txt")).solvePart2())
    }
}