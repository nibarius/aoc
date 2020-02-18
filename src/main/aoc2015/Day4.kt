package aoc2015

import md5

class Day4(var input: String) {



    fun solvePart1(): Int {
        return mine(5)
    }

    private fun mine(zeros: Int): Int {
        var i = 0
        val match = "".padStart(zeros, '0')
        while (true) {
            if ("$input$i".md5().startsWith(match)) {
                return i
            }
            i++
        }
    }

    fun solvePart2(): Int {
        return mine(6)
    }
}