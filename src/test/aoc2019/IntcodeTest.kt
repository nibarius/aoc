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
        computer.run()
        assertEquals(listOf(2, 0, 0, 0, 99), computer.dumpMemory())
    }

    @Test
    fun testDay2Example2() {
        val computer = Intcode("2,3,0,3,99".toIntList())
        computer.run()
        assertEquals(listOf(2, 3, 0, 6, 99), computer.dumpMemory())
    }

    @Test
    fun testDay2Example3() {
        val computer = Intcode("2,4,4,5,99,0".toIntList())
        computer.run()
        assertEquals(listOf(2, 4, 4, 5, 99, 9801), computer.dumpMemory())
    }

    @Test
    fun testDay2Example4() {
        val computer = Intcode("1,1,1,4,99,5,6,0,99".toIntList())
        computer.run()
        assertEquals(listOf(30, 1, 1, 4, 2, 5, 6, 0, 99), computer.dumpMemory())
    }

    @Test
    fun testDay5InputOutput() {
        val computer = Intcode("3,0,4,0,99".toIntList(), mutableListOf(13))
        computer.run()
        assertEquals(13, computer.output.first())
    }

    @Test
    fun testDay5OperationMode() {
        val computer = Intcode("1002,4,3,4,33".toIntList())
        computer.run()
        assertEquals(listOf(1002,4,3,4,99), computer.dumpMemory())
    }

    @Test
    fun testDay5NegativeValues() {
        val computer = Intcode("1101,100,-1,4,0".toIntList())
        computer.run()
        assertEquals(listOf(1101,100,-1,4,99), computer.dumpMemory())
    }

    @Test
    fun testDay5EqualToPositionMode() {
        val computer = Intcode("3,9,8,9,10,9,4,9,99,-1,8".toIntList(), mutableListOf(8))
        computer.run()
        assertEquals(1, computer.output.first())

        computer.reinitialize()
        computer.input.add(7)
        computer.run()
        assertEquals(0, computer.output.first())
    }

    @Test
    fun testDay5EqualToImmediateMode() {
        val computer = Intcode("3,3,1108,-1,8,3,4,3,99".toIntList(), mutableListOf(8))
        computer.run()
        assertEquals(1, computer.output.first())

        computer.reinitialize()
        computer.input.add(7)
        computer.run()
        assertEquals(0, computer.output.first())
    }

    @Test
    fun testDay5LessThanPositionMode() {
        val computer = Intcode("3,9,7,9,10,9,4,9,99,-1,8".toIntList(), mutableListOf(7))
        computer.run()
        assertEquals(1, computer.output.first())

        computer.reinitialize()
        computer.input.add(8)
        computer.run()
        assertEquals(0, computer.output.first())
    }

    @Test
    fun testDay5LessThanImmediateMode() {
        val computer = Intcode("3,3,1107,-1,8,3,4,3,99".toIntList(), mutableListOf(7))
        computer.run()
        assertEquals(1, computer.output.first())

        computer.reinitialize()
        computer.input.add(8)
        computer.run()
        assertEquals(0, computer.output.first())
    }

    @Test
    fun testDay5JumpImmediateMode() {
        val computer = Intcode("3,3,1105,-1,9,1101,0,0,12,4,12,99,1".toIntList(), mutableListOf(1))
        computer.run()
        assertEquals(1, computer.output.first())

        computer.reinitialize()
        computer.input.add(0)
        computer.run()
        assertEquals(0, computer.output.first())
    }

    @Test
    fun testDay5JumpPositionMode() {
        val computer = Intcode("3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9".toIntList(), mutableListOf(1))
        computer.run()
        assertEquals(1, computer.output.first())

        computer.reinitialize()
        computer.input.add(0)
        computer.run()
        assertEquals(0, computer.output.first())
    }

    @Test
    fun testDay5LargerExample() {
        val computer = Intcode("3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99".toIntList(),
                mutableListOf(1))
        computer.run()
        assertEquals(999, computer.output.first())

        computer.reinitialize()
        computer.input.add(8)
        computer.run()
        assertEquals(1000, computer.output.first())

        computer.reinitialize()
        computer.input.add(9)
        computer.run()
        assertEquals(1001, computer.output.first())
    }
}


