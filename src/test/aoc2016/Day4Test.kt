package test.aoc2016

import aoc2016.Day4
import org.junit.Assert
import org.junit.Test

class Day4Test {

    @Test
    fun partOneExample() {
        Assert.assertEquals(1514, Day4(listOf<String>("aaaaa-bbb-z-y-x-123[abxyz]",
                "a-b-c-d-e-f-g-h-987[abcde]",
                "not-a-real-room-404[oarel]",
                "totally-real-room-200[decoy]")).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        Assert.assertEquals(409147, Day4(resourceAsList("2016/day4.txt")).solvePart1())
    }

    @Test
    fun partTwoDecryptionTest() {
        Assert.assertEquals("very encrypted name", Day4.decrypt("qzmt-zixmtkozy-ivhz", 343))
    }

    @Test
    fun partTwoRealInput() {
        Assert.assertEquals(991, Day4(resourceAsList("2016/day4.txt")).solvePart2())
    }
}