package test.aoc2022

import aoc2022.Day20
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day20Test {
    private val exampleInput = """
        1
        2
        -3
        3
        -2
        0
        4
    """.trimIndent().split("\n")


    @Test
    fun testMinStepsToMoveForward() {
        val sequence = Day20.Companion.Sequence(listOf(0, 1, 2, 3, 4, 5, 6), 1)
        assertEquals(0, sequence.getMinStepsToMove(0))
        assertEquals(1, sequence.getMinStepsToMove(1))
        assertEquals(2, sequence.getMinStepsToMove(2))
        assertEquals(3, sequence.getMinStepsToMove(3))
        assertEquals(-2, sequence.getMinStepsToMove(4))
        assertEquals(-1, sequence.getMinStepsToMove(5))
        assertEquals(0, sequence.getMinStepsToMove(6))
        assertEquals(1, sequence.getMinStepsToMove(7))
        assertEquals(2, sequence.getMinStepsToMove(8))
        assertEquals(3, sequence.getMinStepsToMove(9))
        assertEquals(-2, sequence.getMinStepsToMove(10))
        assertEquals(-1, sequence.getMinStepsToMove(11))
        assertEquals(0, sequence.getMinStepsToMove(12))
    }

    @Test
    fun testMinStepsToMoveForwardEvenEntries() {
        val sequence = Day20.Companion.Sequence(listOf(0, 1, 2, 3, 4, 5), 1)
        assertEquals(0, sequence.getMinStepsToMove(0))
        assertEquals(1, sequence.getMinStepsToMove(1))
        assertEquals(2, sequence.getMinStepsToMove(2))
        assertEquals(-2, sequence.getMinStepsToMove(3))
        assertEquals(-1, sequence.getMinStepsToMove(4))
        assertEquals(0, sequence.getMinStepsToMove(5))
        assertEquals(1, sequence.getMinStepsToMove(6))
        assertEquals(2, sequence.getMinStepsToMove(7))
        assertEquals(-2, sequence.getMinStepsToMove(8))
        assertEquals(-1, sequence.getMinStepsToMove(9))
        assertEquals(0, sequence.getMinStepsToMove(10))
    }

    @Test
    fun testMinStepsToMoveBackward() {
        val sequence = Day20.Companion.Sequence(listOf(0, 1, 2, 3, 4, 5, 6), 1)
        assertEquals(0, sequence.getMinStepsToMove(0))
        assertEquals(-1, sequence.getMinStepsToMove(-1))
        assertEquals(-2, sequence.getMinStepsToMove(-2))
        assertEquals(-3, sequence.getMinStepsToMove(-3))
        assertEquals(2, sequence.getMinStepsToMove(-4))
        assertEquals(1, sequence.getMinStepsToMove(-5))
        assertEquals(0, sequence.getMinStepsToMove(-6))
        assertEquals(-1, sequence.getMinStepsToMove(-7))
        assertEquals(-2, sequence.getMinStepsToMove(-8))
        assertEquals(-3, sequence.getMinStepsToMove(-9))
        assertEquals(2, sequence.getMinStepsToMove(-10))
        assertEquals(1, sequence.getMinStepsToMove(-11))
        assertEquals(0, sequence.getMinStepsToMove(-12))
    }

    @Test
    fun testMinStepsToMoveBackwardEven() {
        val sequence = Day20.Companion.Sequence(listOf(0, 1, 2, 3, 4, 5), 1)
        assertEquals(0, sequence.getMinStepsToMove(0))
        assertEquals(-1, sequence.getMinStepsToMove(-1))
        assertEquals(-2, sequence.getMinStepsToMove(-2))
        assertEquals(2, sequence.getMinStepsToMove(-3))
        assertEquals(1, sequence.getMinStepsToMove(-4))
        assertEquals(0, sequence.getMinStepsToMove(-5))
        assertEquals(-1, sequence.getMinStepsToMove(-6))
        assertEquals(-2, sequence.getMinStepsToMove(-7))
        assertEquals(2, sequence.getMinStepsToMove(-8))
        assertEquals(1, sequence.getMinStepsToMove(-9))
        assertEquals(0, sequence.getMinStepsToMove(-10))
    }

    @Test
    fun testPartOneExample1() {
        val day20 = Day20(exampleInput)
        assertEquals(3, day20.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day20 = Day20(resourceAsList("2022/day20.txt"))
        assertEquals(13522, day20.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day20 = Day20(exampleInput)
        assertEquals(1623178306, day20.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day20 = Day20(resourceAsList("2022/day20.txt"))
        assertEquals(17113168880158, day20.solvePart2())
    }
}