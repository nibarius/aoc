package aoc2020

import AMap
import Pos
import toDirection

class Day24(input: List<String>) {

    // Represent the hex grid as a normal 2 dimensional map (there are just some limits on adjacency)
    val map = AMap().apply {
        input.forEach { line ->
            val pos = parse(line)
            val current = this.getOrDefault(pos, 'W')
            this[pos] = if (current == 'W') 'B' else 'W'
        }
    }

    private fun Pos.allHexNeighbours(): List<Pos> {
        // tiles at (-1, -1) and (1, 1) are not adjacent to (0,0), all other are
        return Pos.allDeltas(true)
                .subtract(setOf(Pos(-1, -1), Pos(1, 1)))
                .map { delta -> Pos(x + delta.x, y + delta.y) }
    }

    private fun parse(line: String): Pos {
        val iterator = line.iterator()
        var pos = Pos(0, 0)
        while (iterator.hasNext()) {
            when (val char = iterator.nextChar()) {
                'e', 'w' -> pos = pos.move(char.toDirection()) // e, w ==> x +,- 1
                's', 'n' -> {
                    pos = pos.move(char.toDirection()) // s, n ==> y +,- 1
                    val next = iterator.nextChar()
                    if ("$char$next" in listOf("sw", "ne")) { // sw, ne additionally moves x +,- 1
                        pos = pos.move(next.toDirection())
                    }
                }
            }
        }
        return pos
    }

    private fun rearrange() {
        repeat(100) {
            val copy = map.copy()
            val black = copy.toMap().filterValues { it == 'B' }
            val xr = black.minByOrNull { it.key.x }!!.key.x - 1..black.maxByOrNull { it.key.x }!!.key.x + 1
            val yr = black.minByOrNull { it.key.y }!!.key.y - 1..black.maxByOrNull { it.key.y }!!.key.y + 1
            for (y in yr) {
                for (x in xr) {
                    val pos = Pos(x, y)
                    val current = copy.getOrDefault(pos, 'W')
                    val blackNeighbours = pos.allHexNeighbours().count { copy[it] == 'B' }
                    when {
                        current == 'W' && blackNeighbours == 2 -> map[pos] = 'B'
                        current == 'B' && (blackNeighbours == 0 || blackNeighbours > 2) -> map[pos] = 'W'
                    }
                }
            }
        }
    }

    fun solvePart1(): Int {
        return map.values.count { it == 'B' }
    }

    fun solvePart2(): Int {
        rearrange()
        return map.values.count { it == 'B' }
    }
}