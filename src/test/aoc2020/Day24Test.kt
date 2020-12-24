package test.aoc2020

import aoc2020.Day24
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day24Test {
    private val exampleInput = """
        sesenwnenenewseeswwswswwnenewsewsw
        neeenesenwnwwswnenewnwwsewnenwseswesw
        seswneswswsenwwnwse
        nwnwneseeswswnenewneswwnewseswneseene
        swweswneswnenwsewnwneneseenw
        eesenwseswswnenwswnwnwsewwnwsene
        sewnenenenesenwsewnenwwwse
        wenwwweseeeweswwwnwwe
        wsweesenenewnwwnwsenewsenwwsesesenwne
        neeswseenwwswnwswswnw
        nenwswwsewswnenenewsenwsenwnesesenew
        enewnwewneswsewnwswenweswnenwsenwsw
        sweneswneswneneenwnewenewwneswswnese
        swwesenesewenwneswnwwneseswwne
        enesenwswwswneneswsenwnewswseenwsese
        wnwnesenesenenwwnenwsewesewsesesew
        nenewswnwewswnenesenwnesewesw
        eneswnwswnwsenenwnwnwwseeswneewsenese
        neswnwewnwnwseenwseesewsenwsweewe
        wseweeenwnesenwwwswnew
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day24 = Day24(exampleInput)
        assertEquals(10, day24.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day24 = Day24(resourceAsList("2020/day24.txt"))
        assertEquals(326, day24.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day24 = Day24(exampleInput)
        assertEquals(2208, day24.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day24 = Day24(resourceAsList("2020/day24.txt"))
        assertEquals(3979, day24.solvePart2())
    }
}