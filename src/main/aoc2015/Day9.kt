package aoc2015

import java.util.*
import kotlin.math.max

class Day9(input: List<String>) {

    val parsedInput = parseInput(input)

    private fun initMapIfNeeded(key: String, map: MutableMap<String, MutableMap<String, Int>>) {
        if (!map.containsKey(key)) {
            map[key] = mutableMapOf()
        }
    }

    fun parseInput(input: List<String>): Map<String, Map<String, Int>> {
        val ret = mutableMapOf<String, MutableMap<String, Int>>()
        input.forEach { line ->
            val parts = line.split(" ")
            val a = parts[0]
            val b = parts[2]
            val distance = parts.last().toInt()
            initMapIfNeeded(a, ret)
            initMapIfNeeded(b, ret)
            ret[a]!![b] = distance
            ret[b]!![a] = distance
        }
        return ret
    }

    data class State(val visited: List<String>) {
        var distance = 0
    }

    private fun findDistance(shortest: Boolean): Int {
        val toCheck = PriorityQueue<State>(compareBy { state -> state.distance })
        parsedInput.keys.forEach { toCheck.add(State(listOf(it))) }
        var longest = -1
        while (toCheck.isNotEmpty()) {
            val current = toCheck.remove()
            if (current.visited.size == parsedInput.size) {
                if (shortest) return current.distance
                longest = max(current.distance, longest)
                continue
            }
            parsedInput.filterKeys { !current.visited.contains(it) }
                    .forEach { (nextCity, distances) ->
                        val dist = current.distance + distances[current.visited.last()]!!
                        val visited = current.visited.toMutableList().apply { add(nextCity) }
                        toCheck.add(State(visited).apply { distance = dist })
                    }
        }
        return longest
    }

    fun solvePart1(): Int {
        return findDistance(true)
    }

    fun solvePart2(): Int {
        return findDistance(false)
    }
}