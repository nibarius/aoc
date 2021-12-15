package test.aoc2021

import aoc2021.Day15
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day15Test {
    private val exampleInput = """
        1163751742
        1381373672
        2136511328
        3694931569
        7463417111
        1319128137
        1359912421
        3125421639
        1293138521
        2311944581
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day15 = Day15(exampleInput)
        assertEquals(40, day15.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day15 = Day15(resourceAsList("2021/day15.txt"))
        assertEquals(745, day15.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day15 = Day15(exampleInput)
        assertEquals(315, day15.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day15 = Day15(resourceAsList("2021/day15.txt"))
        assertEquals(3002, day15.solvePart2())
    }
}