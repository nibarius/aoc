package test.aoc2025

import aoc2025.Day11
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day11Test {
    private val exampleInput = """
        aaa: you hhh
        you: bbb ccc
        bbb: ddd eee
        ccc: ddd eee fff
        ddd: ggg
        eee: out
        fff: out
        ggg: out
        hhh: ccc fff iii
        iii: out
    """.trimIndent().split("\n")

    private val exampleInput2 = """
        svr: aaa bbb
        aaa: fft
        fft: ccc
        bbb: tty
        tty: ccc
        ccc: ddd eee
        ddd: hub
        hub: fff
        eee: dac
        dac: fff
        fff: ggg hhh
        ggg: out
        hhh: out
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day11 = Day11(exampleInput)
        assertEquals(5, day11.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day11 = Day11(resourceAsList("2025/day11.txt"))
        assertEquals(699, day11.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day11 = Day11(exampleInput2)
        assertEquals(2, day11.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day11 = Day11(resourceAsList("2025/day11.txt"))
        assertEquals(388893655378800, day11.solvePart2())
    }
}