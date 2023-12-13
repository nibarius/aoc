package aoc2023

class Day13(input: List<String>) {
    private val patterns2 = input.map { rawPattern ->
        val rows = rawPattern.split("\n")
        val cols = rows.first().indices.map { i ->
            rows.map { it[i] }.joinToString("")
        }
        Pattern(rows, cols)
    }

    // Keeps rows and cols as strings for easy comparing
    private data class Pattern(val rows: List<String>, val cols: List<String>)

    private fun findBoth2(pattern: Pattern, fixSmudge: Boolean): Int {
        val v = findReflectionLine(pattern.cols, fixSmudge)
        if (v != -1) {
            return v
        }
        val h = findReflectionLine(pattern.rows, fixSmudge)
        return 100 * h
    }

    // Global state to keep track if the smudge has been fixed or not during comparison
    private var hasFixedSmudge = false

    private fun findReflectionLine(lines: List<String>, fixSmudge: Boolean): Int {
        val range = lines.indices
        var offset = 0
        while (offset in range) {
            hasFixedSmudge = false
            val candidate = findCandidate(lines, offset, fixSmudge) ?: return -1
            // Check all lines outward from the candidate position until we reach the edge
            // or two lines no longer match
            for (i in range) {
                val a = candidate - i - 1
                val b = candidate + i + 2
                if (a !in range || b !in range) {
                    if (!fixSmudge || hasFixedSmudge) {
                        return candidate + 1
                    }
                    break
                }
                if (!linesMatch(lines[a], lines[b], fixSmudge)) {
                    break
                }
            }
            offset = candidate + 1
        }
        return -1
    }

    // Find two identical lines next to each other. A possible candidate for the reflection line
    private fun findCandidate(lines: List<String>, offset: Int, fixSmudge: Boolean): Int? {
        lines.indices.drop(offset).zipWithNext().forEach { (a, b) ->
            if (linesMatch(lines[a], lines[b], fixSmudge)) {
                return a
            }
        }
        return null
    }

    private fun linesMatch(a: String, b: String, fixSmudge: Boolean): Boolean {
        var differences = 0
        for (i in a.indices) {
            if (a[i] != b[i]) {
                differences++
            }
        }
        if (fixSmudge && !hasFixedSmudge && differences == 1) {
            // Allow the diff and fix the smudge
            hasFixedSmudge = true
            return true
        }
        return differences == 0
    }

    fun solvePart1(): Int {
        return patterns2.sumOf { findBoth2(it, false) }
    }

    fun solvePart2(): Int {
        return patterns2.sumOf { findBoth2(it, true) }
    }
}