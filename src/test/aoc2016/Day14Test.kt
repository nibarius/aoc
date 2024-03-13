package test.aoc2016

import aoc2016.Day14
import org.junit.Assert
import org.junit.Test
import resourceAsString

class Day14Test {


    @Test
    fun partOneExample() {
        Assert.assertEquals(22728, Day14("abc").solvePart1())
    }

    @Test
    fun partOneRealInput() {
        Assert.assertEquals(15168, Day14(resourceAsString("2016/day14.txt")).solvePart1())
    }

    @Test
    fun partTwoExample() {
        Assert.assertEquals(22551, Day14("abc").solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        Assert.assertEquals(20864, Day14(resourceAsString("2016/day14.txt")).solvePart2())
    }
}