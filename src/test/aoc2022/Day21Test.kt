package test.aoc2022

import aoc2022.Day21
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day21Test {
    private val exampleInput = """
        root: pppw + sjmn
        dbpl: 5
        cczh: sllz + lgvd
        zczc: 2
        ptdq: humn - dvpt
        dvpt: 3
        lfqf: 4
        humn: 5
        ljgn: 2
        sjmn: drzm * dbpl
        sllz: 4
        pppw: cczh / lfqf
        lgvd: ljgn * ptdq
        drzm: hmdt - zczc
        hmdt: 32
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day21 = Day21(exampleInput)
        assertEquals(152, day21.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day21 = Day21(resourceAsList("2022/day21.txt"))
        assertEquals(110181395003396, day21.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day21 = Day21(exampleInput)
        assertEquals(301, day21.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day21 = Day21(resourceAsList("2022/day21.txt"))
        assertEquals(3721298272959, day21.solvePart2())
    }
}