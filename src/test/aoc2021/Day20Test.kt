package test.aoc2021

import aoc2021.Day20
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day20Test {
    private val exampleInput = """
        ..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..###..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###.######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#..#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#......#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#.....####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#.......##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#

        #..#.
        #....
        ##..#
        ..#..
        ..###
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day20 = Day20(exampleInput)
        assertEquals(35, day20.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day20 = Day20(resourceAsList("2021/day20.txt"))
        assertEquals(4917, day20.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day20 = Day20(exampleInput)
        assertEquals(3351, day20.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day20 = Day20(resourceAsList("2021/day20.txt"))
        assertEquals(16389, day20.solvePart2())
    }

    @Test
    fun toggleAllTest() {
        // 9 filled pixels results in an empty pixel, everything else fills the pixel
        // step 1, everything turns on
        // step 2, everything turns off
        val input = """
            ###############################################################################################################################################################################################################################################################################################################################################################################################################################################################################################################################.
            
            ...
            ...
            ...
        """.trimIndent().split("\n")
        val day20 = Day20(input)
        assertEquals(0, day20.solvePart1())
    }
}