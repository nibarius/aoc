package test.aoc2016

import aoc2016.Day19
import org.junit.Assert
import org.junit.Test

class Day19Test {
    @Test
    fun partOneExample() {
        Assert.assertEquals(3, Day19(5).solvePart1())
    }

    @Test
    fun testWinnerCalculation() {
        for (i in 2..200) {
            Assert.assertEquals(Day19(i).play(), Day19(i).getWinner(i))
        }
    }

    @Test
    fun testOptimizedPart1() {
        for (i in 2..200) {
            Assert.assertEquals(Day19(i).play(), Day19(i).playOptimized())
        }
    }

    @Test
    fun partOneRealInput() {
        Assert.assertEquals(1842613, Day19(3018458).solvePart1())
    }

    @Test
    fun partTwoExample() {
        Assert.assertEquals(2, Day19(5).solvePart2())
    }

    @Test
    fun playPart2with6() {
        Assert.assertEquals(3, Day19(6).playPart2())
    }

    @Test
    fun testPart2WinnerCalculation() {
        for (i in 9..200) {
            Assert.assertEquals(Day19(i).playPart2(), Day19(i).getWinnerPart2(i))
        }
    }

    @Test
    fun partTwoRealInput() {
        Assert.assertEquals(1424135, Day19(3018458).solvePart2())
    }
}