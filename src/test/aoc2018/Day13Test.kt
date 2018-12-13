package test.aoc2018

import aoc2018.Day13
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day13Test {
    val testInput = listOf(
            "/->-\\        ",
            "|   |  /----\\",
            "| /-+--+-\\  |",
            "| | |  | v  |",
            "\\-+-/  \\-+--/",
            "  \\------/   "
    )

    val testInput2 = listOf(
            "/>-<\\  ",
            "|   |  ",
            "| /<+-\\",
            "| | | v",
            "\\>+</ |",
            "  |   ^",
            "  \\<->/"
    )

    @Test
    fun testPart1Example() {
        assertEquals("7,3", Day13(testInput).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals("45,34", Day13(resourceAsList("2018/day13.txt")).solvePart1())
    }

    @Test
    fun testPart2Example() {
        assertEquals("6,4", Day13(testInput2).solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals("91,25", Day13(resourceAsList("2018/day13.txt")).solvePart2())
    }
}




