package test.aoc2016

import aoc2016.Day14
import org.junit.Assert
import org.junit.Test

class Day14Test {


    @Test
    fun partOneExample() {
        Assert.assertEquals(22728, Day14("abc").solvePart1())
    }

    @Test
    fun partOneRealInput() {
        Assert.assertEquals(15168, Day14("qzyelonm").solvePart1())
    }

    @Test
    fun partTwoExample() {
        Assert.assertEquals(22551, Day14("abc").solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        Assert.assertEquals(20864, Day14("qzyelonm").solvePart2())
    }
}