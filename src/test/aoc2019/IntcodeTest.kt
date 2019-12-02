package test.aoc2019

import aoc2019.Intcode
import org.junit.Assert.assertEquals
import org.junit.Test

class IntcodeTest {

    private fun String.toIntList(): List<Int> {
        return this.split(",").map { it.toInt() }
    }

    @Test
    fun testDay2Example1() {
        val computer = Intcode("1,0,0,0,99".toIntList())
        computer.initialize()
        computer.run()
        assertEquals(listOf(2, 0, 0, 0, 99), computer.dumpMemory())
    }

    @Test
    fun testDay2Example2() {
        val computer = Intcode("2,3,0,3,99".toIntList())
        computer.initialize()
        computer.run()
        assertEquals(listOf(2, 3, 0, 6, 99), computer.dumpMemory())
    }

    @Test
    fun testDay2Example3() {
        val computer = Intcode("2,4,4,5,99,0".toIntList())
        computer.initialize()
        computer.run()
        assertEquals(listOf(2, 4, 4, 5, 99, 9801), computer.dumpMemory())
    }

    @Test
    fun testDay2Example4() {
        val computer = Intcode("1,1,1,4,99,5,6,0,99".toIntList())
        computer.initialize()
        computer.run()
        assertEquals(listOf(30, 1, 1, 4, 2, 5, 6, 0, 99), computer.dumpMemory())
    }
}


