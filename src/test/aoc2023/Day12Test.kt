package test.aoc2023

import aoc2023.Day12
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day12Test {
    private val exampleInput = """
        ???.### 1,1,3
        .??..??...?##. 1,1,3
        ?#?#?#?#?#?#?#? 1,3,1,6
        ????.#...#... 4,1,1
        ????.######..#####. 1,6,5
        ?###???????? 3,2,1
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day12 = Day12(exampleInput)
        assertEquals(21, day12.solvePart1())
    }

    @Test
    fun extraTest() {
        val day12 = Day12(listOf(".#.???#.?..#?##??? 1,1,2,1,7"))
        assertEquals(1, day12.solvePart1())
    }

    @Test
    fun extraTest2() {
        val day12 = Day12(listOf("??????#????...#?... 9,1"))
        assertEquals(3, day12.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day12 = Day12(resourceAsList("2023/day12.txt"))
        assertEquals(7407, day12.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day12 = Day12(exampleInput)
        assertEquals(525152, day12.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day12 = Day12(resourceAsList("2023/day12.txt"))
        assertEquals(30568243604962, day12.solvePart2())
    }
}