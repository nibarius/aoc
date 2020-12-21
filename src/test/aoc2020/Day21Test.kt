package test.aoc2020

import aoc2020.Day21
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day21Test {
    private val exampleInput = """
        mxmxvkd kfcds sqjhc nhms (contains dairy, fish)
        trh fvjkl sbzzf mxmxvkd (contains dairy)
        sqjhc fvjkl (contains soy)
        sqjhc mxmxvkd sbzzf (contains fish)
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day21 = Day21(exampleInput)
        assertEquals(5, day21.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day21 = Day21(resourceAsList("2020/day21.txt"))
        assertEquals(2779, day21.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day21 = Day21(exampleInput)
        assertEquals("mxmxvkd,sqjhc,fvjkl", day21.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day21 = Day21(resourceAsList("2020/day21.txt"))
        assertEquals("lkv,lfcppl,jhsrjlj,jrhvk,zkls,qjltjd,xslr,rfpbpn", day21.solvePart2())
    }
}