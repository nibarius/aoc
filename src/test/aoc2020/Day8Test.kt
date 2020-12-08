package test.aoc2020

import aoc2020.Day8
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day8Test {
    private val exampleInput = """
        nop +0
        acc +1
        jmp +4
        acc +3
        jmp -3
        acc -99
        acc +1
        jmp -4
        acc +6
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day8 = Day8(exampleInput)
        assertEquals(5, day8.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day8 = Day8(resourceAsList("2020/day8.txt"))
        assertEquals(1801, day8.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day8 = Day8(exampleInput)
        assertEquals(8, day8.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day8 = Day8(resourceAsList("2020/day8.txt"))
        assertEquals(2060, day8.solvePart2())
    }
}