package aoc2023

import AMap
import Direction
import Pos

class Day10(input: List<String>) {

    private val map = AMap.parse(input)

    private val start = map.toMap().filterValues { it == 'S' }.keys.single()

    // All points on the right/left hand side of the loop while walking in one direction around the loop
    private val toRight = mutableSetOf<Pos>()
    private val toLeft = mutableSetOf<Pos>()
    // All points that makes up the loop
    private val loop = mutableSetOf<Pos>()

    init {
        // Replace the 'S' with the correct shape
        map[start] = startShape()

        // Walk through the loop to discover all points in the loop and what's to the left/right of the loop
        val facing = exitDirections(start).first()
        var current = start to facing
        do {
            loop.add(current.first)
            val (l, r) = neighbours(current)
            toLeft.addAll(l)
            toRight.addAll(r)
            current = walk(current)
        } while (current.first != start)
        toLeft.removeIf { it in loop || it !in map.keys }
        toRight.removeIf { it in loop || it !in map.keys }
    }

    private fun startShape(): Char {
        // If it's possible to move in each direction from the start position
        val up = map[start.move(Direction.Up)] in listOf('|', '7', 'F')
        val down = map[start.move(Direction.Down)] in listOf('|', 'L', 'J')
        val left = map[start.move(Direction.Left)] in listOf('-', 'L', 'F')
        val right = map[start.move(Direction.Right)] in listOf('-', 'J', '7')
        return when {
            up && down -> '|'
            up && left -> 'J'
            up && right -> 'L'
            down && left -> '7'
            down && right -> 'F'
            left && right -> '-'
            else -> error("invalid start shape")
        }
    }

    /**
     * List the directions that it's possible to exit a room from depending on it's shape
     */
    private fun exitDirections(pos: Pos): List<Direction> {
        return when (map[pos]) {
            '|' -> listOf(Direction.Up, Direction.Down)
            '-' -> listOf(Direction.Left, Direction.Right)
            'L' -> listOf(Direction.Up, Direction.Right)
            'J' -> listOf(Direction.Up, Direction.Left)
            '7' -> listOf(Direction.Down, Direction.Left)
            'F' -> listOf(Direction.Down, Direction.Right)
            else -> listOf()
        }
    }

    fun solvePart1(): Int {
        return loop.size / 2
    }

    private fun walk(from: Pair<Pos, Direction>): Pair<Pos, Direction> {
        val nextRoom = from.first.move(from.second)
        val nextFacing = exitDirections(nextRoom).single { it != from.second.opposite() }
        return nextRoom to nextFacing
    }

    /**
     * @current Current position and facing direction
     * @return a pair of sets. first is the neighbours to the left of the pipe while second is neighbours to the right
     */
    private fun neighbours(current: Pair<Pos, Direction>): Pair<Set<Pos>, Set<Pos>> {
        val pos = current.first
        val neighbours = mapOf(
            Pair('-', Direction.Right) to Pair(setOf(pos.move(Direction.Up)), setOf(pos.move(Direction.Down))),
            Pair('-', Direction.Left) to Pair(setOf(pos.move(Direction.Down)), setOf(pos.move(Direction.Up))),
            Pair('|', Direction.Up) to Pair(setOf(pos.move(Direction.Left)), setOf(pos.move(Direction.Right))),
            Pair('|', Direction.Down) to Pair(setOf(pos.move(Direction.Right)), setOf(pos.move(Direction.Left))),
            Pair('L', Direction.Up) to Pair(setOf(pos.move(Direction.Left), pos.move(Direction.Down)), setOf()),
            Pair('L', Direction.Right) to Pair(setOf(), setOf(pos.move(Direction.Left), pos.move(Direction.Down))),
            Pair('J', Direction.Up) to Pair(setOf(), setOf(pos.move(Direction.Right), pos.move(Direction.Down))),
            Pair('J', Direction.Left) to Pair(setOf(pos.move(Direction.Right), pos.move(Direction.Down)), setOf()),
            Pair('7', Direction.Left) to Pair(setOf(), setOf(pos.move(Direction.Right), pos.move(Direction.Up))),
            Pair('7', Direction.Down) to Pair(setOf(pos.move(Direction.Right), pos.move(Direction.Up)), setOf()),
            Pair('F', Direction.Down) to Pair(setOf(), setOf(pos.move(Direction.Left), pos.move(Direction.Up))),
            Pair('F', Direction.Right) to Pair(setOf(pos.move(Direction.Left), pos.move(Direction.Up)), setOf())
        )
        return neighbours[map[pos] to current.second]!!
    }

    private fun Pos.onBorder(): Boolean {
        return x == 0 || y == 0 || x == map.xRange().last || y == map.yRange().last
    }

    // Do a BFS search to walk from each unknown point outward until a point in
    // either toLeft, or toRight is found. Then assign all visited points to that set
    private fun assignUnknown() {
        val unknown = (map.keys - loop - toLeft - toRight).toMutableSet()
        while (unknown.isNotEmpty()) {
            val toCheck = ArrayDeque<Pos>().apply { add(unknown.first()) }
            val connected = mutableSetOf(unknown.first())
            while (toCheck.isNotEmpty()) {
                val current = toCheck.removeFirst()
                if (current in toLeft) {
                    // all connected are left
                    toLeft.addAll(connected)
                    unknown.removeAll(connected)
                    break
                } else if (current in toRight) {
                    // all connected are right
                    toRight.addAll(connected)
                    unknown.removeAll(connected)
                    break
                }
                current.allNeighbours()
                    .filter { it !in connected }
                    .filter { it in unknown || it in toLeft || it in toRight }
                    .forEach { next ->
                        connected.add(next)
                        toCheck.add(next)
                    }
            }
        }
    }

    fun solvePart2(): Int {
        assignUnknown()
        // The set of positions that touches the border is not enclosed by the loop
        return if (toLeft.any { it.onBorder()} ) toRight.size else toLeft.size

    }
}