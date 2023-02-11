package test.aoc2022

import aoc2022.Day25
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day25Test {
    private val exampleInput = """
        1=-0-2
        12111
        2=0=
        21
        2=01
        111
        20012
        112
        1=-1=
        1-12
        12
        1=
        122
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day25 = Day25(exampleInput)
        assertEquals("2=-1=0", day25.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day25 = Day25(resourceAsList("2022/day25.txt"))
        assertEquals("2-212-2---=00-1--102", day25.solvePart1())
    }
}