package test.aoc2020

import aoc2020.Day23
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList
import resourceAsString

class Day23Test {
    private val exampleInput = """389125467"""

    @Test
    fun testPartOneExample1() {
        val day23 = Day23(exampleInput)
        assertEquals("67384529", day23.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day23 = Day23(resourceAsString("2020/day23.txt"))
        assertEquals("45798623", day23.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day23 = Day23(exampleInput)
        assertEquals(149245887792, day23.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day23 = Day23(resourceAsString("2020/day23.txt"))
        assertEquals(235551949822, day23.solvePart2())
    }
}