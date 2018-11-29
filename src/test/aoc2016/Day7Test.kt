package test.aoc2016

import aoc2016.Day7
import org.junit.Assert
import org.junit.Test
import resourceAsList

class Day7Test {
    private val day1TestInput = listOf("abba[mnop]qrst",
            "abcd[bddb]xyyx",
            "aaaa[qwer]tyui",
            "ioxxoj[asdfgh]zxcvbn"
    )

    @Test
    fun partOneExample() {
        Assert.assertEquals(2, Day7(day1TestInput).solvePart1())
    }

    @Test
    fun partOneExample1() {
        Assert.assertEquals(1, Day7(day1TestInput.subList(0, 1)).solvePart1())
    }

    @Test
    fun partOneExample2() {
        Assert.assertEquals(0, Day7(day1TestInput.subList(1, 2)).solvePart1())
    }

    @Test
    fun partOneExample3() {
        Assert.assertEquals(0, Day7(day1TestInput.subList(2, 3)).solvePart1())
    }

    @Test
    fun partOneExample4() {
        Assert.assertEquals(1, Day7(day1TestInput.subList(3, 4)).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        Assert.assertTrue("There should be more than 110 addresses",
                110 < Day7(resourceAsList("2016/day7.txt")).solvePart1())
        Assert.assertEquals(115, Day7(resourceAsList("2016/day7.txt")).solvePart1())
    }

    @Test
    fun partTwoExample1() {
        Assert.assertEquals(1, Day7(listOf("aba[bab]xyz")).solvePart2())
    }

    @Test
    fun partTwoExample2() {
        Assert.assertEquals(0, Day7(listOf("xyx[xyx]xyx")).solvePart2())
    }

    @Test
    fun partTwoExample3() {
        Assert.assertEquals(1, Day7(listOf("aaa[kek]eke")).solvePart2())
    }

    @Test
    fun partTwoExample4() {
        Assert.assertEquals(1, Day7(listOf("zazbz[bzb]cdb")).solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        Assert.assertEquals(231, Day7(resourceAsList("2016/day7.txt")).solvePart2())
    }
}