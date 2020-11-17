package test.aoc2019

import Pos
import aoc2019.Day10
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList
import kotlin.math.PI

class Day10Test {
    @Test
    fun testPartOneExample1() {
        val input = """
              .#..#
              .....
              #####
              ....#
              ...##""".trimIndent().split("\n")
        assertEquals(8, Day10(input).solvePart1())
    }

    @Test
    fun testPartOneExample2() {
        val input = """
            ......#.#.
            #..#.#....
            ..#######.
            .#.#.###..
            .#..#.....
            ..#....#.#
            #..#....#.
            .##.#..###
            ##...#..#.
            .#....####
        """.trimIndent().split("\n")
        assertEquals(33, Day10(input).solvePart1())
    }

    @Test
    fun testPartOneExample3() {
        val input = """
            #.#...#.#.
            .###....#.
            .#....#...
            ##.#.#.#.#
            ....#.#.#.
            .##..###.#
            ..#...##..
            ..##....##
            ......#...
            .####.###.""".trimIndent().split("\n")
        assertEquals(35, Day10(input).solvePart1())
    }

    @Test
    fun testPartOneExample4() {
        val input = """
            .#..#..###
            ####.###.#
            ....###.#.
            ..###.##.#
            ##.##.#.#.
            ....###..#
            ..#.#..#.#
            #..#.#.###
            .##...##.#
            .....#.#..""".trimIndent().split("\n")
        assertEquals(41, Day10(input).solvePart1())
    }

    @Test
    fun testPartOneExample5() {
        val input = """
            .#..##.###...#######
            ##.############..##.
            .#.######.########.#
            .###.#######.####.#.
            #####.##.#.##.###.##
            ..#####..#.#########
            ####################
            #.####....###.#.#.##
            ##.#################
            #####.##.###..####..
            ..######..##.#######
            ####.##.####...##..#
            .#####..#.######.###
            ##...#.##########...
            #.##########.#######
            .####.#.###.###.#.##
            ....##.##.###..#####
            .#.#.###########.###
            #.#.#.#####.####.###
            ###.##.####.##.#..##""".trimIndent().split("\n")
        assertEquals(210, Day10(input).solvePart1())
    }

    @Test
    fun testMagic() {
        val self = Pos(5, 5)
        val up = Pos(5, 0)
        val right = Pos(10, 5)
        val down = Pos(5, 10)
        val left = Pos(0, 5)
        assertEquals(0.0, Day10.Angle.calculate(self, up).value, 0.01)
        assertEquals(-PI / 2, Day10.Angle.calculate(self, right).value, 0.01)
        assertEquals(-PI, Day10.Angle.calculate(self, down).value, 0.01)
        assertEquals(-(PI * 1.5), Day10.Angle.calculate(self, left).value, 0.01)
    }

    @Test
    fun partOneRealInput() {
        assertEquals(282, Day10(resourceAsList("2019/day10.txt")).solvePart1())
    }

    // Only for debugging, doesn't return any meaningful value
    @Test
    fun testPartTwoPartialExample() {
        val input = """
            .#....#####...#..
            ##...##.#####..##
            ##...#...#.#####.
            ..#.....#...###..
            ..#.#.....#....##""".trimIndent().split("\n")
        assertEquals(-1, Day10(input).solvePart2())
    }

    @Test
    fun testPartTwoExample() {
        val input = """
            .#..##.###...#######
            ##.############..##.
            .#.######.########.#
            .###.#######.####.#.
            #####.##.#.##.###.##
            ..#####..#.#########
            ####################
            #.####....###.#.#.##
            ##.#################
            #####.##.###..####..
            ..######..##.#######
            ####.##.####...##..#
            .#####..#.######.###
            ##...#.##########...
            #.##########.#######
            .####.#.###.###.#.##
            ....##.##.###..#####
            .#.#.###########.###
            #.#.#.#####.####.###
            ###.##.####.##.#..##""".trimIndent().split("\n")
        assertEquals(802, Day10(input).solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(1008, Day10(resourceAsList("2019/day10.txt")).solvePart2())
    }
}