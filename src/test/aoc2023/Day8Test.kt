package test.aoc2023

import aoc2023.Day8
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day8Test {
    private val exampleInput = """
        LLR

        AAA = (BBB, BBB)
        BBB = (AAA, ZZZ)
        ZZZ = (ZZZ, ZZZ)
    """.trimIndent().split("\n")

    private val exampleInput2 = """
        LR

        11A = (11B, XXX)
        11B = (XXX, 11Z)
        11Z = (11B, XXX)
        22A = (22B, XXX)
        22B = (22C, 22C)
        22C = (22Z, 22Z)
        22Z = (22B, 22B)
        XXX = (XXX, XXX)
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day8 = Day8(exampleInput)
        assertEquals(6, day8.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day8 = Day8(resourceAsList("2023/day8.txt"))
        assertEquals(16579, day8.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day8 = Day8(exampleInput2)
        assertEquals(6, day8.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day8 = Day8(resourceAsList("2023/day8.txt"))
        assertEquals(12927600769609, day8.solvePart2())
    }
}