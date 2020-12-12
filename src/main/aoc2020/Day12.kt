package aoc2020

import Pos
import toDirection

class Day12(input: List<String>) {

    private val movements = input.map { it[0] to it.drop(1).toInt() }

    private fun followInstructions(initialWaypoint: Pos, shipMove: Int = 0, waypointMove: Int = 0): Int {
        var shipPos = Pos(0, 0)
        var waypoint = initialWaypoint
        for ((action, amount) in movements) {
            when (action) {
                'F' -> shipPos += waypoint * amount
                'L', 'R' -> {
                    repeat(amount / 90) {
                        waypoint = waypoint.rotate(action.toDirection())
                    }
                }
                else -> {
                    shipPos = shipPos.move(action.toDirection(), amount * shipMove)
                    waypoint = waypoint.move(action.toDirection(), amount * waypointMove)
                }
            }
        }
        return shipPos.distanceToOrigin
    }

    fun solvePart1(): Int {
        // The ship rotating and moving forward is the same as having the waypoint
        // one step from the ship and then having the waypoint doing the rotations.
        return followInstructions(Pos(1, 0), shipMove = 1)
    }

    fun solvePart2(): Int {
        return followInstructions(Pos(10, -1), waypointMove = 1)
    }
}