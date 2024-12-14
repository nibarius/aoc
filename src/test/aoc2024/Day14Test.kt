package test.aoc2024

import aoc2024.Day14
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day14Test {
    private val exampleInput = """
        p=0,4 v=3,-3
        p=6,3 v=-1,-3
        p=10,3 v=-1,2
        p=2,0 v=2,-1
        p=0,0 v=1,3
        p=3,0 v=-2,-2
        p=7,6 v=-1,-3
        p=3,0 v=-1,-2
        p=9,3 v=2,3
        p=7,3 v=-1,2
        p=2,4 v=2,-3
        p=9,5 v=-3,-3
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day14 = Day14(exampleInput, 11, 7)
        assertEquals(12, day14.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day14 = Day14(resourceAsList("2024/day14.txt"))
        assertEquals(208437768, day14.solvePart1())
    }

    @Test
    fun partTwoRealInput() {
        val day14 = Day14(resourceAsList("2024/day14.txt"))
        assertEquals(7492, day14.solvePart2())
    }
}