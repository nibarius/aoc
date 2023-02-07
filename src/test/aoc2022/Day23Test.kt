package test.aoc2022

import aoc2022.Day23
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day23Test {
    private val exampleInput = """
        ..............
        ..............
        .......#......
        .....###.#....
        ...#...#.#....
        ....#...##....
        ...#.###......
        ...##.#.##....
        ....#..#......
        ..............
        ..............
        ..............
    """.trimIndent().split("\n")

    private val smallExample = """
        .....
        ..##.
        ..#..
        .....
        ..##.
        .....
    """.trimIndent().split("\n")

    @Test
    fun testPartOneSmallExample() {
        val day23 = Day23(smallExample)
        assertEquals(25, day23.solvePart1())
    }

    @Test
    fun testPartOneExample1() {
        val day23 = Day23(exampleInput)
        assertEquals(110, day23.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day23 = Day23(resourceAsList("2022/day23.txt"))
        assertEquals(3689, day23.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day23 = Day23(exampleInput)
        assertEquals(20, day23.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day23 = Day23(resourceAsList("2022/day23.txt"))
        assertEquals(965, day23.solvePart2())
    }
}