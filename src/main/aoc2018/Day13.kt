package aoc2018

import next
import prev

class Day13(input: List<String>) {
    data class Pos(val x: Int, val y: Int) {
        override fun toString(): String {
            return "$x,$y"
        }
    }

    enum class Side(val dx: Int, val dy: Int) {
        UP(0, -1),
        LEFT(-1, 0),
        DOWN(0, 1),
        RIGHT(1, 0)
    }

    enum class Turn {
        LEFT,
        STRAIGHT,
        RIGHT
    }

    data class Path(val exits: List<Side>)
    data class Cart(var pos: Pos, var comingFrom: Side) {
        var removed = false
        var nextTurn = Turn.LEFT
        fun move(map: Map<Pos, Path>) {
            val possibleDirections = map[pos]!!.exits.filterNot { it == comingFrom }
            if (possibleDirections.size == 1) {
                moveInDirection(possibleDirections.first())
            } else {
                when (nextTurn) {
                    Turn.LEFT -> moveInDirection(comingFrom.prev())
                    Turn.STRAIGHT -> moveInDirection(comingFrom.next().next())
                    Turn.RIGHT -> moveInDirection(comingFrom.next())
                }
                nextTurn = nextTurn.next()
            }
        }

        private fun moveInDirection(direction: Side) {
            pos = Pos(pos.x + direction.dx, pos.y + direction.dy)
            comingFrom = direction.next().next()
        }
    }

    private val map: Map<Pos, Path>
    private val carts: List<Cart>

    init {
        val map = mutableMapOf<Pos, Path>()
        val carts = mutableListOf<Cart>()
        for (y in 0 until input.size) {
            for (x in 0 until input[y].length) {
                when (input[y][x]) {
                    '-' -> map[Pos(x, y)] = Path(listOf(Side.LEFT, Side.RIGHT))
                    '|' -> map[Pos(x, y)] = Path(listOf(Side.UP, Side.DOWN))
                    '+' -> map[Pos(x, y)] = Path(listOf(Side.UP, Side.LEFT, Side.DOWN, Side.RIGHT))
                    '/' -> {
                        if (y > 0 && listOf('|', '+').any { it == input[y - 1][x] }) {
                            map[Pos(x, y)] = Path(listOf(Side.UP, Side.LEFT))
                        } else {
                            map[Pos(x, y)] = Path(listOf(Side.DOWN, Side.RIGHT))
                        }
                    }
                    '\\' -> {
                        if (y > 0 && listOf('|', '+').any { it == input[y - 1][x] }) {
                            map[Pos(x, y)] = Path(listOf(Side.UP, Side.RIGHT))
                        } else {
                            map[Pos(x, y)] = Path(listOf(Side.DOWN, Side.LEFT))
                        }
                    }
                    '^' -> {
                        map[Pos(x, y)] = Path(listOf(Side.UP, Side.DOWN))
                        carts.add(Cart(Pos(x, y), Side.DOWN))
                    }
                    'v' -> {
                        map[Pos(x, y)] = Path(listOf(Side.UP, Side.DOWN))
                        carts.add(Cart(Pos(x, y), Side.UP))
                    }
                    '<' -> {
                        map[Pos(x, y)] = Path(listOf(Side.LEFT, Side.RIGHT))
                        carts.add(Cart(Pos(x, y), Side.RIGHT))
                    }
                    '>' -> {
                        map[Pos(x, y)] = Path(listOf(Side.LEFT, Side.RIGHT))
                        carts.add(Cart(Pos(x, y), Side.LEFT))
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
                    return cart.pos.toString()
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
                return liveCars.first{!it.removed}.pos.toString()
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