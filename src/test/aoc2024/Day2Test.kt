package test.aoc2024

import aoc2024.Day2
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day2Test {
    private val exampleInput = """
        7 6 4 2 1
        1 2 7 8 9
        9 7 6 2 1
        1 3 2 4 5
        8 6 4 4 1
        1 3 6 7 9
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day2 = Day2(exampleInput)
        assertEquals(2, day2.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day2 = Day2(resourceAsList("2024/day2.txt"))
        assertEquals(334, day2.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day2 = Day2(exampleInput)
        assertEquals(4, day2.solvePart2())
    }

    @Test
    fun testPartTwoExtra1() {
        // Safe when removing first and safe when removing last
        val exampleInput = """
            99 1 2 4 5
            3 1 2 4 5
            1 2 4 99
        """.trimIndent().split("\n")
        val day2 = Day2(exampleInput)
        assertEquals(3, day2.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day2 = Day2(resourceAsList("2024/day2.txt"))
        assertEquals(400, day2.solvePart2())
    }
}