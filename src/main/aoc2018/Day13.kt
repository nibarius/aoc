package aoc2018

import Direction
import Pos
import next

class Day13(input: List<String>) {

    enum class Turn {
        LEFT,
        STRAIGHT,
        RIGHT
    }

    data class Path(val exits: List<Direction>)
    data class Cart(var pos: Pos, var comingFrom: Direction) {
        var removed = false
        private var nextTurn = Turn.LEFT
        fun move(map: Map<Pos, Path>) {
            val possibleDirections = map.getValue(pos).exits.filterNot { it == comingFrom }
            if (possibleDirections.size == 1) {
                moveInDirection(possibleDirections.first())
            } else {
                when (nextTurn) {
                    Turn.LEFT -> moveInDirection(comingFrom.turnRight())
                    Turn.STRAIGHT -> moveInDirection(comingFrom.opposite())
                    Turn.RIGHT -> moveInDirection(comingFrom.turnLeft())
                }
                nextTurn = nextTurn.next()
            }
        }

        private fun moveInDirection(direction: Direction) {
            pos = Pos(pos.x + direction.dx, pos.y + direction.dy)
            comingFrom = direction.opposite()
        }
    }

    private val map: Map<Pos, Path>
    private val carts: List<Cart>

    init {
        val map = mutableMapOf<Pos, Path>()
        val carts = mutableListOf<Cart>()
        for (y in input.indices) {
            for (x in input[y].indices) {
                when (input[y][x]) {
                    '-' -> map[Pos(x, y)] = Path(listOf(Direction.Left, Direction.Right))
                    '|' -> map[Pos(x, y)] = Path(listOf(Direction.Up, Direction.Down))
                    '+' -> map[Pos(x, y)] = Path(listOf(Direction.Up, Direction.Left, Direction.Down, Direction.Right))
                    '/' -> {
                        if (y > 0 && listOf('|', '+').any { it == input[y - 1][x] }) {
                            map[Pos(x, y)] = Path(listOf(Direction.Up, Direction.Left))
                        } else {
                            map[Pos(x, y)] = Path(listOf(Direction.Down, Direction.Right))
                        }
                    }
                    '\\' -> {
                        if (y > 0 && listOf('|', '+').any { it == input[y - 1][x] }) {
                            map[Pos(x, y)] = Path(listOf(Direction.Up, Direction.Right))
                        } else {
                            map[Pos(x, y)] = Path(listOf(Direction.Down, Direction.Left))
                        }
                    }
                    '^' -> {
                        map[Pos(x, y)] = Path(listOf(Direction.Up, Direction.Down))
                        carts.add(Cart(Pos(x, y), Direction.Down))
                    }
                    'v' -> {
                        map[Pos(x, y)] = Path(listOf(Direction.Up, Direction.Down))
                        carts.add(Cart(Pos(x, y), Direction.Up))
                    }
                    '<' -> {
                        map[Pos(x, y)] = Path(listOf(Direction.Left, Direction.Right))
                        carts.add(Cart(Pos(x, y), Direction.Right))
                    }
                    '>' -> {
                        map[Pos(x, y)] = Path(listOf(Direction.Left, Direction.Right))
                        carts.add(Cart(Pos(x, y), Direction.Left))
                    }
                }
            }
        }
        this.map = map
        this.carts = carts
    }

    private fun runUntilCollision(): String {
        var tick = 0
        while (true) {
            tick++
            carts.sortedWith(compareBy({ it.pos.y }, { it.pos.x })).forEach { cart ->
                cart.move(map)
                if (carts.filter { it.pos == cart.pos }.size > 1) {
                    return "${cart.pos.x},${cart.pos.y}"
                }
            }

        }
    }

    private fun runUntilOnlyOneCartRemaining(): String {
        var tick = 0
        while (true) {
            tick++
            val liveCars = carts.filterNot { it.removed }.sortedWith(compareBy({ it.pos.y }, { it.pos.x }))
            liveCars.forEach { cart ->
                cart.move(map)
                val cartsOnSamePos = liveCars.filter { it.pos == cart.pos }
                if (cartsOnSamePos.size > 1) {
                    cartsOnSamePos.forEach { it.removed = true }
                }
            }
            if (carts.filterNot { it.removed }.size == 1) {
                return liveCars.first { !it.removed }.pos.let { "${it.x},${it.y}" }
            }
        }
    }

    fun solvePart1(): String {
        return runUntilCollision()
    }

    fun solvePart2(): String {
        return runUntilOnlyOneCartRemaining()
    }
}