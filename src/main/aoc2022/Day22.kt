package aoc2022

import AMap
import Direction
import Pos

class Day22(input: List<String>) {

    val map = AMap.parse(input.first().split("\n"), listOf(' '))
    val path = parsePath(input.last())
    val start = map.keys.filter { it.y == 0 && map[it] == '.' }.minBy { it.x }

    private fun parsePath(raw: String): List<Pair<Int, Direction>> {
        val sb = StringBuilder()
        val ret = mutableListOf<Pair<Int, Direction>>()
        raw.forEach { ch ->
            if (ch in listOf('L', 'R')) {
                val distance = sb.toString().toInt()
                sb.clear()
                val dir = Direction.fromChar(ch)
                ret.add(distance to dir)
            } else {
                sb.append(ch)
            }
        }
        // There is no turn after the last movement, but insert a dummy turn to always have a move-turn combination.
        ret.add(sb.toString().toInt() to Direction.Left)
        return ret
    }

    private fun Pos.withWraparound(
        facing: Direction,
        wrapStrategy: (Pos, Direction) -> Pair<Pos, Direction>
    ): Pair<Pos, Direction> {
        return if (this in map) {
            this to facing
        } else {
            //Wrap around
            val (next, nextFacing) = wrapStrategy(this, facing)
            next to nextFacing
        }
    }

    private fun wrapAroundPartOne(pos: Pos, facing: Direction): Pair<Pos, Direction> {
        with(pos) {
            val dir = when (facing) {
                Direction.Up -> Pos(x, map.yRange().reversed().first { Pos(x, it) in map })
                Direction.Right -> Pos(map.xRange().first { Pos(it, y) in map }, y)
                Direction.Down -> Pos(x, map.yRange().first { Pos(x, it) in map })
                Direction.Left -> Pos(map.xRange().reversed().first { Pos(it, y) in map }, y)
            }
            return dir to facing
        }
    }

    /**
     * Example input:
     *   1
     * 234
     *   56
     */
    private fun wrapAroundPartTwoExample(pos: Pos, facing: Direction): Pair<Pos, Direction> {
        with(pos) {
            return when {
                // wrap around 1 to 3 edge
                x == 7 && y in 0..3 && facing == Direction.Left -> Pos(4 + y, 4) to Direction.Down
                x in 4..7 && y == 3 && facing == Direction.Up -> Pos(8, x - 4) to Direction.Right
                // wrap around 1 to 2 edge
                x in 8..11 && y == -1 && facing == Direction.Up -> Pos(11 - x, 4) to Direction.Down
                x in 0..3 && y == 3 && facing == Direction.Up -> Pos(11 - x, 0) to Direction.Down
                // wrap around 1 to 6 edge
                x == 12 && y in 0..3 && facing == Direction.Right -> Pos(15, 11 - y) to Direction.Left
                x in 8..11 && y == 16 && facing == Direction.Right -> Pos(11, 11 - y) to Direction.Left
                // wrap around the 2 to 6 edge
                x == -1 && y in 4..7 && facing == Direction.Left -> Pos(19 - y, 11) to Direction.Up
                x in 12..16 && y == 12 && facing == Direction.Down -> Pos(0, 19 - x) to Direction.Right
                // wrap around the 2 to 5 edge
                x in 0..3 && y == 8 && facing == Direction.Down -> Pos(11 - x, 15) to Direction.Up
                x in 8..11 && y == 12 && facing == Direction.Down -> Pos(11 - x, 7) to Direction.Up
                // wrap around the 3 to 5 edge
                x in 4..7 && y == 8 && facing == Direction.Down -> Pos(8, 15 - x) to Direction.Right
                x == 7 && y in 8..11 && facing == Direction.Left -> Pos(15 - y, 7) to Direction.Up
                // wrap around the 4 to 6 edge
                x == 12 && y in 4..7 && facing == Direction.Right -> Pos(19 - y, 8) to Direction.Down
                x in 12..15 && y == 7 && facing == Direction.Up -> Pos(11, 19 - x) to Direction.Left
                else -> error("should not happen, x: $x, y: $y, facing: $facing")
            }
        }
    }

    /**
     * My Input:
     *  12
     *  3
     * 45
     * 6
     */
    private fun wrapAroundPartTwoRealInput(pos: Pos, facing: Direction): Pair<Pos, Direction> {
        with(pos) {
            return when {
                // wrap around 1 to 4 edge
                x == 49 && y in 0..49 && facing == Direction.Left -> Pos(0, 149 - y) to Direction.Right
                x == -1 && y in 100..149 && facing == Direction.Left -> Pos(50, 149 - y) to Direction.Right
                // wrap around 1 to 6 edge
                x in 50..99 && y == -1 && facing == Direction.Up -> Pos(0, 100 + x) to Direction.Right
                x == -1 && y in 150..199 && facing == Direction.Left -> Pos(y - 100, 0) to Direction.Down
                // wrap around 2 to 6 edge
                x in 100..149 && y == -1 && facing == Direction.Up -> Pos(x - 100, 199) to Direction.Up
                x in 0..59 && y == 200 && facing == Direction.Down -> Pos(100 + x, 0) to Direction.Down
                // Wrap around 2 to 5 edge
                x == 150 && y in 0..49 && facing == Direction.Right -> Pos(99, 149 - y) to Direction.Left
                x == 100 && y in 100..149 && facing == Direction.Right -> Pos(149, 149 - y) to Direction.Left
                // Wrap around 2 to 3 edge
                x in 100..149 && y == 50 && facing == Direction.Down -> Pos(99, x - 50) to Direction.Left
                x == 100 && y in 50..99 && facing == Direction.Right -> Pos(y + 50, 49) to Direction.Up
                // Wrap around 3 to 4 edge
                x == 49 && y in 50..99 && facing == Direction.Left -> Pos(y - 50, 100) to Direction.Down
                x in 0..49 && y == 99 && facing == Direction.Up -> Pos(50, 50 + x) to Direction.Right
                // Wrap around 5 to 6 edge
                x in 50..99 && y == 150 && facing == Direction.Down -> Pos(49, 100 + x) to Direction.Left
                x == 50 && y in 150..199 && facing == Direction.Right -> Pos(y - 100, 149) to Direction.Up

                else -> error("should not happen, x: $x, y: $y, facing: $facing")
            }
        }
    }

    private fun Direction.value(): Int = when (this) {
        Direction.Right -> 0
        Direction.Down -> 1
        Direction.Left -> 2
        Direction.Up -> 3
    }

    private fun simulate(wrapStrategy: (Pos, Direction) -> Pair<Pos, Direction>): Int {
        var currentPos = start
        var facing = Direction.Right
        path.forEach { (steps, turn) ->
            // Move
            for (i in 1..steps) {
                val (next, nextFacing) = currentPos.move(facing).withWraparound(facing, wrapStrategy)
                if (map[next] == '#') {
                    break // Hit a wall, stop moving
                }
                currentPos = next
                facing = nextFacing
            }
            // And then turn
            facing = facing.turn(turn)
        }
        // There is a dummy left turn as the last move, so to get the true final direction we need to turn right
        val lastFacing = facing.turnRight()
        return (currentPos.y + 1) * 1000 + (currentPos.x + 1) * 4 + lastFacing.value()
    }

    fun solvePart1(): Int {
        return simulate(::wrapAroundPartOne)
    }

    // Instead of finding a generic way of folding any kind of paper into a cube, just manually list all the warping
    // rules for the example input and the real input.
    fun solvePart2(realInput: Boolean = false): Int {
        return simulate(if (realInput) ::wrapAroundPartTwoRealInput else ::wrapAroundPartTwoExample)
    }
}