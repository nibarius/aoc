package aoc2019

import ShortestPath
import Pos
import AMap
import java.util.*
import kotlin.math.abs

class Day18(input: List<String>) {

    private val initialState: State
    private val map: AMap
    private val doors: Map<Char, Pos>
    private val keys: Map<Char, Pos>

    init {
        val tmpMap = AMap()
        val tmpKeys = mutableMapOf<Char, Pos>() // key to key-pos
        val tmpDoors = mutableMapOf<Char, Pos>() // door to door-pos
        var startPos = Pos(-1, -1)
        for (y in input.indices) {
            for (x in input[y].indices) {
                tmpMap[Pos(x, y)] = input[y][x]
                when {
                    input[y][x] == '@' -> startPos = Pos(x, y)
                    ('a'..'z').contains(input[y][x]) -> tmpKeys[input[y][x]] = Pos(x, y)
                    ('A'..'Z').contains(input[y][x]) -> tmpDoors[input[y][x]] = Pos(x, y)
                }
            }
        }
        map = tmpMap
        doors = tmpDoors
        keys = tmpKeys
        initialState = State(startPos)
    }

    data class State(val pos: Pos, val keysFound: Set<Char> = emptySet()) {
        var steps = 0
    }

    fun find(theMap: AMap): Int {
        val toCheck = PriorityQueue<State>(compareBy { state -> state.steps })
        toCheck.add(initialState)

        val alreadyChecked = mutableSetOf<State>()

        while (toCheck.isNotEmpty()) {
            val currentState = toCheck.remove()
            if (currentState.keysFound.size == keys.size) {
                // All keys found, return length traveled
                return currentState.steps
            }
            if (alreadyChecked.contains(currentState)) {
                continue
            }
            alreadyChecked.add(currentState)

            val remainingKeys = keys.filterNot { currentState.keysFound.contains(it.key) }
            remainingKeys.forEach { (key, position) ->
                val traversable = mutableListOf('.', '@').apply {
                    add(key) // The key we want to go to is traversable
                    addAll(currentState.keysFound)
                    doors.forEach { (door, _) ->
                        if (currentState.keysFound.contains(door.toLowerCase())) {
                            // all doors that we have keys to are traversable
                            add(door)
                        }
                    }
                }
                val len = ShortestPath(traversable).find(theMap, currentState.pos, position).size
                if (len > 0) {
                    // It was possible to find a path to this key, pick it up
                    // and continue searching paths from it
                    toCheck.add(State(position, currentState.keysFound.toMutableSet().apply { add(key) })
                            .apply {
                                steps = currentState.steps + len
                            }
                    )
                }
            }
        }
        return -1
    }

    data class State2(val pos: List<Pos>, val keysFound: Set<Char> = emptySet()) {
        var steps = 0
    }

    fun find2(theMap: AMap, startPos: List<Pos>): Int {

        val toCheck = PriorityQueue<State2>(compareBy { state -> state.steps })
        toCheck.add(State2(startPos))

        val alreadyChecked = mutableSetOf<State2>()

        while (toCheck.isNotEmpty()) {
            val currentState = toCheck.remove()
            if (currentState.keysFound.size == keys.size) {
                // All keys found, return length traveled
                return currentState.steps
            }
            if (alreadyChecked.contains(currentState)) {
                continue
            }
            alreadyChecked.add(currentState)

            for ((robotIndex, robotPos) in currentState.pos.withIndex()) {

                val remainingKeys = keys
                        .filterNot { currentState.keysFound.contains(it.key) }
                        .filter {
                            // Only look at keys in the robot's quadrant (one example allows the robot to go into
                            // the horizontal divider so do a +/- 1 in the y-axis to support this case
                            when (robotIndex) {
                                0 -> { // upper left
                                    it.value.x <= startPos[robotIndex].x && it.value.y <= startPos[robotIndex].y + 1
                                }
                                1 -> { // upper right
                                    it.value.x >= startPos[robotIndex].x && it.value.y <= startPos[robotIndex].y + 1
                                }
                                2 -> { // lower left
                                    it.value.x <= startPos[robotIndex].x && it.value.y >= startPos[robotIndex].y - 1
                                }
                                3 -> { // lower right
                                    it.value.x >= startPos[robotIndex].x && it.value.y >= startPos[robotIndex].y - 1
                                }
                                else -> throw RuntimeException("Unknown robot index: $robotIndex")
                            }
                        }


                remainingKeys.forEach { (key, position) ->
                    val traversable = mutableListOf('.', '@').apply {
                        add(key) // The key we want to go to is traversable
                        addAll(currentState.keysFound)
                        doors.forEach { (door, _) ->
                            if (currentState.keysFound.contains(door.toLowerCase())) {
                                // all doors that we have keys to are traversable
                                add(door)
                            }
                        }
                    }
                    val len = ShortestPath(traversable).find(theMap, robotPos, position).size
                    if (len > 0) {
                        // It was possible to find a path to this key, pick it up
                        // and continue searching paths from it
                        toCheck.add(State2(
                                currentState.pos.toMutableList().apply { this[robotIndex] = position },
                                currentState.keysFound.toMutableSet().apply { add(key) })
                                .apply {
                                    steps = currentState.steps + len
                                }
                        )
                    }
                }
            }
        }
        return -1
    }

    private fun updateMap(map: AMap): Pair<AMap, MutableList<Pos>> {
        val startPos = mutableListOf<Pos>()
        val newMap = map.copy().apply {
            val (x, y) = initialState.pos
            for (dy in -1..1) {
                for (dx in -1..1) {
                    val pos = Pos(x + dx, y + dy)
                    if (abs(dx) == 1 && abs(dy) == 1) {
                        this[pos] = '@'
                        startPos.add(pos)
                    } else {
                        this[pos] = '#'
                    }
                }
            }
        }
        return newMap to startPos
    }

    fun solvePart1(): Int {
        return find(map)
    }

    fun solvePart2(): Int {
        val (newMap, startPos) = updateMap(map)
        return find2(newMap, startPos)
    }
}