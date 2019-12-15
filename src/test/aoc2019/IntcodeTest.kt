package test.aoc2019

import aoc2019.Intcode
import org.junit.Assert.assertEquals
import org.junit.Test

class IntcodeTest {

    private fun String.toLongList(): List<Long> {
        return this.split(",").map { it.toLong() }
    }

    @Test
    fun testDay2Example1() {
        val computer = Intcode("1,0,0,0,99".toLongList())
        computer.run()
        assertEquals("2,0,0,0,99".toLongList(), computer.dumpMemory())
    }

    @Test
    fun testDay2Example2() {
        val computer = Intcode("2,3,0,3,99".toLongList())
        computer.run()
        assertEquals("2,3,0,6,99".toLongList(), computer.dumpMemory())
    }

    @Test
    fun testDay2Example3() {
        val computer = Intcode("2,4,4,5,99,0".toLongList())
        computer.run()
        assertEquals("2,4,4,5,99,9801".toLongList(), computer.dumpMemory())
    }

    @Test
    fun testDay2Example4() {
        val computer = Intcode("1,1,1,4,99,5,6,0,99".toLongList())
        computer.run()
        assertEquals("30,1,1,4,2,5,6,0,99".toLongList(), computer.dumpMemory())
    }

    @Test
    fun testDay5InputOutput() {
        val computer = Intcode("3,0,4,0,99".toLongList(), mutableListOf(13))
        computer.run()
        assertEquals(13, computer.output.first())
    }

    @Test
    fun testDay5OperationMode() {
        val computer = Intcode("1002,4,3,4,33".toLongList())
        computer.run()
        assertEquals("1002,4,3,4,99".toLongList(), computer.dumpMemory())
    }

    @Test
    fun testDay5NegativeValues() {
        val computer = Intcode("1101,100,-1,4,0".toLongList())
        computer.run()
        assertEquals("1101,100,-1,4,99".toLongList(), computer.dumpMemory())
    }

    @Test
    fun testDay5EqualToPositionMode() {
        val computer = Intcode("3,9,8,9,10,9,4,9,99,-1,8".toLongList(), mutableListOf(8))
        computer.run()
        assertEquals(1, computer.output.first())

        computer.reinitialize()
        computer.input.add(7)
        computer.run()
        assertEquals(0, computer.output.first())
    }

    @Test
    fun testDay5EqualToImmediateMode() {
        val computer = Intcode("3,3,1108,-1,8,3,4,3,99".toLongList(), mutableListOf(8))
        computer.run()
        assertEquals(1, computer.output.first())

        computer.reinitialize()
        computer.input.add(7)
        computer.run()
        assertEquals(0, computer.output.first())
    }

    @Test
    fun testDay5LessThanPositionMode() {
        val computer = Intcode("3,9,7,9,10,9,4,9,99,-1,8".toLongList(), mutableListOf(7))
        computer.run()
        assertEquals(1, computer.output.first())

        computer.reinitialize()
        computer.input.add(8)
        computer.run()
        assertEquals(0, computer.output.first())
    }

    @Test
    fun testDay5LessThanImmediateMode() {
        val computer = Intcode("3,3,1107,-1,8,3,4,3,99".toLongList(), mutableListOf(7))
        computer.run()
        assertEquals(1, computer.output.first())

        computer.reinitialize()
        computer.input.add(8)
        computer.run()
        assertEquals(0, computer.output.first())
    }

    @Test
    fun testDay5JumpImmediateMode() {
        val computer = Intcode("3,3,1105,-1,9,1101,0,0,12,4,12,99,1".toLongList(), mutableListOf(1))
        computer.run()
        assertEquals(1, computer.output.first())

        computer.reinitialize()
        computer.input.add(0)
        computer.run()
        assertEquals(0, computer.output.first())
    }

    @Test
    fun testDay5JumpPositionMode() {
        val computer = Intcode("3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9".toLongList(), mutableListOf(1))
        computer.run()
        assertEquals(1, computer.output.first())

        computer.reinitialize()
        computer.input.add(0)
        computer.run()
        assertEquals(0, computer.output.first())
    }

    @Test
    fun testDay5LargerExample() {
        val computer = Intcode("3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99".toLongList(),
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

    @Test
    fun testDay9Quine() {
        val input= "109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99".toLongList()
        val c = Intcode(input)
        c.run()
        assertEquals(input, c.output)
    }

    @Test
    fun testDay9LargeNumbers1() {
        val input= "1102,34915192,34915192,7,4,7,99,0".toLongList()
        val c = Intcode(input)
        c.run()
        assertEquals(16, c.output.first().toString().length)
    }

    @Test
    fun testDay9LargeNumbers2() {
        val input= "104,1125899906842624,99".toLongList()
        val c = Intcode(input)
        c.run()
        assertEquals(input[1], c.output.first())
    }

    @Test
    fun testCopy() {
        // add value of address 0 with 0 and put in 0, output 0, read from input and put in 0, exit
        val program = "1,0,0,0,4,0,3,0,99".toLongList()
        val c1 = Intcode(program)
        val c2 = c1.copy()
        c1.run()
        assertEquals(2, c1.output.first())
        assertEquals(mutableListOf<Long>(), c2.output)
        val c3 = c1.copy()
        assertEquals(2L, c3.dumpMemory()[0])
        c1.input.add(100)
        c1.run()
        assertEquals("100,0,0,0,4,0,3,0,99".toLongList(), c1.dumpMemory())
        c2.input.add(99)
        c2.run()
        assertEquals("99,0,0,0,4,0,3,0,99".toLongList(), c2.dumpMemory())
        assertEquals(2L, c3.dumpMemory()[0])
    }
}