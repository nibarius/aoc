package test.aoc2016

import aoc2016.Assembunny
import aoc2016.Day23
import org.junit.Assert
import org.junit.Test

class Day23Test {

    private val exampleInput = listOf(
            "cpy 2 a",
            "tgl a",
            "tgl a",
            "tgl a",
            "cpy 1 a",
            "dec a",
            "dec a"
    )

    private val mulProgram = listOf("cpy a b",
            "dec b",
            "cpy a d",
            "cpy 0 a",
            "cpy b c",
            "inc a",
            "dec c",
            "jnz c -2",
            "dec d",
            "jnz d -5")

    private val mulProgram2 = listOf("cpy a b",
            "dec b",
            "mul b a",
            "nop",
            "nop",
            "nop",
            "nop",
            "nop",
            "nop",
            "nop")

    @Test
    fun partOneExampleInput() {
        val bunny = Assembunny(exampleInput.toMutableList())
        bunny.execute()
        Assert.assertEquals(3, bunny.registers["a"]!!)
    }

    @Test
    fun testMultiplicationWithoutMulInstruction() {
        // Just verify that I understand the assembunny correctly
        // Line 4-10 calculates a = b * d
        val bunny = Assembunny(mulProgram.toMutableList())
        val a = 7
        bunny.registers["a"] = a
        bunny.execute()
        Assert.assertEquals(a * (a - 1), bunny.registers["a"]!!)
    }

    @Test
    fun testMulInstruction() {
        val bunny = Assembunny(mulProgram2.toMutableList())
        val bunny2 = Assembunny(mulProgram.toMutableList())
        val a = 7
        bunny.registers["a"] = a
        bunny2.registers["a"] = a
        bunny.execute()
        bunny2.execute()
        Assert.assertEquals(bunny2.registers, bunny.registers)
        println(bunny.registers)
    }

    @Test
    fun partOneRealInput() {
        Assert.assertEquals(14065, Day23(resourceAsList("2016/day23_optimized.txt").toMutableList()).solvePart1())
    }

    @Test
    fun partTwoRealInput() {
        Assert.assertEquals(479010625, Day23(resourceAsList("2016/day23_optimized.txt")).solvePart2())
    }
}