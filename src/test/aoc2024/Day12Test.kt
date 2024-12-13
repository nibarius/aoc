package test.aoc2024

import aoc2024.Day12
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day12Test {
    private val exampleInput = """
        RRRRIICCFF
        RRRRIICCCF
        VVRRRCCFFF
        VVRCCCJFFF
        VVVVCJJCFE
        VVIVCCJJEE
        VVIIICJJEE
        MIIIIIJJEE
        MIIISIJEEE
        MMMISSJEEE
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day12 = Day12(exampleInput)
        assertEquals(1930, day12.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day12 = Day12(resourceAsList("2024/day12.txt"))
        assertEquals(1456082, day12.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day12 = Day12(exampleInput)
        assertEquals(1206, day12.solvePart2())
    }

    @Test
    fun testPartTwoExtraExample1() {
        val exampleInput = """
        AA
        AA
    """.trimIndent().split("\n")
        val day12 = Day12(exampleInput)
        assertEquals(16, day12.solvePart2())
    }


    @Test
    fun partTwoRealInput() {
        val day12 = Day12(resourceAsList("2024/day12.txt"))
        assertEquals(872382, day12.solvePart2())
    }
}