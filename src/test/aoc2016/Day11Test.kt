package test.aoc2016

import aoc2016.Day11
import org.junit.Assert
import org.junit.Test
import resourceAsList

class Day11Test {

    private val exampleInput = listOf("The first floor contains a hydrogen-compatible microchip and a lithium-compatible microchip.",
            "The second floor contains a hydrogen generator.",
            "The third floor contains a lithium generator.",
            "The fourth floor contains nothing relevant.")

    @Test
    fun partOneExample() {
        Assert.assertEquals(11, Day11(exampleInput).solvePart1())
    }

    @Test
    fun testEquivalentStateEqual(){
        val a = Day11.State(4, listOf(1,2,1,2,1,5), 5)
        val b = Day11.State(4, listOf(1,5,1,2,1,2), 15)
        Assert.assertEquals(a.getEquivalentState(), b.getEquivalentState())
    }

    @Test
    fun testEquivalentStateNotEqual(){
        val a = Day11.State(4, listOf(1,2,1,2,1,5), 5)
        val b = Day11.State(4, listOf(1,5,1,2,1,2,1,2), 5)
        Assert.assertNotEquals(a.getEquivalentState(), b.getEquivalentState())
    }

    @Test
    fun partOneRealInput() {
        Assert.assertEquals(47, Day11(resourceAsList("2016/day11.txt")).solvePart1())
    }

    @Test
    fun partTwoRealInput() {
        Assert.assertEquals(71, Day11(resourceAsList("2016/day11.txt")).solvePart2())
    }
}