package test.aoc2015

import aoc2015.Day13
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day13Test {
    @Test
    fun testPartOneExample1() {
        val input = """
            Alice would gain 54 happiness units by sitting next to Bob.
            Alice would lose 79 happiness units by sitting next to Carol.
            Alice would lose 2 happiness units by sitting next to David.
            Bob would gain 83 happiness units by sitting next to Alice.
            Bob would lose 7 happiness units by sitting next to Carol.
            Bob would lose 63 happiness units by sitting next to David.
            Carol would lose 62 happiness units by sitting next to Alice.
            Carol would gain 60 happiness units by sitting next to Bob.
            Carol would gain 55 happiness units by sitting next to David.
            David would gain 46 happiness units by sitting next to Alice.
            David would lose 7 happiness units by sitting next to Bob.
            David would gain 41 happiness units by sitting next to Carol.
        """.trimIndent().split("\n")
        assertEquals(330, Day13(input).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals(733, Day13(resourceAsList("2015/day13.txt")).solvePart1())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(725, Day13(resourceAsList("2015/day13.txt")).solvePart2())
    }
}