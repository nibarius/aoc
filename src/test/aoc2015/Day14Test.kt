package test.aoc2015

import aoc2015.Day14
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day14Test {
    @Test
    fun testPartOneExample1() {
        val input = """
            Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.
            Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds.
        """.trimIndent().split("\n")
        assertEquals(1120, Day14(input).solvePart1(1000))
    }

    @Test
    fun partOneRealInput() {
        assertEquals(2660, Day14(resourceAsList("2015/day14.txt")).solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val input = """
            Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.
            Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds.
        """.trimIndent().split("\n")
        assertEquals(689, Day14(input).solvePart2(1000))
    }

    @Test
    fun raceSimulationTest() {
        val input = """
            Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.
            Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds.
        """.trimIndent().split("\n")
        val d14 = Day14(input)
        for (i in 1..1000) {
            d14.reindeers.forEach { it.tick() }
            d14.reindeers.forEach { assertEquals("Second $i, ${it.Name}", it.distanceAfter(i), it.currentDistance) }
        }
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(1256, Day14(resourceAsList("2015/day14.txt")).solvePart2())
    }
}