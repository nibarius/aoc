package test.aoc2023

import aoc2023.Day23
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day23Test {
    private val exampleInput = """
        #.#####################
        #.......#########...###
        #######.#########.#.###
        ###.....#.>.>.###.#.###
        ###v#####.#v#.###.#.###
        ###.>...#.#.#.....#...#
        ###v###.#.#.#########.#
        ###...#.#.#.......#...#
        #####.#.#.#######.#.###
        #.....#.#.#.......#...#
        #.#####.#.#.#########v#
        #.#...#...#...###...>.#
        #.#.#v#######v###.###v#
        #...#.>.#...>.>.#.###.#
        #####v#.#.###v#.#.###.#
        #.....#...#...#.#.#...#
        #.#########.###.#.#.###
        #...###...#...#...#.###
        ###.###.#.###v#####v###
        #...#...#.#.>.>.#.>.###
        #.###.###.#.###.#.#v###
        #.....###...###...#...#
        #####################.#
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day23 = Day23(exampleInput)
        assertEquals(94, day23.solvePart1())
    }

    @Test
    fun testPartOneSmallExample() {
        val input = """
            #.#####
            #.#####
            #v#####
            #.>.>.#
            #v#^#.#
            #.#.#.#
            #...#.#
            #####.#
        """.trimIndent().split("\n")
        val day23 = Day23(input)
        assertEquals(17, day23.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day23 = Day23(resourceAsList("2023/day23.txt"))
        assertEquals(2162, day23.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day23 = Day23(exampleInput)
        assertEquals(154, day23.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day23 = Day23(resourceAsList("2023/day23.txt"))
        assertEquals(6334, day23.solvePart2())
    }
}