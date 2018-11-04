package test.aoc2016

import aoc2016.Day18
import org.junit.Assert
import org.junit.Test

class Day18Test {
    @Test
    fun partOneExample() {
        Assert.assertEquals(6, Day18(3, "..^^.").solvePart1())
    }

    @Test
    fun partOneExample2() {
        Assert.assertEquals(38, Day18(10, ".^^.^.^^^^").solvePart1())
    }

    @Test
    fun partOneRealInput() {
        Assert.assertEquals(1913, Day18(40, resourceAsString("2016/day18.txt")).solvePart1())
    }

    @Test
    fun partTwoRealInput() {
        Assert.assertEquals(19993564, Day18(400000, resourceAsString("2016/day18.txt")).solvePart2())
    }
}