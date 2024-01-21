package test.aoc2023

import aoc2023.Day22
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day22Test {
    private val exampleInput = """
        1,0,1~1,2,1
        0,0,2~2,0,2
        0,2,3~2,2,3
        0,0,4~0,2,4
        2,0,5~2,2,5
        0,1,6~2,1,6
        1,1,8~1,1,9
    """.trimIndent().split("\n")

    private val extraInput = """
            0,0,1~0,5,1
            0,6,1~0,9,1
            0,0,2~0,0,2
            0,3,2~0,8,2
        """.trimIndent().split("\n")

    private val extraInput2 = """
            0,0,1~0,5,1
            0,6,1~0,9,1
            0,0,2~0,0,2
            0,2,2~0,2,2
            0,3,2~0,8,2
            0,0,3~0,2,3
        """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day22 = Day22(exampleInput)
        assertEquals(5, day22.solvePart1())
    }

    @Test
    fun testPartOneExtraExample() {
        val day22 = Day22(extraInput)
        assertEquals(3, day22.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day22 = Day22(resourceAsList("2023/day22.txt"))
        assertEquals(490, day22.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day22 = Day22(exampleInput)
        assertEquals(7, day22.solvePart2())
    }

    @Test
    fun testPartTwoExtraExample() {
        val day22 = Day22(extraInput)
        assertEquals(1, day22.solvePart2())
    }

    @Test
    fun testPartTwoExtraExample2() {
        val day22 = Day22(extraInput2)
        assertEquals(3, day22.solvePart2())
    }

    @Test
    fun testPartTwoExtraExample3() {
        val input = """
            0,0,1~0,5,1
            0,6,1~0,9,1
            0,0,2~0,0,2
            0,2,2~0,2,2
            0,3,2~0,8,2
            0,0,3~0,1,3
        """.trimIndent().split("\n")
        val day22 = Day22(input)
        assertEquals(4, day22.solvePart2())
    }

    @Test
    fun testPartTwoExtraExample4() {
        val input = """
            0,0,1~0,5,1
            0,0,2~0,0,2
            0,2,2~0,2,2
            0,0,3~0,0,3
            0,2,3~0,2,3
            0,0,4~0,2,4
        """.trimIndent().split("\n")
        val day22 = Day22(input)
        assertEquals(7, day22.solvePart2())
    }

    @Test
    fun testPartTwoExtraExample5() {
        val input = """
            0,0,1~0,2,1
            0,0,2~0,0,2
            0,2,2~0,2,3
            0,0,3~0,0,3
            0,0,4~0,2,4
        """.trimIndent().split("\n")
        val day22 = Day22(input)
        assertEquals(5, day22.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day22 = Day22(resourceAsList("2023/day22.txt"))
        assertEquals(96356, day22.solvePart2())
    }
}