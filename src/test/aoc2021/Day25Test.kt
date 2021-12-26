package test.aoc2021

import aoc2021.Day25
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day25Test {
    private val exampleInput = """
        v...>>.vv>
        .vv>>.vv..
        >>.>v>...v
        >>v>>.>.v.
        v>v.vv.v..
        >.>>..v...
        .vv..>.>v.
        v.v..>>v.v
        ....v..v.>
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day25 = Day25(exampleInput)
        assertEquals(58, day25.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day25 = Day25(resourceAsList("2021/day25.txt"))
        assertEquals(532, day25.solvePart1())
    }
}