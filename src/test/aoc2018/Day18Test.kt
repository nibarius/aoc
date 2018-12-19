package test.aoc2018

import aoc2018.Day18
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day18Test {
    private val testInput = listOf(
            ".#.#...|#.",
            ".....#|##|",
            ".|..|...#.",
            "..|#.....#",
            "#.#|||#|#|",
            "...#.||...",
            ".|....|...",
            "||...#|.#|",
            "|.||||..|.",
            "...#.|..|."
    )

    @Test
    fun testPart1Example() {
        assertEquals(1147, Day18(testInput).solvePart1())
    }


    @Test
    fun partOneRealInput() {
        assertEquals(604884, Day18(resourceAsList("2018/day18.txt")).solvePart1())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(190820, Day18(resourceAsList("2018/day18.txt")).solvePart2())
    }
}




