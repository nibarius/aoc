package test.aoc2024

import aoc2024.Day5
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceSplitOnBlankLines

class Day5Test {
    private val exampleInput = """
        47|53
        97|13
        97|61
        97|47
        75|29
        61|13
        75|53
        29|13
        97|29
        53|29
        61|53
        97|53
        61|29
        47|13
        75|47
        97|75
        47|61
        75|61
        47|29
        75|13
        53|13

        75,47,61,53,29
        97,61,53,29,13
        75,29,13
        75,97,47,61,53
        61,13,29
        97,13,75,29,47
    """.trimIndent().split("\n\n")

    @Test
    fun testPartOneExample1() {
        val day5 = Day5(exampleInput)
        assertEquals(143, day5.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day5 = Day5(resourceSplitOnBlankLines("2024/day5.txt"))
        assertEquals(5955, day5.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day5 = Day5(exampleInput)
        assertEquals(123, day5.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day5 = Day5(resourceSplitOnBlankLines("2024/day5.txt"))
        assertEquals(4030, day5.solvePart2())
    }
}