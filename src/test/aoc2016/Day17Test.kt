package test.aoc2016

import aoc2016.Day17
import org.junit.Assert
import org.junit.Test

class Day17Test {

    @Test
    fun partOneExample1() {
        Assert.assertEquals("DDRRRD", Day17("ihgpwlah").solvePart1())
    }

    @Test
    fun partOneExample2() {
        Assert.assertEquals("DDUDRLRRUDRD", Day17("kglvqrro").solvePart1())
    }

    @Test
    fun partOneExample3() {
        Assert.assertEquals("DRURDRUDDLLDLUURRDULRLDUUDDDRR", Day17("ulqzkmiv").solvePart1())
    }

    @Test
    fun partOneRealInput() {
        Assert.assertEquals("RRRLDRDUDD", Day17("qtetzkpl").solvePart1())
    }

    @Test
    fun partTwoExample1() {
        Assert.assertEquals(370, Day17("ihgpwlah").solvePart2())
    }

    @Test
    fun partTwoExample2() {
        Assert.assertEquals(492, Day17("kglvqrro").solvePart2())
    }

    @Test
    fun partTwoExample3() {
        Assert.assertEquals(830, Day17("ulqzkmiv").solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        Assert.assertEquals(706, Day17("qtetzkpl").solvePart2())
    }
}