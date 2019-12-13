package test.aoc2019

import aoc2019.Day12
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day12Test {

    @Test
    fun testPartOneExample1() {
        val input = """
            <x=-1, y=0, z=2>
            <x=2, y=-10, z=-7>
            <x=4, y=-8, z=8>
            <x=3, y=5, z=-1>
        """.trimIndent().split("\n")

        assertEquals(179, Day12(input).solvePart1(10))
    }

    @Test
    fun testPartOneExample2() {
        val input = """
            <x=-8, y=-10, z=0>
            <x=5, y=5, z=10>
            <x=2, y=-7, z=3>
            <x=9, y=-8, z=-3>
        """.trimIndent().split("\n")

        assertEquals(1940, Day12(input).solvePart1(100))
    }

    @Test
    fun partOneRealInput() {
        assertEquals(12644, Day12(resourceAsList("2019/day12.txt")).solvePart1(1000))
    }

    @Test
    fun testPartTwoExample1() {
        val input = """
            <x=-1, y=0, z=2>
            <x=2, y=-10, z=-7>
            <x=4, y=-8, z=8>
            <x=3, y=5, z=-1>
        """.trimIndent().split("\n")

        assertEquals(2772, Day12(input).solvePart2())
    }

    @Test
    fun testPartTwoExample2() {
        val input = """
            <x=-8, y=-10, z=0>
            <x=5, y=5, z=10>
            <x=2, y=-7, z=3>
            <x=9, y=-8, z=-3>
        """.trimIndent().split("\n")

        assertEquals(4686774924, Day12(input).solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(290314621566528, Day12(resourceAsList("2019/day12.txt")).solvePart2())
    }
}