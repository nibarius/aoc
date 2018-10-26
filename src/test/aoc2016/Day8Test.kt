package test.aoc2016

import aoc2016.Day8
import org.junit.Assert
import org.junit.Test

class Day8Test {

    @Test
    fun partOneExample1() {
        Assert.assertEquals(6, Day8(listOf("rect 3x2")).solvePart1())
    }

    @Test
    fun partOneExample2() {
        Assert.assertEquals(6, Day8(listOf("rect 3x2", "rotate column x=1 by 1", "rotate row y=0 by 4", "rotate column x=1 by 1")).solvePart1())
    }

    @Test
    fun doubleAddingPixels() {
        val day8 = Day8(listOf("rect 3x2", "rotate column x=1 by 1", "rotate row y=1 by 3", "rect 3x2"))
        val ret = day8.solvePart1()
        //day8.printDisplay()

        Assert.assertEquals(10, ret)
    }

    @Test
    fun partOneRealInput() {
        Assert.assertEquals(128, Day8(resourceAsList("2016/day8.txt")).solvePart1())
    }

    @Test
    fun partTwoRealInput() {
        Day8(resourceAsList("2016/day8.txt")).solvePart2()
    }
}