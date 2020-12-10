package test.aoc2020

import aoc2020.Day10
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day10Test {
    private val exampleInput = """
        16
        10
        15
        5
        1
        11
        7
        19
        6
        12
        4
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day10 = Day10(exampleInput)
        assertEquals(35, day10.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day10 = Day10(resourceAsList("2020/day10.txt"))
        assertEquals(1690, day10.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day10 = Day10(exampleInput)
        assertEquals(8, day10.solvePart2())
    }

    @Test
    fun testPartTwoExample2() {
        val exampleInput = """
            28
            33
            18
            42
            31
            14
            46
            20
            48
            47
            24
            23
            49
            45
            19
            38
            39
            11
            1
            32
            25
            35
            8
            17
            7
            9
            4
            2
            34
            10
            3
        """.trimIndent().split("\n")
        val day10 = Day10(exampleInput)
        assertEquals(19208, day10.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day10 = Day10(resourceAsList("2020/day10.txt"))
        assertEquals(5289227976704, day10.solvePart2())
    }
}