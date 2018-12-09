package test.aoc2018

import aoc2018.Day9
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsString

class Day9Test {
    private val testInput1 = "10 players; last marble is worth 1618 points"
    private val testInput2 = "13 players; last marble is worth 7999 points"
    private val testInput3 = "17 players; last marble is worth 1104 points"
    private val testInput4 = "21 players; last marble is worth 6111 points"
    private val testInput5 = "30 players; last marble is worth 5807 points"

    @Test
    fun testPart1Examples() {
        assertEquals(8317, Day9(testInput1).solvePart1())
        assertEquals(146373, Day9(testInput2).solvePart1())
        assertEquals(2764, Day9(testInput3).solvePart1())
        assertEquals(54718, Day9(testInput4).solvePart1())
        assertEquals(37305, Day9(testInput5).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals(439635, Day9(resourceAsString("2018/day9.txt")).solvePart1())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(3562722971, Day9(resourceAsString("2018/day9.txt")).solvePart2())
    }
}




