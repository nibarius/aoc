package test.aoc2018

import aoc2018.Day12
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day12Test {
    val testInput = listOf(
            "initial state: #..#.#..##......###...###",
            "",
            "...## => #",
            "..#.. => #",
            ".#... => #",
            ".#.#. => #",
            ".#.## => #",
            ".##.. => #",
            ".#### => #",
            "#.#.# => #",
            "#.### => #",
            "##.#. => #",
            "##.## => #",
            "###.. => #",
            "###.# => #",
            "####. => #"
    )

    @Test
    fun testPart1Examples() {
        assertEquals(325, Day12(testInput).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals(2140, Day12(resourceAsList("2018/day12.txt")).solvePart1())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(1900000000384, Day12(resourceAsList("2018/day12.txt")).solvePart2())
    }
}




