package test.aoc2021

import aoc2021.Day17
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day17Test {
    private val exampleInput = """target area: x=20..30, y=-10..-5""".trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day17 = Day17(exampleInput)
        assertEquals(45, day17.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day17 = Day17(resourceAsList("2021/day17.txt"))
        assertEquals(3570, day17.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day17 = Day17(exampleInput)
        assertEquals(112, day17.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day17 = Day17(resourceAsList("2021/day17.txt"))
        assertEquals(1919, day17.solvePart2())
    }
}