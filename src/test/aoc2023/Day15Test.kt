package test.aoc2023

import aoc2023.Day15
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day15Test {
    private val exampleInput = """rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7""".trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day15 = Day15(exampleInput)
        assertEquals(1320, day15.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day15 = Day15(resourceAsList("2023/day15.txt"))
        assertEquals(508498, day15.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day15 = Day15(exampleInput)
        assertEquals(145, day15.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day15 = Day15(resourceAsList("2023/day15.txt"))
        assertEquals(279116, day15.solvePart2())
    }
}