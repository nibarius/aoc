package test.aoc2023

import aoc2023.Day20
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day20Test {
    private val exampleInput = """
        broadcaster -> a, b, c
        %a -> b
        %b -> c
        %c -> inv
        &inv -> a
    """.trimIndent().split("\n")

    private val exampleInput2 = """
        broadcaster -> a
        %a -> inv, con
        &inv -> b
        %b -> con
        &con -> output
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day20 = Day20(exampleInput)
        assertEquals(32000000, day20.solvePart1())
    }

    @Test
    fun testPartOneExample2() {
        val day20 = Day20(exampleInput2)
        assertEquals(11687500, day20.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day20 = Day20(resourceAsList("2023/day20.txt"))
        assertEquals(743090292, day20.solvePart1())
    }

    @Test
    fun partTwoRealInput() {
        val day20 = Day20(resourceAsList("2023/day20.txt"))
        assertEquals(241528184647003, day20.solvePart2())
    }
}