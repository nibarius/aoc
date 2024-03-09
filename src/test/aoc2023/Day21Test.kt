package test.aoc2023

import aoc2023.Day21
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day21Test {
    private val exampleInput = """
        ...........
        .....###.#.
        .###.##..#.
        ..#.#...#..
        ....#.#....
        .##..S####.
        .##..#...#.
        .......##..
        .##.#.####.
        .##..##.##.
        ...........
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day21 = Day21(exampleInput)
        assertEquals(16, day21.solvePart1(6))
    }

    @Test
    fun partOneRealInput() {
        val day21 = Day21(resourceAsList("2023/day21.txt"))
        assertEquals(3716, day21.solvePart1())
    }

    /*@Test
    fun testPartTwoExample1() {
        val day21 = Day21(exampleInput)
        assertEquals(16, day21.solvePart2(6))
    }

    @Test
    fun testPartTwoExample2() {
        val day21 = Day21(exampleInput)
        assertEquals(50, day21.solvePart2(10))
    }*/

    @Test
    fun testPartTwoExample3() {
        val day21 = Day21(exampleInput)
        assertEquals(1594, day21.solvePart2(50))
    }

    @Test
    fun testPartTwoExample4() {
        val day21 = Day21(exampleInput)
        assertEquals(6536, day21.solvePart2(100))
    }

    @Test
    fun testPartTwoExample42() {
        val day21 = Day21(exampleInput)
        assertEquals(6684, day21.solvePart2(101))
    }

    @Test
    fun testPartTwoExample5() {
        val day21 = Day21(exampleInput)
        assertEquals(668697, day21.solvePart2(1000))
    }

    @Test
    fun testPartTwoExample6() {
        val day21 = Day21(exampleInput)
        assertEquals(16733044, day21.solvePart2(5000))
    }

    @Test
    fun testPartTwoRealInputTest1() {
        val day21 = Day21(resourceAsList("2023/day21.txt"))
        assertEquals(318259, day21.solvePart2(601))
    }

    @Test
    fun partTwoRealInput() {
        val day21 = Day21(resourceAsList("2023/day21.txt"))
        assertEquals(616583483179597, day21.solvePart2())
    }

    private val reddit1 = """
        .............
        ..#......###.
        .##.......##.
        .......#...#.
        ....#........
        ....#...##...
        ......S......
        ........#....
        ....##.##....
        .#...#.....#.
        .##..........
        .#.......#...
        .............
    """.trimIndent().split("\n")

    @Test
    fun partTwoReddit1() {
        // from https://www.reddit.com/r/adventofcode/comments/18orpvg/2023_day_21_the_puzzle_input_had_some_features_to/
        val day21 = Day21(reddit1)
        assertEquals(8665, day21.solvePart2(100))
    }
    @Test
    fun partTwoReddit2() {
        // The above-mentioned reddit post gives larger answers for the test input
        val day21 = Day21(exampleInput)
        assertEquals(16735534173504  , day21.solvePart2(5000000  ))
    }

}