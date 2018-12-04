package test.aoc2018

import aoc2018.Day4
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day4Test {
    private val testInput = listOf(
            "[1518-11-01 00:00] Guard #10 begins shift",
            "[1518-11-01 00:25] wakes up",
            "[1518-11-03 00:05] Guard #10 begins shift",
            "[1518-11-01 00:30] falls asleep",
            "[1518-11-01 00:55] wakes up",
            "[1518-11-02 00:40] falls asleep",
            "[1518-11-02 00:50] wakes up",
            "[1518-11-03 00:24] falls asleep",
            "[1518-11-03 00:29] wakes up",
            "[1518-11-04 00:02] Guard #99 begins shift",
            "[1518-11-05 00:03] Guard #99 begins shift",
            "[1518-11-01 00:05] falls asleep",
            "[1518-11-04 00:36] falls asleep",
            "[1518-11-01 23:58] Guard #99 begins shift",
            "[1518-11-04 00:46] wakes up",
            "[1518-11-05 00:45] falls asleep",
            "[1518-11-05 00:55] wakes up"
    )

    @Test
    fun testPart1Examples() {
        assertEquals(240, Day4(testInput).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals(38813, Day4(resourceAsList("2018/day4.txt")).solvePart1())
    }


    @Test
    fun testPart2Examples() {
        assertEquals(4455, Day4(testInput).solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(141071, Day4(resourceAsList("2018/day4.txt")).solvePart2())
    }
}




