package test.aoc2020

import aoc2020.Day3
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day3Test {
    private val exampleInput = """
            ..##.......
            #...#...#..
            .#....#..#.
            ..#.#...#.#
            .#...##..#.
            ..#.##.....
            .#.#.#....#
            .#........#
            #.##...#...
            #...##....#
            .#..#...#.#
        """.trimIndent().split("\n")
    private val exampleDay3 = Day3(exampleInput)
    private val day3 = Day3(resourceAsList("2020/day3.txt"))

    @Test
    fun testPartOneExample1() {
        assertEquals(7, exampleDay3.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals(289, day3.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        assertEquals(336, exampleDay3.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(5522401584, day3.solvePart2())
    }
}