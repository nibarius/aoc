package aoc2024

import AMap
import Pos

class Day10(input: List<String>) {

    val map = AMap.parse(input)

    private fun Pos.score(peaks: Set<Pos>, paths: Int): Pair<Set<Pos>, Int> {
        val currentValue = map[this]!!
        if (currentValue == '9') return mutableSetOf(this) + peaks to paths + 1
        return allNeighbours()
            .filter { (map[it] ?: '0') - currentValue == 1 }
            .fold(Pair(peaks, paths)) { acc, pos -> pos.score(acc.first, acc.second) }
    }

    private fun scoreTrailheads(scoringMethod: (Pair<Set<Pos>, Int>) -> Int): Int {
        return map.keys.filter { map[it] == '0' }
            .sumOf { pos -> scoringMethod(pos.score(setOf(), 0)) }
    }

    fun solvePart1(): Int {
        return scoreTrailheads { it.first.size }
    }

    fun solvePart2(): Int {
        return scoreTrailheads { it.second }
    }
}