package test.aoc2018

import aoc2018.Day17
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day17Test {
    private val testInput = listOf(
            "x=495, y=2..7",
            "y=7, x=495..501",
            "x=501, y=3..7",
            "x=498, y=2..4",
            "x=506, y=1..2",
            "x=498, y=10..13",
            "x=504, y=10..13",
            "y=13, x=498..504"
    )

    @Test
    fun testPart1Example() {
        assertEquals(57, Day17(testInput).solvePart1())
    }

    @Test
    fun testContainedTub() {
        val input = listOf(
                "...+.........",
                ".............",
                "..#.......#..",
                "..#..#.#..#..",
                "..#..###..#..",
                "..#.......#..",
                "..#########..",
                "............."
        )
        val d = Day17(input, true)
        val ret = d.solvePart1()
        d.printArea()
        assertEquals(35, ret)
    }

    @Test
    fun testContainedTub2() {
        val input = listOf(
                ".....+.......",
                ".............",
                "..#.......#..",
                "..#..#.#..#..",
                "..#..###..#..",
                "..#.......#..",
                "..#########..",
                "............."
        )
        val d = Day17(input, true)
        val ret = d.solvePart1()
        d.printArea()
        assertEquals(35, ret)
    }

    @Test
    fun testPartiallyContainedTub1() {
        val input = listOf(
                ".....+.....",
                "...........",
                "....#.#....",
                "....###..#.",
                ".#.......#.",
                ".#########.",
                "..........."
        )
        val d = Day17(input, true)
        val ret = d.solvePart1()
        d.printArea()
        assertEquals(26, ret)
    }

    @Test
    fun testPartiallyContainedTub2() {
        val input = listOf(
                ".....+.....",
                "...........",
                "....#.#....",
                ".#..###....",
                ".#.......#.",
                ".#########.",
                "..........."
        )
        val d = Day17(input, true)
        val ret = d.solvePart1()
        d.printArea()
        assertEquals(26, ret)
    }

    @Test
    fun testTwoStreams() {
        val input = listOf(
                "......+.......",
                ".....#.#......",
                ".....###......",
                "..............",
                "..#.......#...",
                "..#########...",
                ".............."
        )
        val d = Day17(input, true)
        val ret = d.solvePart1()
        d.printArea()
        assertEquals(29, ret)
    }

    @Test
    fun partOneRealInput() {
        assertEquals(31383, Day17(resourceAsList("2018/day17.txt")).solvePart1())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(25376, Day17(resourceAsList("2018/day17.txt")).solvePart2())
    }
}




