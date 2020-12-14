package test.aoc2020

import aoc2020.Day14
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day14Test {
    private val exampleInput = """
        mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
        mem[8] = 11
        mem[7] = 101
        mem[8] = 0
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day14 = Day14(exampleInput)
        assertEquals(165, day14.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day14 = Day14(resourceAsList("2020/day14.txt"))
        assertEquals(9615006043476, day14.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val exampleInput = """
            mask = 000000000000000000000000000000X1001X
            mem[42] = 100
            mask = 00000000000000000000000000000000X0XX
            mem[26] = 1
        """.trimIndent().split("\n")
        val day14 = Day14(exampleInput)
        assertEquals(208, day14.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day14 = Day14(resourceAsList("2020/day14.txt"))
        assertEquals(4275496544925, day14.solvePart2())
    }
}