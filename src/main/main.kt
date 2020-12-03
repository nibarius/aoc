import java.io.File

fun main() {
    val year = 2020
    val day = 4
    createDayClassFile(year, day)
    createTestFile(year, day)
    createInputFile("$year/day$day.txt")
}

fun createInputFile(fileName: String) {
    val path = "src/test/resources/$fileName"
    File(path)
            .takeIf { !it.exists() }
            ?.createNewFile()
            ?.also { println("$path created") }
}

fun createTestFile(year: Int, day: Int) {
    val path = "src/test/aoc$year/Day${day}Test.kt"
    File(path)
            .takeIf { !it.exists() }
            ?.writeText(getTestCaseContent(year, day))
            ?.also { println("$path created") }
}

fun createDayClassFile(year: Int, day: Int) {
    val path = "src/main/aoc$year/Day${day}.kt"
    File("src/main/aoc$year/Day${day}.kt")
            .takeIf { !it.exists() }
            ?.writeText(getDayClassContent(year, day))
            ?.also { println("$path created") }
}



fun getTestCaseContent(year: Int, day: Int): String {
    val doubleTriple = "\"\"\"\"\"\""
    return """package test.aoc$year

import aoc${year}.Day${day}
import org.junit.Assert.assertEquals
import org.junit.Test
import resourceAsList

class Day${day}Test {
    private val exampleInput = $doubleTriple.trimIndent().split("\n")
    private val exampleDay$day = Day$day(exampleInput)
    private val day$day = Day$day(resourceAsList("${year}/day${day}.txt"))

    @Test
    fun testPartOneExample1() {
        assertEquals(0, exampleDay$day.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        assertEquals(0, day$day.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        assertEquals(0, exampleDay$day.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        assertEquals(0, day$day.solvePart2())
    }
}""".replace("\n", System.getProperty("line.separator"))
}


fun getDayClassContent(year: Int, day: Int): String {
    return """package aoc${year}

class Day${day}(input: List<String>) {

    fun solvePart1(): Int {
        return -1
    }

    fun solvePart2(): Int {
        return -1
    }
}""".replace("\n", System.getProperty("line.separator"))
}