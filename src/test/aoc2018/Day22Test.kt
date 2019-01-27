package test.aoc2018

import aoc2018.Day22
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day22Test {
    val testInput = listOf("depth: 510",
            "target: 10,10"
    )

    @Test
    fun partOneTestInput() {
        val d22 = Day22(testInput)
        //d22.printMap(15, 15)
        assertEquals(114, d22.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals(8681, Day22(resourceAsList("2018/day22.txt")).solvePart1())
    }

    @Test
    fun partTwoTestInput() {
        val d22 = Day22(testInput)
        assertEquals(45, d22.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(1070, Day22(resourceAsList("2018/day22.txt")).solvePart2())
    }
}




