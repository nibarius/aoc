package aoc2019

import Direction
import Pos
import AMap
import org.magicwerk.brownies.collections.GapList
import kotlin.math.max

class Day15(input: List<String>) {

    val program = Intcode(input.map { it.toLong() })

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

        fun move(direction: Direction): Status {
            val dir = when(direction){
                Direction.Up -> 1L
                Direction.Down -> 2L
                Direction.Left -> 3L
                Direction.Right -> 4L
            }
            program.input.add(dir)
            program.run()
            return Status.get(program.output.removeAt(0))
        }
    }

    private data class QueueEntry(val pos: Pos, val droid: Droid, val direction: Direction)

    private fun makeMap(droid: Droid): Triple<Int, Pos, AMap> {
        val map = AMap()

        // Queue is position, (droid + movement)
        val queue = GapList<QueueEntry>()
        val alreadyChecked = mutableSetOf<Pos>()
        val startPos = Pos(0, 0)
        var stepsToOxygen = 0
        var oxygenPosition = Pos(0,0)

        // Move one step in every direction
        droid.stepsTaken++
        Direction.values().forEach { queue.add(QueueEntry(it.from(startPos), droid.copy(), it)) }
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
            Direction.values().forEach {
                val nextPos = it.from(current.pos)
                if (!alreadyChecked.contains(nextPos)) {
                    queue.add(QueueEntry(nextPos, current.droid.copy(), it))
                }
            }
        }

        return Triple(stepsToOxygen, oxygenPosition, map)
    }

    data class QueueEntry2(val pos: Pos, val stepsTaken: Int)

    private fun maxDistanceFrom(pos: Pos, map: AMap): Int {
        val queue = GapList<QueueEntry2>()
        val alreadyChecked = mutableSetOf<Pos>()
        var maxDistance = 0

        // Move one step in every direction
        Direction.values().forEach { queue.add(QueueEntry2(it.from(pos), 1)) }
        while (queue.isNotEmpty()) {
            val current = queue.remove()
            if (alreadyChecked.contains(current.pos)) continue
            val result = map[current.pos]!!
            alreadyChecked.add(current.pos)

            if (result == '#') continue
            maxDistance = max(maxDistance, current.stepsTaken)

            Direction.values().forEach {
                val nextPos = it.from(current.pos)
                if (!alreadyChecked.contains(nextPos)) {
                    queue.add(QueueEntry2(nextPos, current.stepsTaken + 1))
                }
            }
        }
        return maxDistance
    }

    fun solvePart1(): Int {
        return makeMap(Droid(program)).first
    }

    fun solvePart2(): Int {
        return makeMap(Droid(program)).let { maxDistanceFrom(it.second, it.third) }
    }
}