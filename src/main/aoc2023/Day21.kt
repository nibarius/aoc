package aoc2023

import AMap
import Pos
import Visualization
import java.awt.Color

class Day21(input: List<String>) {

    val tmp = """
        .................................
        .....###.#......###.#......###.#.
        .###.##..#..###.##..#..###.##..#.
        ..#.#...#....#.#...#....#.#...#..
        ....#.#........#.#........#.#....
        .##...####..##...####..##...####.
        .##..#...#..##..#...#..##..#...#.
        .......##.........##.........##..
        .##.#.####..##.#.####..##.#.####.
        .##..##.##..##..##.##..##..##.##.
        .................................
        .................................
        .....###.#......###.#......###.#.
        .###.##..#..###.##..#..###.##..#.
        ..#.#...#....#.#...#....#.#...#..
        ....#.#........#.#........#.#....
        .##...####..##..S####..##...####.
        .##..#...#..##..#...#..##..#...#.
        .......##.........##.........##..
        .##.#.####..##.#.####..##.#.####.
        .##..##.##..##..##.##..##..##.##.
        .................................
        .................................
        .....###.#......###.#......###.#.
        .###.##..#..###.##..#..###.##..#.
        ..#.#...#....#.#...#....#.#...#..
        ....#.#........#.#........#.#....
        .##...####..##...####..##...####.
        .##..#...#..##..#...#..##..#...#.
        .......##.........##.........##..
        .##.#.####..##.#.####..##.#.####.
        .##..##.##..##..##.##..##..##.##.
        .................................
    """.trimIndent().split("\n")

    private val garden = AMap.parse(input, listOf('#'))
    private val garden2 = AMap.parse(input, listOf())
    private val garden3 = AMap.parse(tmp, listOf())
    private val startPos = garden.positionOf('S')

    private fun countReachablePlots(steps: Int): Int {
        var currentPos = setOf(startPos)
        val viz = Visualization(10)
        repeat(steps) {
            currentPos = currentPos.flatMap { it.allNeighbours() }.filter { it in garden.keys }.toSet()
            val tmpMap = garden2.copy().apply { currentPos.forEach { this[it] = 'O' } }
            viz.drawPng(
                tmpMap,
                mapOf(
                    'O' to Color.GREEN.rgb,
                    '#' to Color.BLACK.rgb,
                    '.' to Color.WHITE.rgb,
                    'S' to Color.LIGHT_GRAY.rgb
                ),
                "tmp$it.png"
            )
        }
        return currentPos.size
    }

    private fun wrappedPos(pos: Pos, size: Int): Pos {
        val newX = pos.x % size + if (pos.x < 0) size else 0
        val newY = pos.y % size + if (pos.y < 0) size else 0
        return Pos(newX % size, newY % size)
    }

    private fun makeBiggerGarden(): AMap {
        val size = garden.xRange().count()
        val ret = AMap()
        for (x in -3..3) {
            for (y in -3..3) {
                garden.keys.forEach { pos ->
                    ret[Pos(x * size + pos.x, y * size + pos.y)] = garden[pos]!!
                }
            }
        }
        return ret
    }

    private fun countReachablePlots2(steps: Int): Int {
        //val startPos = garden3.positionOf('S')
        val reachable = listOf(mutableSetOf(startPos), mutableSetOf())
        var frontier = setOf(startPos)
        val viz = Visualization(10)
        val bigGarden = makeBiggerGarden()
        repeat(steps) { step ->
            val nextOffset = (step + 1) % 2
            frontier = frontier
                .flatMap { it.allNeighbours().filter { neighbour -> neighbour !in reachable[nextOffset] } }
                .filter {
                    wrappedPos(
                        it,
                        garden.xRange().count()
                    ) in garden.keys
                } // to filter out rocks
                .toSet()
            /*val tmpMap = bigGarden.copy().apply {
                reachable[nextOffset].forEach { this[it] = 'O' }
                frontier.forEach { this[it] = 'F' }
            }
            viz.drawPng(
                tmpMap,
                mapOf(
                    'O' to Color.GREEN.rgb,
                    'F' to Color.RED.rgb,
                    '#' to Color.BLACK.rgb,
                    '.' to Color.WHITE.rgb,
                    'S' to Color.WHITE.rgb
                ),
                "tmp$step.png"
            )*/
            reachable[nextOffset].addAll(frontier)//todo: I'm one step ahead all the time (working on step+1 instead of step)
            println("${step+1},${reachable[nextOffset].size}")
        }
        return reachable[steps % 2].size
    }

    fun solvePart1(stepsLeft: Int = 64): Int {
        return countReachablePlots(stepsLeft)
    }

    fun solvePart2(stepsLeft: Int = 26501365): Int {
        return countReachablePlots2(stepsLeft)
    }
}