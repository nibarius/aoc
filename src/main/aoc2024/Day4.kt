package aoc2024

import AMap
import Pos

class Day4(input: List<String>) {
    private val wordSearch = AMap.parse(input)

    private fun isXmas(start: Pos, direction: Pos): Boolean {
        var currentPos = start
        val xmas = "XMAS"
        for (index in xmas.indices) {
            if (wordSearch[currentPos] != xmas[index]) {
                return false
            }
            currentPos += direction
        }
        return true
    }

    fun solvePart1(): Int {
        val directions = Pos(0, 0).allNeighbours(true)
        return wordSearch.keys.sumOf { pos ->
            directions.count { dir ->
                isXmas(pos, dir)
            }
        }
    }

    private val wordSearch2 = AMap.parse(input, listOf('X'))

    private fun isXmas2(start: Pos): Boolean {
        if (wordSearch2[start] != 'A') {
            return false
        }
        return setOf(
            setOf(start + Pos(-1, -1), start + Pos(1, 1)),
            setOf(start + Pos(1, -1), start + Pos(-1, 1))
        ).all { diagonal ->
            diagonal.map { wordSearch2[it] }
                .containsAll(setOf('M', 'S'))
        }
    }

    private fun find2(): Int {
        return wordSearch2.keys.count { pos ->
            isXmas2(pos)
        }
    }

    fun solvePart2(): Int {
        return find2()
    }
}