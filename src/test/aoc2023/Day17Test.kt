package test.aoc2023

import aoc2023.Day17
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day17Test {
    private val exampleInput = """
        2413432311323
        3215453535623
        3255245654254
        3446585845452
        4546657867536
        1438598798454
        4457876987766
        3637877979653
        4654967986887
        4564679986453
        1224686865563
        2546548887735
        4322674655533
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day17 = Day17(exampleInput)
        assertEquals(102, day17.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day17 = Day17(resourceAsList("2023/day17.txt"))
        assertEquals(870, day17.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day17 = Day17(exampleInput)
        assertEquals(94, day17.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day17 = Day17(resourceAsList("2023/day17.txt"))
        assertEquals(1063, day17.solvePart2())
    }
}