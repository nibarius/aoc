package test.aoc2024

import aoc2024.Day16
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day16Test {
    private val exampleInput = """
        ###############
        #.......#....E#
        #.#.###.#.###.#
        #.....#.#...#.#
        #.###.#####.#.#
        #.#.#.......#.#
        #.#.#####.###.#
        #...........#.#
        ###.#.#####.#.#
        #...#.....#.#.#
        #.#.#.###.#.#.#
        #.....#...#.#.#
        #.###.#.#.#.#.#
        #S..#.....#...#
        ###############
    """.trimIndent().split("\n")

    private val exampleInput2 = """
        #################
        #...#...#...#..E#
        #.#.#.#.#.#.#.#.#
        #.#.#.#...#...#.#
        #.#.#.#.###.#.#.#
        #...#.#.#.....#.#
        #.#.#.#.#.#####.#
        #.#...#.#.#.....#
        #.#.#####.#.###.#
        #.#.#.......#...#
        #.#.###.#####.###
        #.#.#...#.....#.#
        #.#.#.#####.###.#
        #.#.#.........#.#
        #.#.#.#########.#
        #S#.............#
        #################
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day16 = Day16(exampleInput)
        assertEquals(7036, day16.solvePart1())
    }

    @Test
    fun testPartOneExample2() {
        val day16 = Day16(exampleInput2)
        assertEquals(11048, day16.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day16 = Day16(resourceAsList("2024/day16.txt"))
        assertEquals(72428, day16.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day16 = Day16(exampleInput)
        assertEquals(45, day16.solvePart2())
    }

    @Test
    fun testPartTwoExample2() {
        val day16 = Day16(exampleInput2)
        assertEquals(64, day16.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day16 = Day16(resourceAsList("2024/day16.txt"))
        assertEquals(456, day16.solvePart2())
    }
}