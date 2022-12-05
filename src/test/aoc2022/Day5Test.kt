package test.aoc2022

import aoc2022.Day5
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList
import resourceSplitOnBlankLines

class Day5Test {
    private val exampleInput = """
    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3 

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2
    """.trimIndent().split("\n\n")

    @Test
    fun testPartOneExample1() {
        val day5 = Day5(exampleInput)
        assertEquals("CMZ", day5.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day5 = Day5(resourceSplitOnBlankLines("2022/day5.txt"))
        assertEquals("RLFNRTNFB", day5.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day5 = Day5(exampleInput)
        assertEquals("MCD", day5.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day5 = Day5(resourceSplitOnBlankLines("2022/day5.txt"))
        assertEquals("MHQTLJRLB", day5.solvePart2())
    }
}