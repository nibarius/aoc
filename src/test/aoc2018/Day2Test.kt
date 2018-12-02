package test.aoc2018

import aoc2018.Day2
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day2Test {
    private val testInput = listOf("abcdef",
            "bababc",
            "abbcde",
            "abcccd",
            "aabcdd",
            "abcdee",
            "ababab"
    )

    @Test
    fun testPart1Examples() {
        assertEquals(12, Day2(testInput).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals(9139, Day2(resourceAsList("2018/day2.txt")).solvePart1())
    }

    private val testInput2 = listOf("abcde",
            "fghij",
            "klmno",
            "pqrst",
            "fguij",
            "axcye",
            "wvxyz"
    )

    @Test
    fun testPart2Examples() {
        assertEquals("fgij", Day2(testInput2).solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals("uqcidadzwtnhsljvxyobmkfyr", Day2(resourceAsList("2018/day2.txt")).solvePart2())
    }
}




