package test.aoc2018

import aoc2018.Day6
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day6Test {
    private val testInput = listOf(
            "1, 1",
            "1, 6",
            "8, 3",
            "3, 4",
            "5, 5",
            "8, 9"
    )

    @Test
    fun testWithinFovUp() {
        val loc = Day6.Location(5, 5)
        assertEquals(true, loc.withinFovUp(Day6.Location(5, 4)))
        assertEquals(true, loc.withinFovUp(Day6.Location(6, 2)))
        assertEquals(true, loc.withinFovUp(Day6.Location(4, 3)))
        assertEquals(true, loc.withinFovUp(Day6.Location(4, 4)))
        assertEquals(true, loc.withinFovUp(Day6.Location(6, 4)))
        assertEquals(false, loc.withinFovUp(Day6.Location(5, 6)))
        assertEquals(false, loc.withinFovUp(Day6.Location(6, 5)))
        assertEquals(false, loc.withinFovUp(Day6.Location(2, 4)))
    }

    @Test
    fun testWithinFovLeft() {
        val loc = Day6.Location(5, 5)
        assertEquals(true, loc.withinFovLeft(Day6.Location(4, 5)))
        assertEquals(true, loc.withinFovLeft(Day6.Location(2, 6)))
        assertEquals(true, loc.withinFovLeft(Day6.Location(3, 4)))
        assertEquals(true, loc.withinFovLeft(Day6.Location(4, 4)))
        assertEquals(true, loc.withinFovLeft(Day6.Location(4, 6)))
        assertEquals(false, loc.withinFovLeft(Day6.Location(6, 5)))
        assertEquals(false, loc.withinFovLeft(Day6.Location(5, 6)))
        assertEquals(false, loc.withinFovLeft(Day6.Location(4, 2)))
    }

    @Test
    fun testWithinFovDown() {
        val loc = Day6.Location(5, 5)
        assertEquals(true, loc.withinFovDown(Day6.Location(5, 6)))
        assertEquals(true, loc.withinFovDown(Day6.Location(6, 7)))
        assertEquals(true, loc.withinFovDown(Day6.Location(3, 7)))
        assertEquals(true, loc.withinFovDown(Day6.Location(7, 7)))
        assertEquals(true, loc.withinFovDown(Day6.Location(4, 6)))
        assertEquals(false, loc.withinFovDown(Day6.Location(2, 7)))
        assertEquals(false, loc.withinFovDown(Day6.Location(7, 6)))
        assertEquals(false, loc.withinFovDown(Day6.Location(5, 4)))
    }

    @Test
    fun testWithinFovRight() {
        val loc = Day6.Location(5, 5)
        assertEquals(true, loc.withinFovRight(Day6.Location(6, 5)))
        assertEquals(true, loc.withinFovRight(Day6.Location(7, 6)))
        assertEquals(true, loc.withinFovRight(Day6.Location(7, 4)))
        assertEquals(true, loc.withinFovRight(Day6.Location(6, 4)))
        assertEquals(true, loc.withinFovRight(Day6.Location(6, 6)))
        assertEquals(false, loc.withinFovRight(Day6.Location(6, 3)))
        assertEquals(false, loc.withinFovRight(Day6.Location(6, 7)))
        assertEquals(false, loc.withinFovRight(Day6.Location(4, 5)))
    }

    @Test
    fun testDistanceTo() {
        val loc = Day6.Location(5, 5)
        assertEquals(10, loc.distanceTo(Day6.Location(0, 0)))
        assertEquals(10, loc.distanceTo(Day6.Location(10, 10)))
        assertEquals(4, loc.distanceTo(Day6.Location(4, 8)))
        assertEquals(4, loc.distanceTo(Day6.Location(8, 4)))
    }

    @Test
    fun testPart1Examples() {
        assertEquals(17, Day6(testInput).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals(3620, Day6(resourceAsList("2018/day6.txt")).solvePart1())
    }


    @Test
    fun testPart2Examples() {
        assertEquals(16, Day6(testInput).solvePart2(32))
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(39930, Day6(resourceAsList("2018/day6.txt")).solvePart2(10000))
    }
}




