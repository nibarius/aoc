package test.aoc2020

import aoc2020.Day16
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day16Test {
    private val exampleInput = """
        class: 1-3 or 5-7
        row: 6-11 or 33-44
        seat: 13-40 or 45-50

        your ticket:
        7,1,14

        nearby tickets:
        7,3,47
        40,4,50
        55,2,20
        38,6,12
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day16 = Day16(exampleInput)
        assertEquals(71, day16.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day16 = Day16(resourceAsList("2020/day16.txt"))
        assertEquals(22000, day16.solvePart1())
    }

    @Test
    fun partTwoRealInput() {
        val day16 = Day16(resourceAsList("2020/day16.txt"))
        assertEquals(410460648673, day16.solvePart2())
    }
}