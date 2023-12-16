package test.aoc2023

import aoc2023.Day16
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day16Test {
    private val exampleInput = """
        .|...\....
        |.-.\.....
        .....|-...
        ........|.
        ..........
        .........\
        ..../.\\..
        .-.-/..|..
        .|....-|.\
        ..//.|....
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day16 = Day16(exampleInput)
        assertEquals(46, day16.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day16 = Day16(resourceAsList("2023/day16.txt"))
        assertEquals(7517, day16.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day16 = Day16(exampleInput)
        assertEquals(51, day16.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day16 = Day16(resourceAsList("2023/day16.txt"))
        assertEquals(7741, day16.solvePart2())
    }
}