package test.aoc2016

import aoc2016.Day22
import org.junit.Assert
import org.junit.Test
import resourceAsList

class Day22Test {

    private val exampleInput = listOf(
            "du",
            "Filesystem            Size  Used  Avail  Use%",
                    "/dev/grid/node-x0-y0   10T    8T     2T   80%",
                    "/dev/grid/node-x0-y1   11T    6T     5T   54%",
                    "/dev/grid/node-x0-y2   32T   28T     4T   87%",
                    "/dev/grid/node-x1-y0    9T    7T     2T   77%",
                    "/dev/grid/node-x1-y1    8T    0T     8T    0%",
                    "/dev/grid/node-x1-y2   11T    7T     4T   63%",
                    "/dev/grid/node-x2-y0   10T    6T     4T   60%",
                    "/dev/grid/node-x2-y1    9T    8T     1T   88%",
                    "/dev/grid/node-x2-y2    9T    6T     3T   66%"
    )

    @Test
    fun partOneRealInput() {
        Assert.assertEquals(872, Day22(resourceAsList("2016/day22.txt")).solvePart1())
    }

    @Test
    fun partTwoExampleInput() {
        Assert.assertEquals(7, Day22(exampleInput).solvePart2())
    }

    @Test
    fun investigateMap() {
        Day22(resourceAsList("2016/day22.txt")).investigateMap()
    }

    @Test
    fun partTwoRealInput() {
        Assert.assertEquals(211, Day22(resourceAsList("2016/day22.txt")).solveMyPart2())
    }
}