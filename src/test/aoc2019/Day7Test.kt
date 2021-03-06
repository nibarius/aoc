package test.aoc2019

import aoc2019.Day7
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day7Test {
    @Test
    fun testPermutationsOf() {
        val d7 = Day7(listOf())
        assertEquals(listOf(listOf(0L, 1L), listOf(1L, 0L)), d7.allPermutationsOf(0..1))
        d7.allPermutationsOf(0..5)
                .also { perm -> assertEquals(true, perm.all { it.size == 6 }) }
                .also { perm -> assertEquals(true, perm.all { it.toSet().size == 6 }) }
    }

    @Test
    fun testPart1Example1() {
        val input = "3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0".split(",")
        assertEquals(43210, Day7(input).solvePart1())
    }

    @Test
    fun testPart1Example2() {
        val input = "3,23,3,24,1002,24,10,24,1002,23,-1,23,101,5,23,23,1,24,23,23,4,23,99,0,0".split(",")
        assertEquals(54321, Day7(input).solvePart1())
    }

    @Test
    fun testPart1Example3() {
        val input = "3,31,3,32,1002,32,10,32,1001,31,-2,31,1007,31,0,33,1002,33,7,33,1,33,31,31,1,32,31,31,4,31,99,0,0,0".split(",")
        assertEquals(65210, Day7(input).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals(95757, Day7(resourceAsList("2019/day7.txt", ",")).solvePart1())
    }

    @Test
    fun testPart2Example1() {
        val input = "3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5".split(",")
        assertEquals(139629729, Day7(input).solvePart2())
    }

    @Test
    fun testPart2Example2() {
        val input = "3,52,1001,52,-5,52,3,53,1,52,56,54,1007,54,5,55,1005,55,26,1001,54,-5,54,1105,1,12,1,53,54,53,1008,54,0,55,1001,55,1,55,2,53,55,53,4,53,1001,56,-1,56,1005,56,6,99,0,0,0,0,10".split(",")
        assertEquals(18216, Day7(input).solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(4275738, Day7(resourceAsList("2019/day7.txt", ",")).solvePart2())
    }
}




