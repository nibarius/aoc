package test.aoc2016

import aoc2016.Day2
import org.junit.Assert
import org.junit.Test
import resourceAsList

class Day2Test {
    @Test
    fun partOneExample() {
        Assert.assertEquals("1985", Day2(listOf("ULL", "RRDDD", "LURDL", "UUUUD")).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        Assert.assertEquals("73597", Day2(resourceAsList("2016/day2.txt")).solvePart1())
    }

    @Test
    fun partTwoExample() {
        Assert.assertEquals("5DB3", Day2(listOf("ULL", "RRDDD", "LURDL", "UUUUD")).solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        Assert.assertEquals("A47DA", Day2(resourceAsList("2016/day2.txt")).solvePart2())
    }
}