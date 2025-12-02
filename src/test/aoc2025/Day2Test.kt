package test.aoc2025

import aoc2025.Day2
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day2Test {
    private val exampleInput = """11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124""".trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day2 = Day2(exampleInput)
        assertEquals(1227775554, day2.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day2 = Day2(resourceAsList("2025/day2.txt"))
        assertEquals(30608905813, day2.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day2 = Day2(exampleInput)
        assertEquals(4174379265, day2.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day2 = Day2(resourceAsList("2025/day2.txt"))
        assertEquals(31898925685, day2.solvePart2())
    }
}