package test.aoc2024

import aoc2024.Day23
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day23Test {
    private val exampleInput = """
        kh-tc
        qp-kh
        de-cg
        ka-co
        yn-aq
        qp-ub
        cg-tb
        vc-aq
        tb-ka
        wh-tc
        yn-cg
        kh-ub
        ta-co
        de-co
        tc-td
        tb-wq
        wh-td
        ta-ka
        td-qp
        aq-cg
        wq-ub
        ub-vc
        de-ta
        wq-aq
        wq-vc
        wh-yn
        ka-de
        kh-ta
        co-tc
        wh-qp
        tb-vc
        td-yn
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day23 = Day23(exampleInput)
        assertEquals(7, day23.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day23 = Day23(resourceAsList("2024/day23.txt"))
        assertEquals(1327, day23.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        /*val exampleInput = """
        co-ta
        co-ka
        co-de
        ta-ka
        ta-de
        ka-de
        ta-kh
        co-tc
        ka-tb
        de-cg
    """.trimIndent().split("\n")*/
        val day23 = Day23(exampleInput)
        assertEquals("co,de,ka,ta", day23.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day23 = Day23(resourceAsList("2024/day23.txt"))
        assertEquals("df,kg,la,mp,pb,qh,sk,th,vn,ww,xp,yp,zk", day23.solvePart2())
    }
}