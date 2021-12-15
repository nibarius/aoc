package aoc2021

import AMap
import Pos
import ShortestPath

class Day15(input: List<String>) {

    val map = AMap.parse(input)
    val start = Pos(0, 0)
    val end = Pos(map.xRange().last, map.yRange().last)

    fun solvePart1(): Int {
        val pathfinder = ShortestPath(('0'..'9').toList())
        val path = pathfinder.find(map, start, end) { pos -> map[pos]!!.digitToInt() }
        return path.sumOf { map[it]!!.digitToInt() }
    }

    fun solvePart2(): Int {
        val realMap = AMap()
        map.keys.forEach { pos ->
            for (y in 0..4) {
                for (x in 0..4) {
                    realMap[Pos(pos.x + x * map.xRange().count(), pos.y + y * map.yRange().count())] =
                        (((map[pos]!!.digitToInt() + x + y - 1) % 9) + 1).digitToChar()
                }
            }
        }
        val pathfinder = ShortestPath(('0'..'9').toList())
        val realEnd = Pos(realMap.xRange().last, realMap.yRange().last)
        val path = pathfinder.find(realMap, start, realEnd) { pos -> realMap[pos]!!.digitToInt() }
        return path.sumOf { realMap[it]!!.digitToInt() }
    }
}