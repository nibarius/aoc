package test.aoc2016

import aoc2016.Day13
import org.junit.Assert
import org.junit.Test

class Day13Test {

    val exampleMap = """  0123456789
0 .#.####.##
1 ..#..#...#
2 #....##...
3 ###.#.###.
4 .##..#..#.
5 ..##....#.
6 #...##.###
"""


    @Test
    fun testMapGeneration() {
        Assert.assertEquals(exampleMap, Day13(10, Pair(7, 4)).getMap())
    }

    @Test
    fun partOneExample() {
        Assert.assertEquals(11, Day13(10, Pair(7, 4)).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        Assert.assertEquals(86, Day13(1364, Pair(31, 39)).solvePart1())
    }

    @Test
    fun partTwoRealInput() {
        Assert.assertEquals(127, Day13(1364, Pair(-1, -1)).solvePart2())
    }
}