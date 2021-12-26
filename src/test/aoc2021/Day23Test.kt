package test.aoc2021

import aoc2021.Day23
import aoc2021.Day23.Move
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day23Test {
    private val exampleInput = """
#############
#...........#
###B#C#B#D###
  #A#D#C#A#
  #########
    """.trimIndent().split("\n")

    @Test
    fun testInitialStateBLockedCantMove() {
        val day23 = Day23(exampleInput)
        assertEquals(listOf<Move>(), day23.findDestinations(day23.initialMap, 8))
        assertEquals(listOf<Move>(), day23.findDestinations(day23.initialMap, 10))
        assertEquals(listOf<Move>(), day23.findDestinations(day23.initialMap, 12))
        assertEquals(listOf<Move>(), day23.findDestinations(day23.initialMap, 14))
    }

    @Test
    fun testInitialStateCanMove() {
        val day23 = Day23(exampleInput)
        assertEquals(
            listOf(Move(0, 3), Move(1, 2), Move(2, 2), Move(3, 4), Move(4, 6), Move(5, 8), Move(6, 9)),
            day23.findDestinations(day23.initialMap, 7)
        )
        assertEquals(
            listOf(Move(0, 5), Move(1, 4), Move(2, 2), Move(3, 2), Move(4, 4), Move(5, 6), Move(6, 7)),
            day23.findDestinations(day23.initialMap, 9)
        )
        assertEquals(
            listOf(Move(0, 7), Move(1, 6), Move(2, 4), Move(3, 2), Move(4, 2), Move(5, 4), Move(6, 5)),
            day23.findDestinations(day23.initialMap, 11)
        )
        assertEquals(
            listOf(Move(0, 9), Move(1, 8), Move(2, 6), Move(3, 4), Move(4, 2), Move(5, 2), Move(6, 3)),
            day23.findDestinations(day23.initialMap, 13)
        )
    }

    private val blockedCorridor = """
#############
#.....B.....#
###.#C#B#D###
  #A#D#C#A#
  #########
    """.trimIndent().split("\n")

    @Test
    fun testBlockedCorridorMoveOut() {
        val day23 = Day23(blockedCorridor)
        assertEquals( // Move C
            listOf(Move(0, 5), Move(1, 4), Move(2, 2)),
            day23.findDestinations(day23.initialMap, 9)
        )
        assertEquals( // Move B in room
            listOf(Move(4, 2), Move(5, 4), Move(6, 5)),
            day23.findDestinations(day23.initialMap, 11)
        )
        assertEquals( // Move D
            listOf(Move(4, 2), Move(5, 2), Move(6, 3)),
            day23.findDestinations(day23.initialMap, 13)
        )
    }

    private val doubleBlockedCorridor = """
#############
#...B.C.....#
###.#.#B#D###
  #A#D#C#A#
  #########
    """.trimIndent().split("\n")

    @Test
    fun testBlockedCorridorMoveOut2() {
        val day23 = Day23(doubleBlockedCorridor)
        assertEquals( // Move D in first room
            listOf<Move>(),
            day23.findDestinations(day23.initialMap, 10)
        )
        assertEquals( // Move B in room
            listOf(Move(4, 2), Move(5, 4), Move(6, 5)),
            day23.findDestinations(day23.initialMap, 11)
        )
        assertEquals( // Move D in second room
            listOf(Move(4, 2), Move(5, 2), Move(6, 3)),
            day23.findDestinations(day23.initialMap, 13)
        )
    }

    private val moveOut = """
#############
#.....D.D...#
###.#B#C#.###
  #A#B#C#A#
  #########
    """.trimIndent().split("\n")

    @Test
    fun testCanMoveOutOfOtherHome() { // A can move out from the D home even if home A contains only A
        val day23 = Day23(moveOut)
        assertEquals(listOf(Move(5, 3), Move(6,4)), day23.findDestinations(day23.initialMap, 14))
    }
    @Test
    fun doNotMoveOutIfNotNeeded() { // Home A contains only A, can't move out
        val day23 = Day23(moveOut)
        assertEquals(listOf<Move>(),day23.findDestinations(day23.initialMap, 8))
    }

    private val blockedMoveBack = """
#############
#...B.D.B.CA#
###.#.#.#.###
  #A#D#C#.#
  #########
    """.trimIndent().split("\n")

    @Test
    fun testBlockedMoveBack() {
        val day23 = Day23(blockedMoveBack)
        assertEquals(listOf<Move>(), day23.findDestinations(day23.initialMap, 2))
        assertEquals(listOf<Move>(), day23.findDestinations(day23.initialMap, 3))
        assertEquals(listOf<Move>(), day23.findDestinations(day23.initialMap, 4))
        assertEquals(listOf<Move>(), day23.findDestinations(day23.initialMap, 5))
        assertEquals(listOf<Move>(), day23.findDestinations(day23.initialMap, 6))
    }

    private val canMoveBack = """
#############
#A..B.C.D.D.#
###.#.#.#.###
  #A#B#C#.#
  #########
    """.trimIndent().split("\n")

    @Test
    fun testMoveBack() {
        val day23 = Day23(canMoveBack)
        assertEquals(listOf(Move(7, 3)), day23.findDestinations(day23.initialMap, 0))
        assertEquals(listOf(Move(9, 2)), day23.findDestinations(day23.initialMap, 2))
        assertEquals(listOf(Move(11, 2)), day23.findDestinations(day23.initialMap, 3))
        assertEquals(listOf(Move(14, 3)), day23.findDestinations(day23.initialMap, 4))
        assertEquals(listOf(Move(14, 3)), day23.findDestinations(day23.initialMap, 5))
    }

    @Test
    fun testPartOneExampleLastStep() {
        val input = """
#############
#.........A.#
###.#B#C#D###
  #A#B#C#D#
  #########
    """.trimIndent().split("\n")
        val day23 = Day23(input)
        assertEquals(8, day23.solvePart1())
    }

    @Test
    fun testPartOneExampleStepNMinus1() {
        val input = """
#############
#.....D.D.A.#
###.#B#C#.###
  #A#B#C#.#
  #########
    """.trimIndent().split("\n")
        val day23 = Day23(input)
        assertEquals(7008, day23.solvePart1())
    }

    @Test
    fun testPartOneExample1() {
        val day23 = Day23(exampleInput)
        assertEquals(12521, day23.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day23 = Day23(resourceAsList("2021/day23.txt"))
        assertEquals(12530, day23.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day23 = Day23(exampleInput)
        assertEquals(44169, day23.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day23 = Day23(resourceAsList("2021/day23.txt"))
        assertEquals(50492, day23.solvePart2())
    }
}