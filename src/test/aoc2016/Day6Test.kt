package test.aoc2016

import aoc2016.Day6
import org.junit.Assert
import org.junit.Test

class Day6Test {
    private val day1TestInput = listOf("eedadn",
            "drvtee",
            "eandsr",
            "raavrd",
            "atevrs",
            "tsrnev",
            "sdttsa",
            "rasrtv",
            "nssdts",
            "ntnada",
            "svetve",
            "tesnvt",
            "vntsnd",
            "vrdear",
            "dvrsen",
            "enarar"
    )

    @Test
    fun partOneExample() {
        Assert.assertEquals("easter", Day6(day1TestInput).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        Assert.assertEquals("afwlyyyq", Day6(resourceAsList("2016/day6.txt")).solvePart1())
    }

    @Test
    fun partTwoExample() {
        Assert.assertEquals("advent", Day6(day1TestInput).solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        Assert.assertEquals("bhkzekao", Day6(resourceAsList("2016/day6.txt")).solvePart2())
    }
}