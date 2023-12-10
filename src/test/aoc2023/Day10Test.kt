package test.aoc2023

import aoc2023.Day10
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day10Test {

    @Test
    fun testPartOneExample1() {
        val exampleInput = """
        .....
        .S-7.
        .|.|.
        .L-J.
        .....
    """.trimIndent().split("\n")
        val day10 = Day10(exampleInput)
        assertEquals(4, day10.solvePart1())
    }
    @Test
    fun testPartOneExample2() {
        val exampleInput = """
        ..F7.
        .FJ|.
        SJ.L7
        |F--J
        LJ...
    """.trimIndent().split("\n")
        val day10 = Day10(exampleInput)
        assertEquals(8, day10.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day10 = Day10(resourceAsList("2023/day10.txt"))
        assertEquals(6856, day10.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val exampleInput = """
        ...........
        .S-------7.
        .|F-----7|.
        .||.....||.
        .||.....||.
        .|L-7.F-J|.
        .|..|.|..|.
        .L--J.L--J.
        ...........
    """.trimIndent().split("\n")
        val day10 = Day10(exampleInput)
        assertEquals(4, day10.solvePart2())
    }

    @Test
    fun testPartTwoExample2() {
        val exampleInput = """
        .F----7F7F7F7F-7....
        .|F--7||||||||FJ....
        .||.FJ||||||||L7....
        FJL7L7LJLJ||LJ.L-7..
        L--J.L7...LJS7F-7L7.
        ....F-J..F7FJ|L7L7L7
        ....L7.F7||L7|.L7L7|
        .....|FJLJ|FJ|F7|.LJ
        ....FJL-7.||.||||...
        ....L---J.LJ.LJLJ...
    """.trimIndent().split("\n")
        val day10 = Day10(exampleInput)
        assertEquals(8, day10.solvePart2())
    }

    @Test
    fun testPartTwoExample3() {
        val exampleInput = """
        FF7FSF7F7F7F7F7F---7
        L|LJ||||||||||||F--J
        FL-7LJLJ||||||LJL-77
        F--JF--7||LJLJ7F7FJ-
        L---JF-JLJ.||-FJLJJ7
        |F|F-JF---7F7-L7L|7|
        |FFJF7L7F-JF7|JL---7
        7-L-JL7||F7|L7F-7F7|
        L.L7LFJ|||||FJL7||LJ
        L7JLJL-JLJLJL--JLJ.L
    """.trimIndent().split("\n")
        val day10 = Day10(exampleInput)
        assertEquals(10, day10.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day10 = Day10(resourceAsList("2023/day10.txt"))
        assertEquals(501, day10.solvePart2())
    }
}