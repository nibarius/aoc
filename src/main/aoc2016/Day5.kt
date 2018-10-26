package aoc2016

import java.math.BigInteger
import java.security.MessageDigest

class Day5(val input: String) {
    private fun String.md5(): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
    }

    fun solvePart1(): String {
        var pwd = ""
        var index = 0
        while (pwd.length < 8) {
            val hash = "$input${index++}".md5()
            if (hash.startsWith("00000")) {
                pwd += hash[5]
            }
            if (index % 1000000 == 0) {
                println("index: $index, pwd: $pwd")
            }
        }
        return pwd
    }


    fun solvePart2(): String {
        val pwd = charArrayOf('_', '_', '_', '_', '_', '_', '_', '_')
        var index = 0
        var found = 0
        while (found < 8) {
            val hash = "$input${index++}".md5()
            if (hash.startsWith("00000") && hash[5] in '0'..'7' && pwd[hash[5].toString().toInt()] == '_') {
                pwd[hash[5].toString().toInt()] = hash[6]
                found++
                println("index: $index, pwd: ${pwd.joinToString("")}")
            }
        }
        return pwd.joinToString("")
    }

}