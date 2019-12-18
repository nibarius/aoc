package test.aoc2019

import aoc2019.Day18
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day18Test {
    @Test
    fun testPartOneExample1() {
        val input = """
            #########
            #b.A.@.a#
            #########
        """.trimIndent().split("\n")
        assertEquals(8, Day18(input).solvePart1())
    }

    @Test
    fun testPartOneExample2() {
        val input = """
            ########################
            #f.D.E.e.C.b.A.@.a.B.c.#
            ######################.#
            #d.....................#
            ########################
        """.trimIndent().split("\n")
        assertEquals(86, Day18(input).solvePart1())
    }

    @Test
    fun testPartOneExample3() {
        val input = """
            ########################
            #...............b.C.D.f#
            #.######################
            #.....@.a.B.c.d.A.e.F.g#
            ########################
        """.trimIndent().split("\n")
        assertEquals(132, Day18(input).solvePart1())
    }

    @Test
    fun testPartOneExample4() {
        val input = """
            #################
            #i.G..c...e..H.p#
            ########.########
            #j.A..b...f..D.o#
            ########@########
            #k.E..a...g..B.n#
            ########.########
            #l.F..d...h..C.m#
            #################
        """.trimIndent().split("\n")
        assertEquals(136, Day18(input).solvePart1())
    }

    @Test
    fun testPartOneExample5() {
        val input = """
            ########################
            #@..............ac.GI.b#
            ###d#e#f################
            ###A#B#C################
            ###g#h#i################
            ########################
        """.trimIndent().split("\n")
        assertEquals(81, Day18(input).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        // Optimizations needed, takes 1 minute 26 seconds to run
        assertEquals(5858, Day18(resourceAsList("2019/day18.txt")).solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val input = """
            #######
            #a.#Cd#
            ##...##
            ##.@.##
            ##...##
            #cB#Ab#
            #######
        """.trimIndent().split("\n")
        assertEquals(8, Day18(input).solvePart2())
    }

    @Test
    fun testPartTwoExample2() {
        val input = """
            ###############
            #d.ABC.#.....a#
            ######...######
            ######.@.######
            ######...######
            #b.....#.....c#
            ###############
        """.trimIndent().split("\n")
        assertEquals(24, Day18(input).solvePart2())
    }

    @Test
    fun testPartTwoExample3() {
        val input = """
            #############
            #DcBa.#.GhKl#
            #.###...#I###
            #e#d#.@.#j#k#
            ###C#...###J#
            #fEbA.#.FgHi#
            #############
        """.trimIndent().split("\n")
        assertEquals(32, Day18(input).solvePart2())
    }

    @Test
    fun testPartTwoExample4() {
        val input = """
            #############
            #g#f.D#..h#l#
            #F###e#E###.#
            #dCba...BcIJ#
            #####.@.#####
            #nK.L...G...#
            #M###N#H###.#
            #o#m..#i#jk.#
            #############
        """.trimIndent().split("\n")
        assertEquals(72, Day18(input).solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(2144, Day18(resourceAsList("2019/day18.txt")).solvePart2())
    }
}