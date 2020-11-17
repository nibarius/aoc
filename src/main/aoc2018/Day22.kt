package aoc2018

import java.util.*
import kotlin.math.abs

class Day22(input: List<String>) {
    enum class Type(val risk: Int) {
        ROCKY(0),
        WET(1),
        NARROW(2);

        companion object {
            fun typeForErosionLevel(level: Int) = when (level % 3) {
                0 -> ROCKY
                1 -> WET
                2 -> NARROW
                else -> throw RuntimeException("invalid terrain type")
            }
        }
    }

    enum class Equipment {
        NONE,
        TORCH,
        CLIMBING_GEAR;

        fun canTraverse(type: Type): Boolean {
            return when (this) {
                NONE -> type != Type.ROCKY
                TORCH -> type != Type.WET
                CLIMBING_GEAR -> type != Type.NARROW
            }
        }
    }

    data class Pos(val x: Int, val y: Int)

    private fun geologicIndex(pos: Pos): Int {
        return when {
            pos.x == 0 && pos.y == 0 -> 0
            pos.y == 0 -> pos.x * 16807
            pos.x == 0 -> pos.y * 48271
            pos == target -> 0
            else -> {
                val pos1 = Pos(pos.x - 1, pos.y)
                val pos2 = Pos(pos.x, pos.y - 1)
                if (erosionLevels[pos1] == null) {
                    erosionLevels[pos1] = erosionLevel(pos1)
                }
                if (erosionLevels[pos2] == null) {
                    erosionLevels[pos2] = erosionLevel(pos2)
                }
                erosionLevels[pos1]!! * erosionLevels[pos2]!!
            }
        }
    }

    private fun erosionLevel(pos: Pos) = (geologicIndex(pos) + depth) % 20183
    private fun type(pos: Pos) = Type.typeForErosionLevel(erosionLevel(pos))


    private val target: Pos
    private val depth: Int = input[0].substringAfter(" ").toInt()
    private val erosionLevels = mutableMapOf<Pos, Int>() //position to erosion level

    init {
        target = Pos(input[1].substringAfter(" ").substringBefore(",").toInt(),
                input[1].substringAfter(",").toInt())
        for (y in 0..target.y) {
            for (x in 0..target.x) {
                val currentPos = Pos(x, y)
                erosionLevels[currentPos] = erosionLevel(currentPos)
            }
        }
    }

    @Suppress("unused")
    fun printMap(maxX: Int, maxY: Int) {
        for (y in 0..maxX) {
            for (x in 0..maxY) {
                when (type(Pos(x, y))) {
                    Type.ROCKY -> print('.')
                    Type.WET -> print('=')
                    Type.NARROW -> print('|')
                }
            }
            println()
        }
    }

    fun solvePart1(): Int {
        return erosionLevels.keys
                .filter { it.x <= target.x && it.y <= target.y }
                .sumBy { type(it).risk }
    }

    fun solvePart2(): Int {
        return find(State(Pos(0, 0), Equipment.TORCH, 0), State(target, Equipment.TORCH))
    }


    // Order of directions specifies which is preferred when several paths is shortest
    private enum class Dir(val dx: Int, val dy: Int) {
        UP(0, -1),
        LEFT(-1, 0),
        RIGHT(1, 0),
        DOWN(0, 1);

        fun from(pos: Pos) = Pos(pos.x + dx, pos.y + dy)
    }

    private fun availableNeighbours(pos: State): List<Pos> {
        return Dir.values().map { dir -> dir.from(pos.currentPos) }
                .filter { it.x >= 0 && it.y >= 0 }
                .filter { newPos -> pos.equipment.canTraverse(type(newPos)) }
    }


    private data class State(val currentPos: Pos, val equipment: Equipment) {
        var timeSpent = 0

        constructor(currentPos: Pos, equipment: Equipment, timeSpent: Int) : this(currentPos, equipment) {
            this.timeSpent = timeSpent
        }
    }

    private fun find(from: State, to: State): Int {
        val toCheck = PriorityQueue(compareBy<State> { state ->
            // Estimate of the minimum required time to get to the target from current position
            // This heuristic speeds up finding the target with 4x
            state.timeSpent +
                    abs(state.currentPos.x - to.currentPos.x) +
                    abs(state.currentPos.y - to.currentPos.y) +
                    (if (state.equipment == to.equipment) 0 else 7)
        })

        toCheck.add(from)
        val alreadyChecked = mutableSetOf<State>()
        while (toCheck.isNotEmpty()) {
            val current = toCheck.remove()!!
            if (current == to) return current.timeSpent
            if (alreadyChecked.contains(current)) continue

            alreadyChecked.add(current)
            // Queue moving to a neighbour without changing equipment
            availableNeighbours(current)
                    .forEach { neighbour -> toCheck.add(State(neighbour, current.equipment, current.timeSpent + 1)) }
            // Queue changing equipment without moving
            Equipment.values()
                    .filter { equipment -> equipment != current.equipment }
                    .filter { equipment -> equipment.canTraverse(type(current.currentPos)) }
                    .forEach { toCheck.add(State(current.currentPos, it, current.timeSpent + 7)) }
        }
        return -1
    }
}