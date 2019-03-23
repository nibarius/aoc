package test.aoc2018

import aoc2018.Day23
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day23Test {
    @Test
    fun partOneTestInput() {
        val testInput = listOf(
                "pos=<0,0,0>, r=4",
                "pos=<1,0,0>, r=1",
                "pos=<4,0,0>, r=3",
                "pos=<0,2,0>, r=1",
                "pos=<0,5,0>, r=3",
                "pos=<0,0,3>, r=1",
                "pos=<1,1,1>, r=1",
                "pos=<1,1,2>, r=1",
                "pos=<1,3,1>, r=1"
        )
        assertEquals(7, Day23(testInput).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals(380, Day23(resourceAsList("2018/day23.txt")).solvePart1())
    }

    @Test
        fun partTwoTestInput() {
        val testInput = listOf(
                "pos=<10,12,12>, r=2",
                "pos=<12,14,12>, r=2",
                "pos=<16,12,12>, r=4",
                "pos=<14,14,14>, r=6",
                "pos=<50,50,50>, r=200",
                "pos=<10,10,10>, r=2"
        )
        assertEquals(36, Day23(testInput).solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(95541011, Day23(resourceAsList("2018/day23.txt")).solvePart2())
    }

    @Test
    fun testDistanceToSurface1() {
        val pos = Day23.Pos(10, 0, 0)
        val cube = Day23.Cube(0..5, 0..5, 0..5, 0, listOf())
        assertEquals(5, cube.shortestDistance(pos))
    }

    @Test
    fun testDistanceToSurface2() {
        val pos = Day23.Pos(-10, 0, 0)
        val cube = Day23.Cube(0..5, 0..5, 0..5, 0, listOf())
        assertEquals(10, cube.shortestDistance(pos))
    }

    @Test
    fun testDistanceToSurface3() {
        val pos = Day23.Pos(3, 0, 0)
        val cube = Day23.Cube(0..5, 0..5, 0..5, 0, listOf())
        assertEquals(0, cube.shortestDistance(pos))
    }

    @Test
    fun testDistanceToSurface4() {
        val pos = Day23.Pos(3, 6, 1)
        val cube = Day23.Cube(0..5, 0..5, 0..5, 0, listOf())
        assertEquals(1, cube.shortestDistance(pos))
    }

    @Test
    fun testDistanceToSurface5() {
        val pos = Day23.Pos(10, 10, 0)
        val cube = Day23.Cube(0..5, 0..5, 0..5, 0, listOf())
        assertEquals(10 , cube.shortestDistance(pos))
    }

    @Test
    fun testProcessCubeNanobotOutside() {
        val nanobots = listOf(Day23.Nanobot(Day23.Pos(10, 10, 10), 2))
        val cube = Day23.Cube(0..5, 0..5, 0..5, 0, nanobots)
        assertEquals(false, cube.needsSplitting)
        assertEquals(0, cube.botsInRange)
    }

    @Test
    fun testProcessCubeNanobotIntersect() {
        val nanobots = listOf(Day23.Nanobot(Day23.Pos(10, 5, 5), 7))
        val cube = Day23.Cube(0..5, 0..5, 0..5, 0, nanobots)
        assertEquals(true, cube.needsSplitting)
        assertEquals(1, cube.botsInRange)
    }

    @Test
    fun testProcessCubeNanobotIntersect2() { //
        val nanobots = listOf(Day23.Nanobot(Day23.Pos(6, 0, 0), 15))
        val cube = Day23.Cube(0..5, 0..5, 0..5, 0, nanobots)
        assertEquals(true, cube.needsSplitting)
        assertEquals(1, cube.botsInRange)
    }

    // The closest point of the cube is in range (the rest of the cube is not in range)
    @Test
    fun testProcessCubeNanobotBarelyIntersect1() {
        val nanobots = listOf(Day23.Nanobot(Day23.Pos(10, 5, 5), 5))
        val cube = Day23.Cube(0..5, 0..5, 0..5, 0, nanobots)
        assertEquals(true, cube.needsSplitting)
        assertEquals(1, cube.botsInRange)
    }


    // nanobot outside cube, but whole cube is in range
    @Test
    fun testProcessCubeNanobotContainsCube1() {
        val nanobots = listOf(Day23.Nanobot(Day23.Pos(10, 10, 10), 100))
        val cube = Day23.Cube(0..5, 0..5, 0..5, 0, nanobots)
        assertEquals(false, cube.needsSplitting)
        assertEquals(1, cube.botsInRange)
    }

    // nanobot inside cube, and whole cube is in range
    @Test
    fun testProcessCubeNanobotContainsCube2() {
        val nanobots = listOf(Day23.Nanobot(Day23.Pos(2, 2, 2), 100))
        val cube = Day23.Cube(0..5, 0..5, 0..5, 0, nanobots)
        assertEquals(false, cube.needsSplitting)
        assertEquals(1, cube.botsInRange)
    }


    // The furthest point of the cube is in range, and so is the whole cube. But one step outside
    // of the cube is not in range. No splitting needed. Same case as whole cube inside the bot's range
    @Test
    fun testProcessCubeNanobotBarelyIntersect2() { //
        val nanobots = listOf(Day23.Nanobot(Day23.Pos(6, 0, 0), 16))
        val cube = Day23.Cube(0..5, 0..5, 0..5, 0, nanobots)
        assertEquals(false, cube.needsSplitting)
        assertEquals(1, cube.botsInRange)
    }

    @Test
    fun testProcessCubeNanobotInside() {
        val nanobots = listOf(Day23.Nanobot(Day23.Pos(2, 2, 2), 1),
                Day23.Nanobot(Day23.Pos(3, 3, 3), 1),
                Day23.Nanobot(Day23.Pos(2, 1, 3), 1))
        val cube = Day23.Cube(0..5, 0..5, 0..5, 0, nanobots)
        assertEquals(true, cube.needsSplitting)
        assertEquals(3, cube.botsInRange)
    }

    @Test
    fun testProcessCubeSizeOneCube1() {
        val nanobots = listOf(Day23.Nanobot(Day23.Pos(0, 0, 0), 1))
        val cube = Day23.Cube(0..0, 0..0, 0..0, 0, nanobots)
        assertEquals(false, cube.needsSplitting)
        assertEquals(1, cube.botsInRange)
    }

    @Test
    fun testProcessCubeSizeOneCube2() {
        val nanobots = listOf(Day23.Nanobot(Day23.Pos(5, 0, 0), 5))
        val cube = Day23.Cube(0..0, 0..0, 0..0, 0, nanobots)
        assertEquals(false, cube.needsSplitting)
        assertEquals(1, cube.botsInRange)
    }

    @Test
    fun testProcessCubeSizeOneCube3() {
        val nanobots = listOf(Day23.Nanobot(Day23.Pos(5, 0, 0), 10))
        val cube = Day23.Cube(0..0, 0..0, 0..0, 0, nanobots)
        assertEquals(false, cube.needsSplitting)
        assertEquals(1, cube.botsInRange)
    }

    @Test
    fun testShallowVsDeepOverlaps() {
        val nanobots = listOf(
                "pos=<-100,-100,-100>, r=30",
                "pos=<-100,-101,-100>, r=40",
                "pos=<-101,-100,-100>, r=50",
                "pos=<10,10,10>, r=1",
                "pos=<12,10,10>, r=1",
                "pos=<14,10,10>, r=1",
                "pos=<16,10,10>, r=1"
        )
        assertEquals(270, Day23(nanobots).solvePart2())
    }
}




