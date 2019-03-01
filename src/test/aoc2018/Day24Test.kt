package test.aoc2018

import aoc2018.Day24
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day24Test {
    val testInput = listOf(
            "Immune System:",
            "17 units each with 5390 hit points (weak to radiation, bludgeoning) with an attack that does 4507 fire damage at initiative 2",
            "989 units each with 1274 hit points (immune to fire; weak to bludgeoning, slashing) with an attack that does 25 slashing damage at initiative 3",
            "",
            "Infection:",
            "801 units each with 4706 hit points (weak to radiation) with an attack that does 116 bludgeoning damage at initiative 1",
            "4485 units each with 2961 hit points (immune to radiation; weak to fire, cold) with an attack that does 12 slashing damage at initiative 4"
    )

    @Test
    fun testParseGroup(){
        val input = "17 units each with 5390 hit points (weak to radiation, bludgeoning) with an attack that does 4507 fire damage at initiative 2"
        val expected = Day24.Group("a", 1, 17, 5390, 4507, "fire", 2, setOf("radiation", "bludgeoning"), setOf())
        Assert.assertEquals(expected, Day24(testInput).parseGroup("a", 1, input))
    }

    @Test
    fun testParseGroup2(){
        val input = "17 units each with 5390 hit points (immune to radiation, bludgeoning; weak to fire) with an attack that does 4507 fire damage at initiative 2"
        val expected = Day24.Group("a", 1, 17, 5390, 4507, "fire", 2, setOf("fire"), setOf("radiation", "bludgeoning"))
        Assert.assertEquals(expected, Day24(testInput).parseGroup("a", 1, input))
    }

    @Test
    fun testParseGroup3(){
        val input = "17 units each with 5390 hit points (weak to fire) with an attack that does 4507 fire damage at initiative 2"
        val expected = Day24.Group("a", 1, 17, 5390, 4507, "fire", 2, setOf("fire"), setOf())
        Assert.assertEquals(expected, Day24(testInput).parseGroup("a", 1, input))
    }

    @Test
    fun testParseGroup4(){
        val input = "17 units each with 5390 hit points with an attack that does 4507 fire damage at initiative 2"
        val expected = Day24.Group("a", 1, 17, 5390, 4507, "fire", 2, setOf(), setOf())
        Assert.assertEquals(expected, Day24(testInput).parseGroup("a", 1, input))
    }

    @Test
    fun partOneTestInput() {
        assertEquals(5216, Day24(testInput).solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals(24009, Day24(resourceAsList("2018/day24.txt")).solvePart1())
    }

    @Test
    fun partTwoTestInput() {
        assertEquals(51, Day24(testInput).solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(379, Day24(resourceAsList("2018/day24.txt")).solvePart2())
    }
}




