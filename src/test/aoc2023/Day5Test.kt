package test.aoc2023

import aoc2023.Day5
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList
import resourceSplitOnBlankLines

class Day5Test {
    private val exampleInput = """
        seeds: 79 14 55 13

        seed-to-soil map:
        50 98 2
        52 50 48

        soil-to-fertilizer map:
        0 15 37
        37 52 2
        39 0 15

        fertilizer-to-water map:
        49 53 8
        0 11 42
        42 0 7
        57 7 4

        water-to-light map:
        88 18 7
        18 25 70

        light-to-temperature map:
        45 77 23
        81 45 19
        68 64 13

        temperature-to-humidity map:
        0 69 1
        1 0 69

        humidity-to-location map:
        60 56 37
        56 93 4
    """.trimIndent().split("\n\n")

    @Test
    fun testPartOneExample1() {
        val day5 = Day5(exampleInput)
        assertEquals(35, day5.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day5 = Day5(resourceSplitOnBlankLines("2023/day5.txt"))
        assertEquals(309796150, day5.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day5 = Day5(exampleInput)
        assertEquals(46, day5.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day5 = Day5(resourceSplitOnBlankLines("2023/day5.txt"))
        assertEquals(50716416, day5.solvePart2())
    }
}