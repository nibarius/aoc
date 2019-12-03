package test.aoc2019

import aoc2019.Day3
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day3Test {
    @Test
    fun testPart1Examples() {
        assertEquals(6, Day3(listOf("R8,U5,L5,D3", "U7,R6,D4,L4")).solvePart1())
        assertEquals(159, Day3(listOf("R75,D30,R83,U83,L12,D49,R71,U7,L72", "U62,R66,U55,R34,D71,R55,D58,R83")).solvePart1())
        assertEquals(135, Day3(listOf("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51", "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7")).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals(651, Day3(resourceAsList("2019/day3.txt")).solvePart1())
    }

    @Test
    fun testPart2Examples() {
        assertEquals(30, Day3(listOf("R8,U5,L5,D3", "U7,R6,D4,L4")).solvePart2())
        assertEquals(610, Day3(listOf("R75,D30,R83,U83,L12,D49,R71,U7,L72", "U62,R66,U55,R34,D71,R55,D58,R83")).solvePart2())
        assertEquals(410, Day3(listOf("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51", "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7")).solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(7534, Day3(resourceAsList("2019/day3.txt")).solvePart2())
    }
}




