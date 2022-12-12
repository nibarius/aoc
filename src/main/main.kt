import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.result.Result
import java.io.File

fun main(args: Array<String>) {
    val year = 2022
    val day = 12
    if(args.firstOrNull() == "download") {
        readInputFileFromInternet(year, day)
    }
    else {
        createDayClassFile(year, day)
        createTestFile(year, day)
    }

}

fun readInputFileFromInternet(year: Int, day: Int) {
    val path = "src/test/resources/$year/day$day.txt"
    val file = File(path)
    if (file.exists()) {
        println("Input file download aborted, file already exists")
        return
    }

    val sessionCookie = resourceAsString("session_cookie.txt")
    Fuel.get("https://adventofcode.com/$year/day/$day/input")
            .header(Headers.USER_AGENT to "github.com/nibarius/aoc/blob/master/src/main/main.kt by nibarius")
            .header(Headers.COOKIE to "session=$sessionCookie")
            .responseString { _, response, result ->
                when (result) {
                    is Result.Failure -> {
                        println("Failed to download input: ${response.statusCode} ${response.responseMessage}")
                    }
                    is Result.Success -> {
                        // Make sure to use same line separators as the system
                        result.value
                                .replace("\n", System.getProperty("line.separator"))
                                .also { File(path).writeText(it) }
                        println("$path downloaded successfully")
                    }
                }
            }
            .join()
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

    @Test
    fun testPartOneExample1() {
        val day$day = Day$day(exampleInput)
        assertEquals(0, day$day.solvePart1())
    }

    @Test
    fun partOneRealInput() {
        val day$day = Day$day(resourceAsList("${year}/day${day}.txt"))
        assertEquals(0, day$day.solvePart1())
    }

    @Test
    fun testPartTwoExample1() {
        val day$day = Day$day(exampleInput)
        assertEquals(0, day$day.solvePart2())
    }

    @Test
    fun partTwoRealInput() {
        val day$day = Day$day(resourceAsList("${year}/day${day}.txt"))
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