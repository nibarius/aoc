package test.aoc2020

import aoc2020.Day15
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day15Test {
    private val exampleInput = "0,3,6".split(",")
    private val exampleInput2 = "1,3,2".split(",")
    private val exampleInput3 = "2,1,3".split(",")
    private val exampleInput4 = "1,2,3".split(",")
    private val exampleInput5 = "2,3,1".split(",")
    private val exampleInput6 = "3,2,1".split(",")
    private val exampleInput7 = "3,1,2".split(",")

    @Test
    fun testPartOneExample1() {
        val day15 = Day15(exampleInput)
        assertEquals(436, day15.solvePart1())
    }

    @Test
    fun testPartOneExample2() {
        val day15 = Day15(exampleInput2)
        assertEquals(1, day15.solvePart1())
    }

    @Test
    fun testPartOneExample3() {
        val day15 = Day15(exampleInput3)
        assertEquals(10, day15.solvePart1())
    }

    @Test
    fun testPartOneExample4() {
        val day15 = Day15(exampleInput4)
        assertEquals(27, day15.solvePart1())
    }

    @Test
    fun testPartOneExample5() {
        val day15 = Day15(exampleInput5)
        assertEquals(78, day15.solvePart1())
    }

    @Test
    fun testPartOneExample6() {
        val day15 = Day15(exampleInput6)
        assertEquals(438, day15.solvePart1())
    }

    @Test
    fun testPartOneExample7() {
        val day15 = Day15(exampleInput7)
        assertEquals(1836, day15.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day15 = Day15(resourceAsList("2020/day15.txt", ","))
        assertEquals(203, day15.solvePart1())
    }

    @Test
    fun partTwoRealInput() {
        val day15 = Day15(resourceAsList("2020/day15.txt", ","))
        assertEquals(9007186, day15.solvePart2())
    }
}