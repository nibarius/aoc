package test.aoc2021

import aoc2019.Intcode
import aoc2021.Day7
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList
import resourceAsString

class Day7Test {
    private val exampleInput = """16,1,2,0,4,2,7,1,2,14""".trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day7 = Day7(exampleInput)
        assertEquals(37, day7.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day7 = Day7(resourceAsList("2021/day7.txt"))
        assertEquals(357353, day7.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day7 = Day7(exampleInput)
        assertEquals(168, day7.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day7 = Day7(resourceAsList("2021/day7.txt"))
        assertEquals(104822130, day7.solvePart2())
    }

    @Test
    fun easterEgg() {
        // Reference to https://en.wikipedia.org/wiki/The_Treachery_of_Images
        val intcode = Intcode(resourceAsString("2021/day7.txt").split(",").map { it.toLong() })
        intcode.run()
        val asciiOutput = intcode.output.joinToString(""){ it.toInt().toChar().toString() }
        assertEquals("Ceci n'est pas une intcode program\n", asciiOutput )
    }
}