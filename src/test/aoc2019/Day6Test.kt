package test.aoc2019

import aoc2019.Day6
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day6Test {
    @Test
    fun testPart1Examples() {
        val input= listOf(
                "COM)B",
                "B)C",
                "C)D",
                "D)E",
                "E)F",
                "B)G",
                "G)H",
                "D)I",
                "E)J",
                "J)K",
                "K)L"
        )
        assertEquals(42, Day6(input).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals(295936, Day6(resourceAsList("2019/day6.txt")).solvePart1())
    }

    @Test
    fun testPart2Examples() {
        val input = listOf(
                "COM)B",
                "B)C",
                "C)D",
                "D)E",
                "E)F",
                "B)G",
                "G)H",
                "D)I",
                "E)J",
                "J)K",
                "K)L",
                "K)YOU",
                "I)SAN"
        )
        assertEquals(4, Day6(input).solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(457, Day6(resourceAsList("2019/day6.txt")).solvePart2())
    }
}




