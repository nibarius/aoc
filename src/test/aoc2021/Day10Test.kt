package test.aoc2021

import aoc2021.Day10
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day10Test {
    private val exampleInput = """
        [({(<(())[]>[[{[]{<()<>>
        [(()[<>])]({[<{<<[]>>(
        {([(<{}[<>[]}>{[]{[(<()>
        (((({<>}<{<{<>}{[]{[]{}
        [[<[([]))<([[{}[[()]]]
        [{[{({}]{}}([{[{{{}}([]
        {<[[]]>}<{[{[{[]{()[[[]
        [<(<(<(<{}))><([]([]()
        <{([([[(<>()){}]>(<<{{
        <{([{{}}[<[[[<>{}]]]>[]]
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day10 = Day10(exampleInput)
        assertEquals(26397, day10.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day10 = Day10(resourceAsList("2021/day10.txt"))
        assertEquals(415953, day10.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day10 = Day10(exampleInput)
        assertEquals(288957, day10.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day10 = Day10(resourceAsList("2021/day10.txt"))
        assertEquals(2292863731, day10.solvePart2())
    }
}