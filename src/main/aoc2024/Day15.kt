package aoc2024

import AMap
import Direction
import Pos
import toDirection

class Day15(input: List<String>) {
    private val map = AMap.parse(input.first().split("\n"))
    private val moves = input.last().split("\n").joinToString("")
        .map { it.toDirection() }

    private fun Pos.gps() = 100 * y + x

    private fun expandPos(map: AMap, pos: Pos, to: String) {
        map[Pos(pos.x * 2, pos.y)] = to.first()
        map[Pos(pos.x * 2 + 1, pos.y)] = to.last()
    }

    private fun expandMap(map: AMap): AMap {
        return AMap().apply {
            map.keys.forEach {
                when (map[it]) {
                    '#' -> expandPos(this, it, "##")
                    'O' -> expandPos(this, it, "[]")
                    '.' -> expandPos(this, it, "..")
                    '@' -> expandPos(this, it, "@.")
                }
            }
        }
    }

    /**
     * Returns true if it's possible to move in the given direction to the given position.
     * This is possible if the destination is empty or if it's possible to push the box
     * on that position one step.
     */
    private fun canMove(map: AMap, to: Pos, dir: Direction): Boolean {
        if (map[to] == '#') return false
        if (map[to] == '.') return true

        return buildList {
            add(to.move(dir))
            when {
                dir.isVertical() && map[to] == '[' -> add(Pos(to.x + 1, to.y).move(dir))
                dir.isVertical() && map[to] == ']' -> add(Pos(to.x - 1, to.y).move(dir))
            }
        }.all { canMove(map, it, dir) }
    }

    /**
     * Move one step in the given direction, recursively push all obstacles in the way.
     * Must not be called when there are things in the way that can't be pushed.
     */
    private fun moveAndPush(map: AMap, from: Pos, dir: Direction) {
        val to = from.move(dir)
        val atDestination = map[to]
        val boxesToPush = buildList {
            if (atDestination != '.') add(to) // need to push what's ahead to move

            // If we're pushing a big box we need to push the other side as well
            if (dir.isVertical() && atDestination == '[') add(to.move(Direction.Right))
            if (dir.isVertical() && atDestination == ']') add(to.move(Direction.Left))
        }

        boxesToPush.forEach {
            moveAndPush(map, it, dir)
            map[it] = '.' // The box ahead has been pushed away, mark it as empty.
        }
        map[to] = map[from]!!
        map[from] = '.'

    }

    /**
     * Tries to move the robot one step and return true if it managed to do so
     */
    private fun moveRobotOneStep(map: AMap, from: Pos, dir: Direction): Boolean {
        return canMove(map, from.move(dir), dir)
            .also { canMove -> if (canMove) moveAndPush(map, from, dir) }
    }

    private fun moveRobot(map: AMap, box: Char): Int {
        var pos = map.positionOf('@')
        for (dir in moves) {
            if (moveRobotOneStep(map, pos, dir)) {
                pos = pos.move(dir)
            }
        }
        return map.keys.filter { map[it] == box }.sumOf { it.gps() }
    }

    fun solvePart1(): Int {
        return moveRobot(map, 'O')
    }

    fun solvePart2(): Int {
        return moveRobot(expandMap(map), '[')
    }
}