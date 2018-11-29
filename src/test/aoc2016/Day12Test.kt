package test.aoc2016

import aoc2016.Day12
import org.junit.Assert
import org.junit.Test
import resourceAsList

class Day12Test {
    private val day1TestInput = listOf("cpy 41 a",
            "inc a",
            "inc a",
            "dec a",
            "jnz a 2",
            "dec a"
    )

    @Test
    fun partOneExample() {
        Assert.assertEquals(42, Day12(day1TestInput).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        Assert.assertEquals(318083, Day12(resourceAsList("2016/day12.txt")).solvePart1())
    }

    @Test
    fun partOneOptimizedInput() {
        Assert.assertEquals(318083, Day12(resourceAsList("2016/day12_optimized.txt")).solvePart1())
    }


    @Test
    fun partTwoOptimizedInput() {
        Assert.assertEquals(9227737, Day12(resourceAsList("2016/day12_optimized.txt")).solvePart2())
    }
}