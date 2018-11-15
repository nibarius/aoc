package test.aoc2016

import aoc2016.Assembunny
import aoc2016.Day23
import org.junit.Assert
import org.junit.Test

class Day23Test {

    private val exampleInput = listOf(
            "cpy 2 a",
            "tgl a",
            "tgl a",
            "tgl a",
            "cpy 1 a",
            "dec a",
            "dec a"
    )

    @Test
    fun partOneExampleInput() {
        val bunny = Assembunny(exampleInput.toMutableList())
        bunny.execute()
        Assert.assertEquals(3, bunny.registers["a"]!!)
    }

    @Test
    fun partOneRealInput() {
        Assert.assertEquals(14065, Day23(resourceAsList("2016/day23.txt").toMutableList()).solvePart1())
    }

    @Test
    fun partTwoRealInput() {
        Assert.assertEquals(-1, Day23(resourceAsList("2016/day23.txt")).solvePart2())
    }
}