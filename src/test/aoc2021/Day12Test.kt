package test.aoc2021

import aoc2021.Day12
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day12Test {
    private val exampleInput = """
        start-A
        start-b
        A-c
        A-b
        b-d
        A-end
        b-end
    """.trimIndent().split("\n")

    private val exampleInput2 = """
            dc-end
            HN-start
            start-kj
            dc-start
            dc-HN
            LN-dc
            HN-end
            kj-sa
            kj-HN
            kj-dc
        """.trimIndent().split("\n")

    private val exampleInput3 = """
            fs-end
            he-DX
            fs-he
            start-DX
            pj-DX
            end-zg
            zg-sl
            zg-pj
            pj-he
            RW-he
            fs-DX
            pj-RW
            zg-RW
            start-pj
            he-WI
            zg-he
            pj-fs
            start-RW
        """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day12 = Day12(exampleInput)
        assertEquals(10, day12.solvePart1())
    }

    @Test
    fun testPartOneExample2() {
        val day12 = Day12(exampleInput2)
        assertEquals(19, day12.solvePart1())
    }

    @Test
    fun testPartOneExample3() {
        val day12 = Day12(exampleInput3)
        assertEquals(226, day12.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day12 = Day12(resourceAsList("2021/day12.txt"))
        assertEquals(3497, day12.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day12 = Day12(exampleInput)
        assertEquals(36, day12.solvePart2())
    }

    @Test
    fun testPartTwoExample2() {
        val day12 = Day12(exampleInput2)
        assertEquals(103, day12.solvePart2())
    }

    @Test
    fun testPartTwoExample3() {
        val day12 = Day12(exampleInput3)
        assertEquals(3509, day12.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day12 = Day12(resourceAsList("2021/day12.txt"))
        assertEquals(93686, day12.solvePart2())
    }
}