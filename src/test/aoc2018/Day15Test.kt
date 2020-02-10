package test.aoc2018

import Pos
import aoc2018.Day15
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day15Test {


    @Test
    fun testMoveTurn() {
        val input = listOf(
                "#######",
                "#E..G.#",
                "#...#.#",
                "#.G.#G#",
                "#######"
        )
        val d = Day15(input)
        val player = Day15.Player(Day15.PlayerType.ELF, Pos(1, 1))
        d.takeTurn(player)
        assertEquals(Pos(2, 1), player.pos)
    }

    @Test
    fun testMoveTurn2() {
        val input = listOf(
                "#######",
                "#.E...#",
                "#.....#",
                "#...G.#",
                "#######"
        )
        val d = Day15(input)
        val player = Day15.Player(Day15.PlayerType.ELF, Pos(2, 1))
        d.takeTurn(player)
        assertEquals(Pos(3, 1), player.pos)
    }

    @Test
    fun testMoveReddit1() {
        val input = listOf(
                "#######",
                "#######",
                "#.E..G#",
                "#.#####",
                "#G#####",
                "#######",
                "#######"
        )
        val d = Day15(input)
        val player = Day15.Player(Day15.PlayerType.ELF, Pos(2, 2))
        d.takeTurn(player)
        assertEquals(Pos(3, 2), player.pos)
    }

    @Test
    fun testMoveReddit2() {
        val input = listOf(
                "####",
                "#GG#",
                "#.E#",
                "####"
        )
        val d = Day15(input)
        val player = Day15.Player(Day15.PlayerType.ELF, Pos(2, 2))
        d.takeTurn(player)
        assertEquals(Pos(2, 2), player.pos)
        assertEquals(197, d.playerAt(Pos(2, 1))?.hp)
    }

    @Test
    fun testMoveReddit2OneRound() {
        val input = listOf(
                "####",
                "#GG#",
                "#.E#",
                "####"
        )
        val d = Day15(input)
        val player = Day15.Player(Day15.PlayerType.ELF, Pos(2, 2))
        d.doRound()
        assertEquals(Pos(2, 2), player.pos)
        assertEquals(197, d.playerAt(Pos(2, 1))?.hp)
        assertEquals(200, d.playerAt(Pos(1, 2))?.hp)
        assertEquals(194, d.playerAt(Pos(2, 2))?.hp)
    }

    @Test
    fun testMoveReddit3() {
        val input = listOf(
                "########",
                "#..E..G#",
                "#G######",
                "########"
        )
        val d = Day15(input)
        val player = Day15.Player(Day15.PlayerType.ELF, Pos(3, 1))
        d.takeTurn(player)
        assertEquals(Pos(2, 1), player.pos)
    }

    @Test
    fun testMoveRound() {
        val initial = listOf(
                "#########",
                "#G..G..G#",
                "#.......#",
                "#.......#",
                "#G..E..G#",
                "#.......#",
                "#.......#",
                "#G..G..G#",
                "#########"
        )
        val after1 = listOf(
                "#########",
                "#.G...G.#",
                "#...G...#",
                "#...E..G#",
                "#.G.....#",
                "#.......#",
                "#G..G..G#",
                "#.......#",
                "#########"
        )
        val after2 = listOf(
                "#########",
                "#..G.G..#",
                "#...G...#",
                "#.G.E.G.#",
                "#.......#",
                "#G..G..G#",
                "#.......#",
                "#.......#",
                "#########"
        )
        val after3 = listOf(
                "#########",
                "#.......#",
                "#..GGG..#",
                "#..GEG..#",
                "#G..G...#",
                "#......G#",
                "#.......#",
                "#.......#",
                "#########")
        val d = Day15(initial)
        d.doRound()
        assertEquals(after1, d.printableMap())
        d.doRound()
        assertEquals(after2, d.printableMap())
        d.doRound()
        assertEquals(after3, d.printableMap())
    }

    @Test
    fun testAttackRound() {
        val input = listOf(
                "#######",
                "#.G...#",
                "#...EG#",
                "#.#.#G#",
                "#..G#E#",
                "#.....#",
                "#######"
        )
        val initial = listOf(
                "#######   ",
                "#.G...#   G(200)",
                "#...EG#   E(200), G(200)",
                "#.#.#G#   G(200)",
                "#..G#E#   G(200), E(200)",
                "#.....#   ",
                "#######   "

        )
        val after1 = listOf(
                "#######   ",
                "#..G..#   G(200)",
                "#...EG#   E(197), G(197)",
                "#.#G#G#   G(200), G(197)",
                "#...#E#   E(197)",
                "#.....#   ",
                "#######   "
        )
        val after2 = listOf(
                "#######   ",
                "#...G.#   G(200)",
                "#..GEG#   G(200), E(188), G(194)",
                "#.#.#G#   G(194)",
                "#...#E#   E(194)",
                "#.....#   ",
                "#######   "
        )
        val after23 = listOf(
                "#######   ",
                "#...G.#   G(200)",
                "#..G.G#   G(200), G(131)",
                "#.#.#G#   G(131)",
                "#...#E#   E(131)",
                "#.....#   ",
                "#######   "
        )
        val after24 = listOf(
                "#######   ",
                "#..G..#   G(200)",
                "#...G.#   G(131)",
                "#.#G#G#   G(200), G(128)",
                "#...#E#   E(128)",
                "#.....#   ",
                "#######   "
                )
        val after26 = listOf(
                "#######   ",
                "#G....#   G(200)",
                "#.G...#   G(131)",
                "#.#.#G#   G(122)",
                "#...#E#   E(122)",
                "#..G..#   G(200)",
                "#######   "
        )
        val after28 = listOf(
                "#######   ",
                "#G....#   G(200)",
                "#.G...#   G(131)",
                "#.#.#G#   G(116)",
                "#...#E#   E(113)",
                "#....G#   G(200)",
                "#######   "
        )
        val after47 = listOf(
                "#######   ",
                "#G....#   G(200)",
                "#.G...#   G(131)",
                "#.#.#G#   G(59)",
                "#...#.#   ",
                "#....G#   G(200)",
                "#######   "
        )

        val d = Day15(input)
        assertEquals(initial, d.printableMap(true))
        d.doRound()
        assertEquals(after1, d.printableMap(true))
        d.doRound()
        assertEquals(after2, d.printableMap(true))
        repeat(21) { d.doRound() }
        assertEquals(after23, d.printableMap(true))
        d.doRound()
        assertEquals(after24, d.printableMap(true))
        repeat(2) { d.doRound() }
        assertEquals(after26, d.printableMap(true))
        repeat(2) { d.doRound() }
        assertEquals(after28, d.printableMap(true))
        repeat(19) { d.doRound() }
        assertEquals(after47, d.printableMap(true))
    }


    private val game1 = listOf(
            "#######",
            "#.G...#",
            "#...EG#",
            "#.#.#G#",
            "#..G#E#",
            "#.....#",
            "#######"
    )

    @Test
    fun testPart1Example1() {
        assertEquals(27730, Day15(game1).solvePart1())
    }


    @Test
    fun testPart1Example2() {
        val input = listOf(
                "#######",
                "#G..#E#",
                "#E#E.E#",
                "#G.##.#",
                "#...#E#",
                "#...E.#",
                "#######"
        )
        val d = Day15(input)
        d.solvePart1()
        d.printableMap(true).forEach { println(it) }

        assertEquals(36334, Day15(input).solvePart1())
    }

    @Test
    fun testPart1Example3() {
        val input = listOf(
                "#######",
                "#E..EG#",
                "#.#G.E#",
                "#E.##E#",
                "#G..#.#",
                "#..E#.#",
                "#######"
        )
        assertEquals(39514, Day15(input).solvePart1())
    }

    @Test
    fun testPart1Example4() {
        val input = listOf(
                "#######",
                "#E.G#.#",
                "#.#G..#",
                "#G.#.G#",
                "#G..#.#",
                "#...E.#",
                "#######"
        )
        assertEquals(27755, Day15(input).solvePart1())
    }

    @Test
    fun testPart1Example5() {
        val input = listOf(
                "#######",
                "#.E...#",
                "#.#..G#",
                "#.###.#",
                "#E#G#G#",
                "#...#G#",
                "#######"
        )
        assertEquals(28944, Day15(input).solvePart1())
    }

    @Test
    fun testPart1Example6() {
        val input = listOf(
                "#########",
                "#G......#",
                "#.E.#...#",
                "#..##..G#",
                "#...##..#",
                "#...#...#",
                "#.G...G.#",
                "#.....G.#",
                "#########"
        )
        assertEquals(18740, Day15(input).solvePart1())
    }



    @Test
    fun partOneRealInput() {
        assertEquals(195774, Day15(resourceAsList("2018/day15.txt")).solvePart1())
    }


    @Test
    fun partTwoExample1() {
        val input =listOf(
                "#######",
                "#.G...#",
                "#...EG#",
                "#.#.#G#",
                "#..G#E#",
                "#.....#",
                "#######"
        )
        assertEquals(4988, Day15(input).solvePart2())

    }

    @Test
    fun partTwoRealInput() {
        assertEquals(37272, Day15(resourceAsList("2018/day15.txt")).solvePart2())
    }
}




