package test.aoc2018

import aoc2018.Day20
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsString

class Day20Test {

    @Test
    fun testPart1Example1() {
        assertEquals(3, Day20("^WNE\$").solvePart1())
    }

    @Test
    fun testPart1Example2() {
        assertEquals(10, Day20("^ENWWW(NEEE|SSE(EE|N))\$").solvePart1())
    }

    @Test
    fun testPart1Example3() {
        assertEquals(18, Day20("^ENNWSWW(NEWS|)SSSEEN(WNSE|)EE(SWEN|)NNN\$").solvePart1())
    }

    @Test
    fun testPart1Example4() {
        assertEquals(23, Day20("^ESSWWN(E|NNENN(EESS(WNSE|)SSS|WWWSSSSE(SW|NNNE)))\$").solvePart1())
    }

    @Test
    fun testPart1Example4Simplified() {
        assertEquals(17, Day20("^NNENN(EESS(WNSE|)SSS|WWWSSSSE(SW|NNNE))\$").solvePart1())
    }


    @Test
    fun testPart1Example5() {
        assertEquals(31, Day20("^WSSEESWWWNW(S|NENNEEEENN(ESSSSW(NWSW|SSEN)|WSWWN(E|WWS(E|SS))))\$").solvePart1())
    }

    @Test
    fun partOneRealInput() {
        // 3312 too low
        assertEquals(3314, Day20(resourceAsString("2018/day20.txt")).solvePart1())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(8550, Day20(resourceAsString("2018/day20.txt")).solvePart2())
    }
}




