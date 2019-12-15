package aoc2019

import org.magicwerk.brownies.collections.GapList
import kotlin.math.max

class Day15(input: List<String>) {

    val program = Intcode(input.map { it.toLong() })

    data class Pos(val x: Int, val y: Int)
    enum class Dir(val value: Long, val dx: Int, val dy: Int) {
        North(1, 0, 1),
        South(2, 0, -1),
        West(3, -1, 0),
        East(4, 1, 0);

        fun from(pos: Pos) = Pos(pos.x + dx, pos.y + dy)
    }

    enum class Status(val ch: Char) {
        Wall('#'),
        Moved('.'),
        Found('o');

        companion object {
            fun get(value: Long): Status {
                return when (value) {
                    2L -> Found
                    1L -> Moved
                    else -> Wall
                }
            }
        }
    }

    data class Droid(private val program: Intcode, var stepsTaken: Int = 0) {

        @Override
        fun copy() = Droid(program.copy(), stepsTaken)

        fun move(direction: Dir): Status {
            program.input.add(direction.value)
            program.run()
            return Status.get(program.output.removeAt(0))
        }
    }

    private data class QueueEntry(val pos: Pos, val droid: Droid, val direction: Dir)

    private fun makeMap(droid: Droid): Triple<Int, Pos, MutableMap<Pos, Char>> {
        val map = mutableMapOf<Pos, Char>()

        // Queue is position, (droid + movement)
        val queue = GapList<QueueEntry>()
        val alreadyChecked = mutableSetOf<Pos>()
        val startPos = Pos(0, 0)
        var stepsToOxygen = 0
        var oxygenPosition = Pos(0,0)

        // Move one step in every direction
        droid.stepsTaken++
        Dir.values().forEach { queue.add(QueueEntry(it.from(startPos), droid.copy(), it)) }
        while (queue.isNotEmpty()) {
            val current = queue.remove()
            if (alreadyChecked.contains(current.pos)) continue
            val result = current.droid.move(current.direction) // Move to get to the current position
            map[current.pos] = result.ch
            alreadyChecked.add(current.pos)

            if (result == Status.Wall) continue
            else if (result == Status.Found) {
                stepsToOxygen = current.droid.stepsTaken
                oxygenPosition = current.pos
            }

            current.droid.stepsTaken++
            Dir.values().forEach {
                val nextPos = it.from(current.pos)
                if (!alreadyChecked.contains(nextPos)) {
                    queue.add(QueueEntry(nextPos, current.droid.copy(), it))
                }
            }
        }

        //println(printArea(map))
        return Triple(stepsToOxygen, oxygenPosition, map)
    }

    data class QueueEntry2(val pos: Pos, val stepsTaken: Int)

    private fun maxDistanceFrom(pos: Pos, map: Map<Pos, Char>): Int {
        val queue = GapList<QueueEntry2>()
        val alreadyChecked = mutableSetOf<Pos>()
        var maxDistance = 0

        // Move one step in every direction
        Dir.values().forEach { queue.add(QueueEntry2(it.from(pos), 1)) }
        while (queue.isNotEmpty()) {
            val current = queue.remove()
            if (alreadyChecked.contains(current.pos)) continue
            val result = map[current.pos]!!
            alreadyChecked.add(current.pos)

            if (result == '#') continue
            maxDistance = max(maxDistance, current.stepsTaken)

            Dir.values().forEach {
                val nextPos = it.from(current.pos)
                if (!alreadyChecked.contains(nextPos)) {
                    queue.add(QueueEntry2(nextPos, current.stepsTaken + 1))
                }
            }
        }
        return maxDistance
    }

    // Used for debugging
    @Suppress("unused")
    private fun printArea(map: Map<Pos, Char>): String {
        fun Map<Pos, Char>.xRange() = keys.minBy { it.x }!!.x..keys.maxBy { it.x }!!.x
        fun Map<Pos, Char>.yRange() = keys.minBy { it.y }!!.y..keys.maxBy { it.y }!!.y
        return (map.yRange()).joinToString("\n") { y ->
            (map.xRange()).joinToString("") { x ->
                map.getOrDefault(Pos(x, y), '#').toString()
            }
        }
    }

    fun solvePart1(): Int {
        return makeMap(Droid(program)).first
    }

    fun solvePart2(): Int {
        return makeMap(Droid(program)).let { maxDistanceFrom(it.second, it.third) }
    }
}