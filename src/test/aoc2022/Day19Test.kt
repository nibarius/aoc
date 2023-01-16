package test.aoc2022

import aoc2022.Day19
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day19Test {
    private val exampleInput = """
Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.
Blueprint 2: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 8 clay. Each geode robot costs 3 ore and 12 obsidian.
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day19 = Day19(exampleInput)
        assertEquals(33, day19.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day19 = Day19(resourceAsList("2022/day19.txt"))
        assertEquals(1675, day19.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day19 = Day19(exampleInput)
        assertEquals(3472, day19.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day19 = Day19(resourceAsList("2022/day19.txt"))
        assertEquals(6840, day19.solvePart2())
    }
}