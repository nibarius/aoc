package test.aoc2016

import aoc2016.Day9
import org.junit.Assert
import org.junit.Test
import resourceAsString

class Day9Test {

    @Test
    fun partOneExample1() {
        Assert.assertEquals("ADVENT".length, Day9("ADVENT").solvePart1())
    }

    @Test
    fun partOneExample2() {
        Assert.assertEquals("ABBBBBC".length, Day9("A(1x5)BC").solvePart1())
    }

    @Test
    fun partOneExample3() {
        Assert.assertEquals("XYZXYZXYZ".length, Day9("(3x3)XYZ").solvePart1())
    }

    @Test
    fun partOneExample4() {
        Assert.assertEquals("ABCBCDEFEFG".length, Day9("A(2x2)BCD(2x2)EFG").solvePart1())
    }

    @Test
    fun partOneExample5() {
        Assert.assertEquals("(1x3)A".length, Day9("(6x1)(1x3)A").solvePart1())
    }

    @Test
    fun partOneExample6() {
        Assert.assertEquals("X(3x3)ABC(3x3)ABCY".length, Day9("X(8x2)(3x3)ABCY").solvePart1())
    }

    @Test
    fun partOneRealInput() {
        Assert.assertEquals(98135, Day9(resourceAsString("2016/day9.txt")).solvePart1())
    }

    @Test
    fun partTwoExample1() {
        Assert.assertEquals(9.toBigInteger(), Day9("(3x3)XYZ").solvePart2())
    }

    @Test
    fun partTwoExample2() {
        Assert.assertEquals(20.toBigInteger(), Day9("XABCABCABCABCABCABCY").solvePart2())
    }

    @Test
    fun partTwoExample3() {
        Assert.assertEquals(241920.toBigInteger(), Day9("(27x12)(20x12)(13x14)(7x10)(1x12)A").solvePart2())
    }


    @Test
    fun partTwoExample4() {
        Assert.assertEquals(445.toBigInteger(), Day9("(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN").solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        Assert.assertEquals(10964557606.toBigInteger(), Day9(resourceAsString("2016/day9.txt")).solvePart2())
    }
}