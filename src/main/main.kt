import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.result.Result
import java.io.File

fun main(args: Array<String>) {
    val year = 2025
    val day = 2
    when (args.firstOrNull()) {
        "download" -> readInputFileFromInternet(year, day)
        "decrypt" -> decryptInputFile(year, day)
        "decrypt_all" -> {
            ((2015..2016) + (2018..2024)).forEach { y ->
                (1..25).forEach { d -> decryptInputFile(y, d) }
            }
        }
        else -> {
            createDayClassFile(year, day)
            createTestFile(year, day)
        }
    }
}

private fun decryptInputFile(year: Int, day: Int) {
    val inPath = "src/test/resources/encrypted/$year/day$day.bin"
    val outPath = "src/test/resources/$year/day$day.txt"
    val plaintext = Encryption.aeadDecrypt(File(inPath).readBytes(), resourceAsString("key.json"))
    File(outPath).writeText(plaintext)
    println("$outPath decrypted successfully")
}

private fun encryptInputFile(data: String, year: Int, day: Int) {
    val outPath = "src/test/resources/encrypted/$year/day$day.bin"
    val encrypted = Encryption.aeadEncrypt(data, resourceAsString("key.json"))
    File(outPath).writeBytes(encrypted)
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

                    // Create an encrypted copy that's stored in git
                    encryptInputFile(result.value, year, day)

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