package test.aoc2024

import aoc2024.Day13
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceSplitOnBlankLines

class Day13Test {
    private val exampleInput = """
        Button A: X+94, Y+34
        Button B: X+22, Y+67
        Prize: X=8400, Y=5400

        Button A: X+26, Y+66
        Button B: X+67, Y+21
        Prize: X=12748, Y=12176

        Button A: X+17, Y+86
        Button B: X+84, Y+37
        Prize: X=7870, Y=6450

        Button A: X+69, Y+23
        Button B: X+27, Y+71
        Prize: X=18641, Y=10279
    """.trimIndent().split("\n\n")

    @Test
    fun testPartOneExample1() {
        val day13 = Day13(exampleInput)
        assertEquals(480, day13.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day13 = Day13(resourceSplitOnBlankLines("2024/day13.txt"))
        assertEquals(39748, day13.solvePart1())
    }

    @Test
    fun partTwoRealInput() {
        val day13 = Day13(resourceSplitOnBlankLines("2024/day13.txt"))
        assertEquals(74478585072604, day13.solvePart2())
    }
}