package test.aoc2015

import aoc2015.Day12
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsString

class Day12Test {
    @Test
    fun testPartOneExample1() {
        assertEquals(6, Day12("[1,2,3]").solvePart1())
        assertEquals(6, Day12("{\"a\":2,\"b\":4}").solvePart1())
    }
    @Test
    fun testPartOneExample2() {
        assertEquals(3, Day12("[[[3]]]").solvePart1())
        assertEquals(3, Day12("{\"a\":{\"b\":4},\"c\":-1}").solvePart1())
    }
    @Test
    fun testPartOneExample3() {
        assertEquals(0, Day12("{\"a\":[-1,1]}").solvePart1())
        assertEquals(0, Day12("[-1,{\"a\":1}]").solvePart1())
    }
    @Test
    fun testPartOneExample4() {
        assertEquals(0, Day12("[]").solvePart1())
        assertEquals(0, Day12("{}").solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals(156366, Day12(resourceAsString("2015/day12.txt")).solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        assertEquals(6, Day12("[1,2,3]").solvePart2())
    }

    @Test
    fun testPartTwoExample2() {
        assertEquals(4, Day12("[1,{\"c\":\"red\",\"b\":2},3]").solvePart2())
    }

    @Test
    fun testPartTwoExample3() {
        assertEquals(0, Day12("{\"d\":\"red\",\"e\":[1,2,3,4],\"f\":5}").solvePart2())
    }

    @Test
    fun testPartTwoExample4() {
        assertEquals(6, Day12("[1,\"red\",5]").solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(96852, Day12(resourceAsString("2015/day12.txt")).solvePart2())
    }
}