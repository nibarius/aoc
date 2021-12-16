package test.aoc2021

import aoc2021.Day16
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day16Test {
    private val exampleInput = """8A004A801A8002F478""".trimIndent().split("\n")
    private val exampleInput2 = """620080001611562C8802118E34""".trimIndent().split("\n")
    private val exampleInput3 = """C0015000016115A2E0802F182340""".trimIndent().split("\n")
    private val exampleInput4 = """A0016C880162017C3686B18A3D4780""".trimIndent().split("\n")

    @Test
    fun testPartOneExample1() {
        val day16 = Day16(exampleInput)
        assertEquals(16, day16.solvePart1())
    }

    @Test
    fun testPartOneLiteral() {
        val day16 = Day16(listOf("D2FE28"))
        assertEquals(6, day16.solvePart1())
    }

    @Test
    fun testPartOneExample2() {
        val day16 = Day16(exampleInput2)
        assertEquals(12, day16.solvePart1())
    }
    @Test
    fun testPartOneExample3() {
        val day16 = Day16(exampleInput3)
        assertEquals(23, day16.solvePart1())
    }
    @Test
    fun testPartOneExample4() {
        val day16 = Day16(exampleInput4)
        assertEquals(31, day16.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day16 = Day16(resourceAsList("2021/day16.txt"))
        assertEquals(877, day16.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day16 = Day16(listOf("C200B40A82"))
        assertEquals(3, day16.solvePart2())
    }

    @Test
    fun testPartTwoExample2() {
        val day16 = Day16(listOf("04005AC33890"))
        assertEquals(54, day16.solvePart2())
    }

    @Test
    fun testPartTwoExample3() {
        val day16 = Day16(listOf("880086C3E88112"))
        assertEquals(7, day16.solvePart2())
    }

    @Test
    fun testPartTwoExample4() {
        val day16 = Day16(listOf("CE00C43D881120"))
        assertEquals(9, day16.solvePart2())
    }

    @Test
    fun testPartTwoExample5() {
        val day16 = Day16(listOf("D8005AC2A8F0"))
        assertEquals(1, day16.solvePart2())
    }

    @Test
    fun testPartTwoExample6() {
        val day16 = Day16(listOf("F600BC2D8F"))
        assertEquals(0, day16.solvePart2())
    }

    @Test
    fun testPartTwoExample7() {
        val day16 = Day16(listOf("9C005AC2F8F0"))
        assertEquals(0, day16.solvePart2())
    }

    @Test
    fun testPartTwoExample8() {
        val day16 = Day16(listOf("9C0141080250320F1802104A08"))
        assertEquals(1, day16.solvePart2())
    }

    @Test
    fun testPartTwoExtraTest1() {
        val day16 = Day16(listOf("32F5DF3B128"))
        assertEquals(123456789, day16.solvePart2())
    }

    @Test
    fun testPartTwoExtraTest2() {
        val day16 = Day16(listOf("D2FE28"))
        assertEquals(2021, day16.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day16 = Day16(resourceAsList("2021/day16.txt"))
        assertEquals(194435634456, day16.solvePart2())
    }
}