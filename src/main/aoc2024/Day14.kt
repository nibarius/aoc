package aoc2024

import AMap
import Pos
import java.awt.Color

class Day14(input: List<String>, val w: Int = 101, private val h: Int = 103) {
    private val robots = input.map {
        val regex = """p=(\d+),(\d+) v=(-?\d+),(-?\d+)""".toRegex()
        val (px, py, vx, vy) = regex.find(it)!!.destructured
        Robot(Pos(px.toInt(), py.toInt()), Pos(vx.toInt(), vy.toInt()), w, h)
    }

    private data class Robot(val p: Pos, val v: Pos, val w: Int, val h: Int) {
        fun posAt(t: Int): Pos {
            return Pos(p.x + v.x * t, p.y + v.y * t).wrapWithin(w, h)
        }
    }

    private fun Pos.inQuadrant(): Int {
        return when {
            x < w / 2 && y < h / 2 -> 1
            x > w / 2 && y < h / 2 -> 2
            x < w / 2 && y > h / 2 -> 3
            x > w / 2 && y > h / 2 -> 4
            else -> 0
        }
    }

    fun solvePart1(): Int {
        return robots.mapNotNull { it.posAt(100).inQuadrant().takeIf { q -> q != 0 } }
            .groupBy { it }.values
            .map { it.size }
            .reduce(Int::times)
    }

    fun solvePart2(): Int {
        repeat(10000) { time ->
            val positions = robots.map { it.posAt(time) }.toSet()

            // There's a tree present if many robots have other robots on all it's sides
            val treeFound = positions
                .filter { it.allNeighbours().all { n -> n in positions } }
                .size > 10

            if (treeFound) {
                if (false) {
                    // don't generate the visualization normally, but keep the code
                    // so that it can be found when searching for usages of the drawPng function
                    val map = AMap()
                    positions.forEach { map[it] = '*' }
                    map.drawPng(mapOf('*' to Color.GREEN.rgb), "2024-day14-part2.png")
                }
                return time
            }
        }
        return -1
    }
}