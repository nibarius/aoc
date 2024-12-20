package test.aoc2024

import aoc2024.Day20
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day20Test {
    private val exampleInput = """
        ###############
        #...#...#.....#
        #.#.#.#.#.###.#
        #S#...#.#.#...#
        #######.#.#.###
        #######.#.#...#
        #######.#.###.#
        ###..E#...#...#
        ###.#######.###
        #...###...#...#
        #.#####.#.###.#
        #.#...#.#.#...#
        #.#.#.#.#.#.###
        #...#...#...###
        ###############
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day20 = Day20(exampleInput)
        assertEquals(1, day20.solvePart1(64))
    }

    @Test
    fun testPartOneExample2() {
        val day20 = Day20(exampleInput)
        assertEquals(8, day20.solvePart1(12))
    }

    @Test
    fun partOneRealInput() {
        val day20 = Day20(resourceAsList("2024/day20.txt"))
        assertEquals(1317, day20.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day20 = Day20(exampleInput)
        assertEquals(41, day20.solvePart2(70))
    }

    @Test
    fun partTwoRealInput() {
        val day20 = Day20(resourceAsList("2024/day20.txt"))
        assertEquals(982474, day20.solvePart2())
    }
}