package aoc2015

import Pos
import increase
import toDirection

class Day3(private val input: String) {

    private fun visit(numSantas: Int): Int {
        val visitedHouses = mutableMapOf<Pos, Int>().apply {
            val pos = Array(numSantas) { Pos(0,0) }
            pos.forEach { increase(it) }
            input.withIndex().forEach { (i, instruction) ->
                val index = i % pos.size
                pos[index] = pos[index].move(instruction.toDirection())
                increase(pos[index])
            }
        }
        return visitedHouses.keys.size
    }

    fun solvePart1(): Int {
        return visit(1)
    }

    fun solvePart2(): Int {
        return visit(2)
    }
}