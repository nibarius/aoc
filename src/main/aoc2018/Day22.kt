package aoc2018

import Pos
import Search
import Search.WeightedGraph

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

        companion object {
            fun equipmentFor(type: Type): List<Equipment> {
                return when (type) {
                    Type.ROCKY -> listOf(CLIMBING_GEAR, TORCH)
                    Type.WET -> listOf(CLIMBING_GEAR, NONE)
                    Type.NARROW -> listOf(TORCH, NONE)
                }
            }
        }
    }

    data class State(val currentPos: Pos, val equipment: Equipment)

    class Graph(private val depth: Int, private val target: Pos) : WeightedGraph<State> {

        override fun neighbours(id: State): List<State> {
            return buildList {
                // Move to adjacent position without changing equipment
                id.currentPos.allNeighbours()
                    .forEach {
                        if (it.x >= 0 && it.y >= 0 && id.equipment.canTraverse(type(it))) {
                            add(State(it, id.equipment))
                        }
                    }

                // Change equipment without moving
                Equipment.equipmentFor(type(id.currentPos))
                    .forEach { equipment ->
                        if (equipment != id.equipment) {
                            add(State(id.currentPos, equipment))
                        }
                    }
            }
        }
        
        // cost 1 to move, cost 7 to change equipment
        override fun cost(from: State, to: State): Float {
            return if (from.equipment == to.equipment) 1f else 7f
        }

        private val erosionLevels = mutableMapOf<Pos, Int>() //position to erosion level

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

        private val typeCache = mutableMapOf<Pos, Type>()
        private fun type(pos: Pos): Type {
            val cached = typeCache[pos]
            if (cached == null) {
                val type = Type.typeForErosionLevel(erosionLevel(pos))
                typeCache[pos] = type
                return type

            }
            return cached
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

        fun totalRisk(): Int {
            for (y in 0..target.y) {
                for (x in 0..target.x) {
                    val currentPos = Pos(x, y)
                    erosionLevels[currentPos] = erosionLevel(currentPos)
                }
            }

            return erosionLevels.keys
                .filter { it.x <= target.x && it.y <= target.y }
                .sumOf { type(it).risk }
        }
    }

    private val target = Pos(
        input[1].substringAfter(" ").substringBefore(",").toInt(),
        input[1].substringAfter(",").toInt()
    )
    private val depth: Int = input[0].substringAfter(" ").toInt()
    private val graph = Graph(depth, target)


    fun solvePart1(): Int {
        return graph.totalRisk()
    }

    fun solvePart2(): Int {
        val goal = State(target, Equipment.TORCH)
        return Search.aStar(graph, State(Pos(0, 0), Equipment.TORCH), goal, ::heuristic).cost[goal]!!.toInt()
    }

    private fun heuristic(current: State, goal: State): Float {
        return current.currentPos.distanceTo(goal.currentPos) + if (current.equipment == goal.equipment) 0f else 7f
    }
}