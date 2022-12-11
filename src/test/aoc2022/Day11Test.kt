package test.aoc2022

import aoc2022.Day11
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList
import resourceSplitOnBlankLines

class Day11Test {
    private val exampleInput = """
Monkey 0:
  Starting items: 79, 98
  Operation: new = old * 19
  Test: divisible by 23
    If true: throw to monkey 2
    If false: throw to monkey 3

Monkey 1:
  Starting items: 54, 65, 75, 74
  Operation: new = old + 6
  Test: divisible by 19
    If true: throw to monkey 2
    If false: throw to monkey 0

Monkey 2:
  Starting items: 79, 60, 97
  Operation: new = old * old
  Test: divisible by 13
    If true: throw to monkey 1
    If false: throw to monkey 3

Monkey 3:
  Starting items: 74
  Operation: new = old + 3
  Test: divisible by 17
    If true: throw to monkey 0
    If false: throw to monkey 1
    """.trimIndent().split("\n\n")

    @Test
    fun testPartOneExample1() {
        val day11 = Day11(exampleInput)
        assertEquals(10605, day11.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day11 = Day11(resourceSplitOnBlankLines("2022/day11.txt"))
        assertEquals(120756, day11.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day11 = Day11(exampleInput)
        assertEquals(2713310158, day11.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day11 = Day11(resourceSplitOnBlankLines("2022/day11.txt"))
        assertEquals(39109444654, day11.solvePart2())
    }
}