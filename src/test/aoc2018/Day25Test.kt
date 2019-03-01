package test.aoc2018

import aoc2018.Day25
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day25Test {


    @Test
    fun partOneTestInput1() {
        val testInput = listOf(
                "0,0,0,0",
                "3,0,0,0",
                "0,3,0,0",
                "0,0,3,0",
                "0,0,0,3",
                "0,0,0,6",
                "9,0,0,0",
                "12,0,0,0"
        )
        assertEquals(2, Day25(testInput).solvePart1())
    }

    @Test
    fun partOneTestInput2() {
        val testInput = listOf(
                "-1,2,2,0",
                "0,0,2,-2",
                "0,0,0,-2",
                "-1,2,0,0",
                "-2,-2,-2,2",
                "3,0,2,-1",
                "-1,3,2,2",
                "-1,0,-1,0",
                "0,2,1,-2",
                "3,0,0,0"
        )
        assertEquals(4, Day25(testInput).solvePart1())
    }

    @Test
    fun partOneTestInput3() {
        val testInput = listOf(
                "1,-1,0,1",
                "2,0,-1,0",
                "3,2,-1,0",
                "0,0,3,1",
                "0,0,-1,-1",
                "2,3,-2,0",
                "-2,2,0,0",
                "2,-2,0,-1",
                "1,-1,0,-1",
                "3,2,0,2"
        )
        assertEquals(3, Day25(testInput).solvePart1())
    }

    @Test
    fun partOneTestInput4() {
        val testInput = listOf(
                "1,-1,-1,-2",
                "-2,-2,0,1",
                "0,2,1,3",
                "-2,3,-2,1",
                "0,2,3,-2",
                "-1,-1,1,-2",
                "0,-2,-1,0",
                "-2,2,3,-1",
                "1,2,2,0",
                "-1,-2,0,-2"
        )
        assertEquals(8, Day25(testInput).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals(-2, Day25(resourceAsList("2018/day25.txt")).solvePart1())
    }

    @Test
    fun partTwoTestInput() {
        assertEquals(-2, Day25(listOf()).solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(-2, Day25(resourceAsList("2018/day25.txt")).solvePart2())
    }
}




