package test.aoc2022

import aoc2022.Day15
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day15Test {
    private val exampleInput = """
        Sensor at x=2, y=18: closest beacon is at x=-2, y=15
        Sensor at x=9, y=16: closest beacon is at x=10, y=16
        Sensor at x=13, y=2: closest beacon is at x=15, y=3
        Sensor at x=12, y=14: closest beacon is at x=10, y=16
        Sensor at x=10, y=20: closest beacon is at x=10, y=16
        Sensor at x=14, y=17: closest beacon is at x=10, y=16
        Sensor at x=8, y=7: closest beacon is at x=2, y=10
        Sensor at x=2, y=0: closest beacon is at x=2, y=10
        Sensor at x=0, y=11: closest beacon is at x=2, y=10
        Sensor at x=20, y=14: closest beacon is at x=25, y=17
        Sensor at x=17, y=20: closest beacon is at x=21, y=22
        Sensor at x=16, y=7: closest beacon is at x=15, y=3
        Sensor at x=14, y=3: closest beacon is at x=15, y=3
        Sensor at x=20, y=1: closest beacon is at x=15, y=3
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day15 = Day15(exampleInput)
        assertEquals(26, day15.solvePart1(10))
    }

    @Test
    fun partOneRealInput() {
        val day15 = Day15(resourceAsList("2022/day15.txt"))
        assertEquals(5127797, day15.solvePart1(2000000))
    }

    @Test
    fun testPartTwoExample1() {
        val day15 = Day15(exampleInput)
        assertEquals(56000011, day15.solvePart2(20))
    }

    @Test
    fun partTwoRealInput() {
        val day15 = Day15(resourceAsList("2022/day15.txt"))
        assertEquals(12518502636475, day15.solvePart2(4000000))
    }
}