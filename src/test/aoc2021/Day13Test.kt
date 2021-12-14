package test.aoc2021

import aoc2021.Day13
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day13Test {
    private val exampleInput = """
        6,10
        0,14
        9,10
        0,3
        10,4
        4,11
        6,0
        6,12
        4,1
        0,13
        10,12
        3,4
        3,0
        8,4
        1,10
        2,14
        8,10
        9,0

        fold along y=7
        fold along x=5
    """.trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day13 = Day13(exampleInput)
        assertEquals(17, day13.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day13 = Day13(resourceAsList("2021/day13.txt"))
        assertEquals(735, day13.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day13 = Day13(exampleInput)
        val expected = """
            #####
            #   #
            #   #
            #   #
            #####
        """.trimIndent()
        assertEquals(expected, day13.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day13 = Day13(resourceAsList("2021/day13.txt"))
        val expected = """
#  # #### ###  #### #  #  ##  #  # ####
#  # #    #  #    # # #  #  # #  #    #
#  # ###  #  #   #  ##   #  # #  #   # 
#  # #    ###   #   # #  #### #  #  #  
#  # #    # #  #    # #  #  # #  # #   
 ##  #    #  # #### #  # #  #  ##  ####
        """.trimIndent()
        assertEquals(expected, day13.solvePart2())
    }
}