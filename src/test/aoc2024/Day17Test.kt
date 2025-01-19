package test.aoc2024

import aoc2024.Day17
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day17Test {
    private val exampleInput = """
        Register A: 729
        Register B: 0
        Register C: 0

        Program: 0,1,5,4,3,0
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day17 = Day17(exampleInput)
        assertEquals("4,6,3,5,6,3,5,2,1,0", day17.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day17 = Day17(resourceAsList("2024/day17.txt"))
        assertEquals("7,4,2,0,5,0,5,3,7", day17.solvePart1())
    }


    @Test
    fun partTwoRealInput() {
        val day17 = Day17(resourceAsList("2024/day17.txt"))
        assertEquals(202991746427434, day17.solvePart2())
    }
}