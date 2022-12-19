package test.aoc2022

import aoc2022.Day16
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day16Test {
    private val exampleInput = """
        Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
        Valve BB has flow rate=13; tunnels lead to valves CC, AA
        Valve CC has flow rate=2; tunnels lead to valves DD, BB
        Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE
        Valve EE has flow rate=3; tunnels lead to valves FF, DD
        Valve FF has flow rate=0; tunnels lead to valves EE, GG
        Valve GG has flow rate=0; tunnels lead to valves FF, HH
        Valve HH has flow rate=22; tunnel leads to valve GG
        Valve II has flow rate=0; tunnels lead to valves AA, JJ
        Valve JJ has flow rate=21; tunnel leads to valve II
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day16 = Day16(exampleInput)
        assertEquals(1651, day16.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day16 = Day16(resourceAsList("2022/day16.txt"))
        assertEquals(1728, day16.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day16 = Day16(exampleInput)
        assertEquals(1707, day16.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day16 = Day16(resourceAsList("2022/day16.txt"))
        assertEquals(0, day16.solvePart2())
    }
}