package test.aoc2021

import aoc2021.Day24
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day24Test {
    @Test
    fun partOneRealInput() {
        val day24 = Day24(resourceAsList("2021/day24.txt"))
        assertEquals(29991993698469, day24.solvePart1())
    }

    @Test
    fun partTwoRealInput() {
        val day24 = Day24(resourceAsList("2021/day24.txt"))
        assertEquals(14691271141118, day24.solvePart2())
    }
}