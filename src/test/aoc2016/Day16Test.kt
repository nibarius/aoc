package test.aoc2016

import aoc2016.Day16
import org.junit.Assert
import org.junit.Test
import resourceAsString

class Day16Test {

    @Test
    fun testDragonStep1() {
        Assert.assertEquals("100", Day16(0, "").dragonStep("1"))
    }

    @Test
    fun testDragonStep2() {
        Assert.assertEquals("001", Day16(0, "").dragonStep("0"))
    }

    @Test
    fun testDragonStep3() {
        Assert.assertEquals("11111000000", Day16(0, "").dragonStep("11111"))
    }

    @Test
    fun testDragonStep4() {
        Assert.assertEquals("1111000010100101011110000", Day16(0, "").dragonStep("111100001010"))
    }

    @Test
    fun testChecksum() {
        Assert.assertEquals("100", Day16(0, "").checksum("110010110100"))
    }

    @Test
    fun testChecksumOptimized() {
        Assert.assertEquals("100", Day16(0, "").checksumOptimized("110010110100"))
    }

    @Test
    fun testChecksumOptimized2() {
        Assert.assertEquals("100", Day16(0, "").checksumOptimized2("110010110100"))
    }

    @Test
    fun partOneExample1() {
        Assert.assertEquals("01100", Day16(20, "10000").solvePart1())
    }

    @Test
    fun partOneRealInput() {
        Assert.assertEquals("10100101010101101", Day16(272, resourceAsString("2016/day16.txt")).solvePart1())
    }

    @Test
    fun partTwoRealInput() {
        Assert.assertEquals("01100001101101001", Day16(35651584, resourceAsString("2016/day16.txt")).solvePart2())
    }
}