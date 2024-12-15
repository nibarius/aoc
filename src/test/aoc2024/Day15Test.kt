package test.aoc2024

import aoc2024.Day15
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceSplitOnBlankLines

class Day15Test {
    private val exampleInput = """
        ########
        #..O.O.#
        ##@.O..#
        #...O..#
        #.#.O..#
        #...O..#
        #......#
        ########

        <^^>>>vv<v>>v<<
    """.trimIndent().split("\n\n")

    private val exampleInput2 = """
        ##########
        #..O..O.O#
        #......O.#
        #.OO..O.O#
        #..O@..O.#
        #O#..O...#
        #O..O..O.#
        #.OO.O.OO#
        #....O...#
        ##########

        <vv>^<v^>v>^vv^v>v<>v^v<v<^vv<<<^><<><>>v<vvv<>^v^>^<<<><<v<<<v^vv^v>^
        vvv<<^>^v^^><<>>><>^<<><^vv^^<>vvv<>><^^v>^>vv<>v<<<<v<^v>^<^^>>>^<v<v
        ><>vv>v^v^<>><>>>><^^>vv>v<^^^>>v^v^<^^>v^^>v^<^v>v<>>v^v^<v>v^^<^^vv<
        <<v<^>>^^^^>>>v^<>vvv^><v<<<>^^^vv^<vvv>^>v<^^^^v<>^>vvvv><>>v^<<^^^^^
        ^><^><>>><>^^<<^^v>>><^<v>^<vv>>v>>>^v><>^v><<<<v>>v<v<v>vvv>^<><<>^><
        ^>><>^v<><^vvv<^^<><v<<<<<><^v<<<><<<^^<v<^^^><^>>^<v^><<<^>>^v<v^v<v^
        >^>>^v>vv>^<<^v<>><<><<v<<v><>v<^vv<<<>^^v^>^^>>><<^v>>v^v><^^>>^<>vv^
        <><^^>^^^<><vvvvv^v<v<<>^v<v>v<<^><<><<><<<^^<<<^<<>><<><^^^>^^<>^>v<>
        ^^>vv<^v^v<vv>^<><v<^v>^^^>>>^^vvv^>vvv<>>>^<^>>>>>^<<^v>^vvv<>^<><<v>
        v^^>>><<^^<>>^v^<v^vv<>v^<<>^<^v^v><^<<<><<^<v><v<>vv>>v><v^<vv<>v^<<^
    """.trimIndent().split("\n\n")

    @Test
    fun testPartOneExample1() {
        val day15 = Day15(exampleInput)
        assertEquals(2028, day15.solvePart1())
    }

    @Test
    fun testPartOneExample2() {
        val day15 = Day15(exampleInput2)
        assertEquals(10092, day15.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day15 = Day15(resourceSplitOnBlankLines("2024/day15.txt"))
        assertEquals(1430536, day15.solvePart1())
    }

    @Test
    fun testPartTwoExample2() {
        val day15 = Day15(exampleInput2)
        assertEquals(9021, day15.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day15 = Day15(resourceSplitOnBlankLines("2024/day15.txt"))
        assertEquals(1452348, day15.solvePart2())
    }
}