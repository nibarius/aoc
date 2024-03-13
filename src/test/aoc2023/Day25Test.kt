package test.aoc2023

import aoc2023.Day25
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day25Test {
    private val exampleInput = """
        jqt: rhn xhk nvd
        rsh: frs pzl lsr
        xhk: hfx
        cmg: qnr nvd lhk bvb
        rhn: xhk bvb hfx
        bvb: xhk hfx
        pzl: lsr hfx nvd
        qnr: nvd
        ntq: jqt hfx bvb xhk
        nvd: lhk
        lsr: lhk
        rzs: qnr cmg lsr rsh
        frs: qnr lhk lsr
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day25 = Day25(exampleInput)
        assertEquals(54, day25.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day25 = Day25(resourceAsList("2023/day25.txt"))
        assertEquals(507626, day25.solvePart1())
    }

}