package test.aoc2021

import aoc2021.Day14
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day14Test {
    private val exampleInput = """
        NNCB

        CH -> B
        HH -> N
        CB -> H
        NH -> C
        HB -> C
        HC -> B
        HN -> C
        NN -> C
        BH -> H
        NC -> B
        NB -> B
        BN -> B
        BB -> N
        BC -> B
        CC -> N
        CN -> C
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day14 = Day14(exampleInput)
        assertEquals(1588, day14.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day14 = Day14(resourceAsList("2021/day14.txt"))
        assertEquals(2851, day14.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day14 = Day14(exampleInput)
        assertEquals(2188189693529, day14.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day14 = Day14(resourceAsList("2021/day14.txt"))
        assertEquals(10002813279337, day14.solvePart2())
    }
}