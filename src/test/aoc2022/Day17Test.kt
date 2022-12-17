package test.aoc2022

import aoc2022.Day17
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day17Test {
    private val exampleInput = """>>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>""".trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day17 = Day17(exampleInput)
        assertEquals(3068, day17.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day17 = Day17(resourceAsList("2022/day17.txt"))
        assertEquals(3100, day17.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day17 = Day17(exampleInput)
        assertEquals(1514285714288, day17.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day17 = Day17(resourceAsList("2022/day17.txt"))
        assertEquals(1540634005751, day17.solvePart2())
    }
}