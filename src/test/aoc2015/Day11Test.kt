package test.aoc2015

import aoc2015.Day11
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsString

class Day11Test {
    @Test
    fun testPartOneExample1() {
        assertEquals(false, Day11("").isValid("hijklmmn"))
    }

    @Test
    fun testPartOneExample2() {
        assertEquals(false, Day11("").isValid("abbceffg"))
    }

    @Test
    fun testPartOneExample3() {
        assertEquals(false, Day11("").isValid("abbcegjk"))
    }

    @Test
    fun testPartOneExample4() {
        assertEquals("abcdffaa", Day11("abcdefgh").solvePart1())
    }

    @Test
    fun testPartOneBonus() {
        assertEquals(true, Day11("").isValid("abcdffaa"))
        assertEquals(true, Day11("").isValid("ghjaabcc"))
    }

    @Test
    fun partOneRealInput() {
        assertEquals("hxbxxyzz", Day11(resourceAsString("2015/day11.txt")).solvePart1())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals("hxcaabcc", Day11(resourceAsString("2015/day11.txt")).solvePart2())
    }
}