package test.aoc2025

import aoc2025.Day8
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day8Test {
    private val exampleInput = """
        162,817,812
        57,618,57
        906,360,560
        592,479,940
        352,342,300
        466,668,158
        542,29,236
        431,825,988
        739,650,466
        52,470,668
        216,146,977
        819,987,18
        117,168,530
        805,96,715
        346,949,466
        970,615,88
        941,993,340
        862,61,35
        984,92,344
        425,690,689
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day8 = Day8(exampleInput)
        assertEquals(40, day8.solvePart1(10))
    }

    @Test
    fun partOneRealInput() {
        val day8 = Day8(resourceAsList("2025/day8.txt"))
        assertEquals(97384, day8.solvePart1(1000))
    }

    @Test
    fun testPartTwoExample1() {
        val day8 = Day8(exampleInput)
        assertEquals(25272, day8.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day8 = Day8(resourceAsList("2025/day8.txt"))
        assertEquals(9003685096, day8.solvePart2())
    }
}