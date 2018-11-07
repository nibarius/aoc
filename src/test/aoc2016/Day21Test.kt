package test.aoc2016

import aoc2016.Day21
import org.junit.Assert
import org.junit.Test

class Day21Test {
    private val exampleInput = listOf("swap position 4 with position 0",
            "swap letter d with letter b",
            "reverse positions 0 through 4",
            "rotate left 1 step",
            "move position 1 to position 4",
            "move position 3 to position 0",
            "rotate based on position of letter b",
            "rotate based on position of letter d")

    @Test
    fun testScramblerSwapPosition() {
        val scrambler = Day21.Scrambler("abcde")
        scrambler.swap(4, 0)
        Assert.assertEquals("ebcda", scrambler.getCurrentValue())
    }

    @Test
    fun testScramblerSwapLetter() {
        val scrambler = Day21.Scrambler("ebcda")
        scrambler.swap('d', 'b')
        Assert.assertEquals("edcba", scrambler.getCurrentValue())
    }

    @Test
    fun testScramblerRotate() {
        val scrambler = Day21.Scrambler("abcde")
        scrambler.rotate(-1)
        Assert.assertEquals("bcdea", scrambler.getCurrentValue())
    }

    @Test
    fun testScramblerRotate2() {
        val scrambler = Day21.Scrambler("abcde")
        scrambler.rotate(2)
        Assert.assertEquals("deabc", scrambler.getCurrentValue())
    }

    @Test
    fun testScramblerRotateSwapPosition() {
        val scrambler = Day21.Scrambler("abcde")
        scrambler.rotate(2)
        scrambler.swap(4, 0)
        Assert.assertEquals("ceabd", scrambler.getCurrentValue())
    }

    @Test
    fun testScramblerRotateSwapLetter() {
        val scrambler = Day21.Scrambler("abcde")
        scrambler.rotate(2)
        scrambler.swap('a', 'b')
        Assert.assertEquals("debac", scrambler.getCurrentValue())
    }

    @Test
    fun testScramblerRotateBasedOnPosition() {
        val scrambler = Day21.Scrambler("abdec")
        scrambler.rotate('b')
        Assert.assertEquals("ecabd", scrambler.getCurrentValue())
    }

    @Test
    fun testScramblerRotateBasedOnPosition2() {
        val scrambler = Day21.Scrambler("abdec")
        scrambler.rotate('c')
        Assert.assertEquals("cabde", scrambler.getCurrentValue())
    }

    @Test
    fun testScramblerReverse() {
        val scrambler = Day21.Scrambler("abcde")
        scrambler.reverse(0, 4)
        Assert.assertEquals("edcba", scrambler.getCurrentValue())
    }

    @Test
    fun testScramblerReverse2() {
        val scrambler = Day21.Scrambler("abcde")
        scrambler.reverse(1, 2)
        Assert.assertEquals("acbde", scrambler.getCurrentValue())
    }

    @Test
    fun testScramblerReverseRotate() {
        val scrambler = Day21.Scrambler("abcde")
        scrambler.rotate(2)
        scrambler.reverse(2, 4)
        Assert.assertEquals("decba", scrambler.getCurrentValue())
    }

    @Test
    fun testScramblerMoveRight() {
        val scrambler = Day21.Scrambler("bcdea")
        scrambler.move(1, 4)
        Assert.assertEquals("bdeac", scrambler.getCurrentValue())
    }

    @Test
    fun testScramblerMoveLeft() {
        val scrambler = Day21.Scrambler("bdeac")
        scrambler.move(3, 0)
        Assert.assertEquals("abdec", scrambler.getCurrentValue())
    }

    @Test
    fun testScramblerRotateMove() {
        val scrambler = Day21.Scrambler("bdeac")
        scrambler.rotate(2)
        scrambler.move(3, 0)
        Assert.assertEquals("dacbe", scrambler.getCurrentValue())
    }

    @Test
    fun partOneExample() {
        Assert.assertEquals("decab", Day21("abcde", exampleInput).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        Assert.assertEquals("dgfaehcb", Day21("abcdefgh", resourceAsList("2016/day21.txt")).solvePart1())
    }

    @Test
    fun testDescramblingRealInput() {
        Assert.assertEquals("abcdefgh", Day21("dgfaehcb", resourceAsList("2016/day21.txt")).solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        Assert.assertEquals("fdhgacbe", Day21("fbgdceah", resourceAsList("2016/day21.txt")).solvePart2())
    }
}