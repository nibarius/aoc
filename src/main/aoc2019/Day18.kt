package aoc2019

import AMap
import Direction
import Pos
import org.magicwerk.brownies.collections.GapList
import java.util.*
import kotlin.math.abs

class Day18(input: List<String>) {

    private val startPos: Pos
    private val map: AMap
    private val doors: Map<Char, Pos>
    private val keys: Map<Char, Pos>

    init {
        val tmpMap = AMap()
        val tmpKeys = mutableMapOf<Char, Pos>() // key to key-pos
        val tmpDoors = mutableMapOf<Char, Pos>() // door to door-pos
        var tmpStartPos = Pos(-1, -1)
        for (y in input.indices) {
            for (x in input[y].indices) {
                tmpMap[Pos(x, y)] = input[y][x]
                when {
                    input[y][x] == '@' -> tmpStartPos = Pos(x, y)
                    ('a'..'z').contains(input[y][x]) -> tmpKeys[input[y][x]] = Pos(x, y)
                    ('A'..'Z').contains(input[y][x]) -> tmpDoors[input[y][x]] = Pos(x, y)
                }
            }
        }
        map = tmpMap
        doors = tmpDoors
        keys = tmpKeys
        startPos = tmpStartPos
    }

    // Part 2: Update the map to the variant used in Part 2
    private fun updateMap(map: AMap): Pair<AMap, MutableList<Pos>> {
        val tmpStartPos = mutableListOf<Pos>()
        val newMap = map.copy().apply {
            val (x, y) = startPos
            for (dy in -1..1) {
                for (dx in -1..1) {
                    val pos = Pos(x + dx, y + dy)
                    if (abs(dx) == 1 && abs(dy) == 1) {
                        this[pos] = '@'
                        tmpStartPos.add(pos)
                    } else {
                        this[pos] = '#'
                    }
                }
            }
        }
        return newMap to tmpStartPos
    }

    // Part 2: Find which robot a position belongs to based on it's location
    private fun robotForPos(pos: Pos): Int {
        return when {
            pos.x <= startPos.x && pos.y <= startPos.y -> 0 // Upper left
            pos.x >= startPos.x && pos.y <= startPos.y -> 1 // Upper right
            pos.x <= startPos.x && pos.y >= startPos.y -> 2 // Lower left
            pos.x >= startPos.x && pos.y >= startPos.y -> 3 // Right
            else -> throw RuntimeException("Unexpected position: $pos")
        }
    }

    private data class ExplorationState(val currentPos: Pos, val steps: Int, val blockingDoors: Set<Char>)
    private data class PathToKey(val key: Char, val steps: Int, val doors: Set<Char>)

    // Walk trough the whole map from the given position and record the distance and doors passed
    // to each of the keys.
    private fun exploreFrom(map: AMap, initialPos: Pos, traversable: Set<Char>, numKeysToFind: Int): MutableSet<PathToKey> {
        val toCheck = GapList<ExplorationState>()
        toCheck.add(ExplorationState(initialPos, 0, setOf()))
        val alreadyChecked = mutableSetOf<Pos>() // Never move back to a position already visited

        // All the keys found so far, including the interesting data about them
        val keysFound = mutableSetOf<PathToKey>()

        // Only explore the area until all keys are found
        while (toCheck.isNotEmpty() && keysFound.size < numKeysToFind) {
            val current = toCheck.remove()
            val item = map[current.currentPos]!!
            val newDoors = current.blockingDoors.toMutableSet()
            if (alreadyChecked.contains(current.currentPos)) continue
            else if (doors.containsKey(item)) {
                // A door found, keep track of it for all movements from this position
                newDoors.add(item)
            } else if (current.currentPos != initialPos && keys.containsKey(item)) {
                // A key found, record all relevant information about it and continue exploring
                keysFound.add(PathToKey(item, current.steps, current.blockingDoors))
            }
            alreadyChecked.add(current.currentPos)

            // Try moving in all directions from here
            Direction.values()
                    .map { dir -> dir.from(current.currentPos) }
                    .filter { newPos -> traversable.contains(map[newPos]) }
                    .forEach { toCheck.add(ExplorationState(it, current.steps + 1, newDoors)) }
        }
        return keysFound

    }

    data class ShortestPathState(val standingAtKeys: List<Char>, val keysFound: Set<Char> = emptySet()) {
        var steps = 0
    }

    // robotKeyPaths is a list with one entry per robot.
    // Each list entry is a map of all keys reachable for the robot
    // Each map entry holds information about the path to the other key
    private fun findShortestPathToAllKeys(robotKeyPaths: List<Map<Char, Set<PathToKey>>>): Int {
        val toCheck = PriorityQueue<ShortestPathState>(compareBy { state -> state.steps })
                .apply { add(ShortestPathState(List(robotKeyPaths.size) { '@' })) }

        val alreadyChecked = mutableSetOf<ShortestPathState>()
        while (toCheck.isNotEmpty()) {
            val current = toCheck.remove()
            if (alreadyChecked.contains(current)) continue
            if (current.keysFound.size == keys.size) {
                // found all keys
                return current.steps
            }
            alreadyChecked.add(current)

            // For each robot
            robotKeyPaths.withIndex().forEach { (robot, distancesToKeys) ->
                distancesToKeys[current.standingAtKeys[robot]]!!
                        // Only consider keys that are not blocked by locked doors
                        .filter { it.doors.all { doors -> current.keysFound.contains(doors.toLowerCase()) } }
                        .forEach { (key, steps, _) ->
                            // For each reachable key from the current robot, queue it for checking.
                            val keysHeld = current.keysFound.toMutableSet().apply { add(key) }
                            val currentKeys = current.standingAtKeys.toMutableList()
                            currentKeys[robot] = key
                            val state = ShortestPathState(currentKeys, keysHeld).apply { this.steps = current.steps + steps }
                            if (!alreadyChecked.contains(state)) {
                                toCheck.add(state)
                            }
                        }
            }
        }
        return -1
    }

    private fun findAllKeys(map: AMap, startPos: List<Pos>, keyReachableByRobot: (Pos, Int) -> Boolean): Int {
        val traversable = mutableSetOf('.', '@').apply {
            addAll(keys.keys)
            addAll(doors.keys)
        }

        // For each robot -  calculate the distance between all reachable keys
        val distances = startPos.withIndex().map { (robot, startPos) ->
            val keysForThisRobot = keys.filterValues { keyReachableByRobot(it, robot) }
                    .toMutableMap()
                    // Include start pos to get distances from start pos to all keys, important for part 2
                    // where there are some robots that can't reach a key without someone else picking up a
                    // key first.
                    .apply { this['@'] = startPos }
            keysForThisRobot.map { (key, pos) ->
                // Calculate distance by walking trough the whole reachable map once from each key
                key to exploreFrom(map, pos, traversable, keysForThisRobot.size)
            }.toMap()
        }

        // Now that all distances are known, do a BFS to find the shortest path that picks up all keys
        return findShortestPathToAllKeys(distances)
    }

    fun solvePart1(): Int {
        return findAllKeys(map, listOf(startPos)) { _, _ -> true } // There is only one robot, all keys belong to it
    }

    fun solvePart2(): Int {
        val (newMap, startPos) = updateMap(map)
        return findAllKeys(newMap, startPos) { pos, robot -> robotForPos(pos) == robot }
    }
}