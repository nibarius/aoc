package test.aoc2020

import aoc2020.Day25
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day25Test {
    private val exampleInput = """
        5764801
        17807724
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day25 = Day25(exampleInput)
        assertEquals(14897079, day25.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day25 = Day25(resourceAsList("2020/day25.txt"))
        assertEquals(0, day25.solvePart1())
    }

}