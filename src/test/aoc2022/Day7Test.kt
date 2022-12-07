package test.aoc2022

import aoc2022.Day7
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day7Test {
    private val exampleInput = """
        ${'$'} cd /
        ${'$'} ls
        dir a
        14848514 b.txt
        8504156 c.dat
        dir d
        ${'$'} cd a
        ${'$'} ls
        dir e
        29116 f
        2557 g
        62596 h.lst
        ${'$'} cd e
        ${'$'} ls
        584 i
        ${'$'} cd ..
        ${'$'} cd ..
        ${'$'} cd d
        ${'$'} ls
        4060174 j
        8033020 d.log
        5626152 d.ext
        7214296 k
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day7 = Day7(exampleInput)
        assertEquals(95437, day7.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day7 = Day7(resourceAsList("2022/day7.txt"))
        assertEquals(1367870, day7.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day7 = Day7(exampleInput)
        assertEquals(24933642, day7.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day7 = Day7(resourceAsList("2022/day7.txt"))
        assertEquals(549173, day7.solvePart2())
    }
}