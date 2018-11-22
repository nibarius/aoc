package test.aoc2016

import aoc2016.Day24
import org.junit.Assert
import org.junit.Test

class Day24Test {

    private val exampleInput = listOf(
            "###########",
            "#0.1.....2#",
            "#.#######.#",
            "#4.......3#",
            "###########"
    )

    @Test
    fun findStartTest() {
        Assert.assertEquals(Day24.Coordinate(1, 1), Day24(exampleInput).theMap.findStart())
        Assert.assertEquals(Day24.Coordinate(1, 19), Day24(resourceAsList("2016/day24.txt")).theMap.findStart())
    }

    @Test
    fun partOneExampleInput() {
        Assert.assertEquals(14, Day24(exampleInput).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        Assert.assertEquals(430, Day24(resourceAsList("2016/day24.txt")).solvePart1())
    }

    @Test
    fun partTwoRealInput() {
        Assert.assertEquals(700, Day24(resourceAsList("2016/day24.txt")).solvePart2())
    }
}