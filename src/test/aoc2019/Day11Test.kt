package test.aoc2019

import aoc2019.Day11
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day11Test {
    @Test
    fun partOneRealInput() {
        assertEquals(2016, Day11(resourceAsList("2019/day11.txt", ",")).solvePart1())
    }

    @Test
    fun partTwoRealInput() {
        val expected = """
            | ###   ##  ###  ###   ##  ###  ###  #  #   
            | #  # #  # #  # #  # #  # #  # #  # #  #   
            | #  # #  # #  # #  # #    ###  #  # ####   
            | ###  #### ###  ###  #    #  # ###  #  #   
            | # #  #  # #    # #  #  # #  # #    #  #   
            | #  # #  # #    #  #  ##  ###  #    #  #   
        """.trimMargin()
        assertEquals(expected, Day11(resourceAsList("2019/day11.txt", ",")).solvePart2())
    }
}