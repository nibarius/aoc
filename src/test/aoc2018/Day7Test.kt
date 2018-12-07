package test.aoc2018

import aoc2018.Day7
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day7Test {
    private val testInput = listOf(
            "Step C must be finished before step A can begin.",
            "Step C must be finished before step F can begin.",
            "Step A must be finished before step B can begin.",
            "Step A must be finished before step D can begin.",
            "Step B must be finished before step E can begin.",
            "Step D must be finished before step E can begin.",
            "Step F must be finished before step E can begin."
    )

    @Test
    fun testPart1Examples() {
        assertEquals("CABDFE", Day7(testInput).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals("JKNSTHCBGRVDXWAYFOQLMPZIUE", Day7(resourceAsList("2018/day7.txt")).solvePart1())
    }


    @Test
    fun testPart2Examples() {
        assertEquals(15, Day7(testInput).solvePart2(2, 0))
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(755, Day7(resourceAsList("2018/day7.txt")).solvePart2(5, 60))
    }
}




