package test.aoc2022

import aoc2022.Day10
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day10Test {
    private val exampleInput = """
        addx 15
        addx -11
        addx 6
        addx -3
        addx 5
        addx -1
        addx -8
        addx 13
        addx 4
        noop
        addx -1
        addx 5
        addx -1
        addx 5
        addx -1
        addx 5
        addx -1
        addx 5
        addx -1
        addx -35
        addx 1
        addx 24
        addx -19
        addx 1
        addx 16
        addx -11
        noop
        noop
        addx 21
        addx -15
        noop
        noop
        addx -3
        addx 9
        addx 1
        addx -3
        addx 8
        addx 1
        addx 5
        noop
        noop
        noop
        noop
        noop
        addx -36
        noop
        addx 1
        addx 7
        noop
        noop
        noop
        addx 2
        addx 6
        noop
        noop
        noop
        noop
        noop
        addx 1
        noop
        noop
        addx 7
        addx 1
        noop
        addx -13
        addx 13
        addx 7
        noop
        addx 1
        addx -33
        noop
        noop
        noop
        addx 2
        noop
        noop
        noop
        addx 8
        noop
        addx -1
        addx 2
        addx 1
        noop
        addx 17
        addx -9
        addx 1
        addx 1
        addx -3
        addx 11
        noop
        noop
        addx 1
        noop
        addx 1
        noop
        noop
        addx -13
        addx -19
        addx 1
        addx 3
        addx 26
        addx -30
        addx 12
        addx -1
        addx 3
        addx 1
        noop
        noop
        noop
        addx -9
        addx 18
        addx 1
        addx 2
        noop
        noop
        addx 9
        noop
        noop
        noop
        addx -1
        addx 2
        addx -37
        addx 1
        addx 3
        noop
        addx 15
        addx -21
        addx 22
        addx -6
        addx 1
        noop
        addx 2
        addx 1
        noop
        addx -10
        noop
        noop
        addx 20
        addx 1
        addx 2
        addx 2
        addx -6
        addx -11
        noop
        noop
        noop
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day10 = Day10(exampleInput)
        assertEquals(13140, day10.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day10 = Day10(resourceAsList("2022/day10.txt"))
        assertEquals(15880, day10.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day10 = Day10(exampleInput)
        val expected = """
            ##..##..##..##..##..##..##..##..##..##..
            ###...###...###...###...###...###...###.
            ####....####....####....####....####....
            #####.....#####.....#####.....#####.....
            ######......######......######......####
            #######.......#######.......#######.....
        """.trimIndent()
        assertEquals(expected, day10.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        // PLGFKAZG
        val expected = """
            ###..#.....##..####.#..#..##..####..##..
            #..#.#....#..#.#....#.#..#..#....#.#..#.
            #..#.#....#....###..##...#..#...#..#....
            ###..#....#.##.#....#.#..####..#...#.##.
            #....#....#..#.#....#.#..#..#.#....#..#.
            #....####..###.#....#..#.#..#.####..###.
        """.trimIndent()
        val day10 = Day10(resourceAsList("2022/day10.txt"))
        assertEquals(expected, day10.solvePart2())
    }
}