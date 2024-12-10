package test.aoc2024

import aoc2024.Day10
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day10Test {
    private val exampleInput = """
        89010123
        78121874
        87430965
        96549874
        45678903
        32019012
        01329801
        10456732
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day10 = Day10(exampleInput)
        assertEquals(36, day10.solvePart1())
    }

    @Test
    fun testPartOneExample2() {
        val exampleInput = """
            ...0...
            ...1...
            ...2...
            6543456
            7.....7
            8.....8
            9.....9
        """.trimIndent().split("\n")
        val day10 = Day10(exampleInput)
        assertEquals(2, day10.solvePart1())
    }

    @Test
    fun testPartOneExample3() {
        val exampleInput = """
            ..90..9
            ...1.98
            ...2..7
            6543456
            765.987
            876....
            987....
        """.trimIndent().split("\n")
        val day10 = Day10(exampleInput)
        assertEquals(4, day10.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day10 = Day10(resourceAsList("2024/day10.txt"))
        assertEquals(510, day10.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day10 = Day10(exampleInput)
        assertEquals(81, day10.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day10 = Day10(resourceAsList("2024/day10.txt"))
        assertEquals(1058, day10.solvePart2())
    }
}