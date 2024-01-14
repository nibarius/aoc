package test.aoc2023

import aoc2023.Day21
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day21Test {
    private val exampleInput = """
        ...........
        .....###.#.
        .###.##..#.
        ..#.#...#..
        ....#.#....
        .##..S####.
        .##..#...#.
        .......##..
        .##.#.####.
        .##..##.##.
        ...........
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day21 = Day21(exampleInput)
        assertEquals(16, day21.solvePart1(6))
    }

    @Test
    fun partOneRealInput() {
        val day21 = Day21(resourceAsList("2023/day21.txt"))
        assertEquals(3716, day21.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day21 = Day21(exampleInput)
        assertEquals(16, day21.solvePart2(6))
    }

    @Test
    fun testPartTwoExample2() {
        val day21 = Day21(exampleInput)
        assertEquals(50, day21.solvePart2(10))
    }

    @Test
    fun testPartTwoExample3() {
        val day21 = Day21(exampleInput)
        assertEquals(1594, day21.solvePart2(50))
    }

    @Test
    fun testPartTwoExample4() {
        val day21 = Day21(exampleInput)
        assertEquals(6536, day21.solvePart2(100))
    }

    @Test
    fun testPartTwoExample5() {
        val day21 = Day21(exampleInput)
        assertEquals(668697, day21.solvePart2(1000))
    }

    @Test
    fun partTwoRealInput() {
        val day21 = Day21(resourceAsList("2023/day21.txt"))
        assertEquals(0, day21.solvePart2())
    }
}