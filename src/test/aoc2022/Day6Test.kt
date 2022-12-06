package test.aoc2022

import aoc2022.Day6
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day6Test {
    private val exampleInput = """mjqjpqmgbljsphdztnvjfqwrcgsmlb""".trimIndent().split("\n")
    private val exampleInput2 = """bvwbjplbgvbhsrlpgdmjqwftvncz""".trimIndent().split("\n")
    private val exampleInput3 = """nppdvjthqldpwncqszvftbrmjlhg""".trimIndent().split("\n")
    private val exampleInput4 = """nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg""".trimIndent().split("\n")
    private val exampleInput5 = """zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw""".trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day6 = Day6(exampleInput)
        assertEquals(7, day6.solvePart1())
    }

    @Test
    fun testPartOneExample2() {
        val day6 = Day6(exampleInput2)
        assertEquals(5, day6.solvePart1())
    }

    @Test
    fun testPartOneExample3() {
        val day6 = Day6(exampleInput3)
        assertEquals(6, day6.solvePart1())
    }

    @Test
    fun testPartOneExample4() {
        val day6 = Day6(exampleInput4)
        assertEquals(10, day6.solvePart1())
    }

    @Test
    fun testPartOneExample5() {
        val day6 = Day6(exampleInput5)
        assertEquals(11, day6.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day6 = Day6(resourceAsList("2022/day6.txt"))
        assertEquals(1655, day6.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day6 = Day6(exampleInput)
        assertEquals(19, day6.solvePart2())
    }

    @Test
    fun testPartTwoExample2() {
        val day6 = Day6(exampleInput2)
        assertEquals(23, day6.solvePart2())
    }

    @Test
    fun testPartTwoExample3() {
        val day6 = Day6(exampleInput3)
        assertEquals(23, day6.solvePart2())
    }

    @Test
    fun testPartTwoExample4() {
        val day6 = Day6(exampleInput4)
        assertEquals(29, day6.solvePart2())
    }

    @Test
    fun testPartTwoExample5() {
        val day6 = Day6(exampleInput5)
        assertEquals(26, day6.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day6 = Day6(resourceAsList("2022/day6.txt"))
        assertEquals(2665, day6.solvePart2())
    }
}