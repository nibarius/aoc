package aoc2022

import Grid
import Pos
import kotlin.math.max

class Day8(input: List<String>) {

    private val forrest = Grid.parse(input)

    /**
     * Get a list of 4 lists, one for each direction. Each list contains the positions
     * of all the trees in the given direction, starting from closest to the star tree
     * and ending with the tree the furthest away. Trees on the edge have at least one
     * empty list (since there are no trees further out).
     */
    private fun allTreesInAllDirections(fromTree: Pos): List<List<Pos>> {
        return listOf(
            (fromTree.y - 1 downTo 0).map { Pos(fromTree.x, it) }, // up
            (fromTree.y + 1 until forrest.size).map { Pos(fromTree.x, it) }, //down
            (fromTree.x - 1 downTo 0).map { Pos(it, fromTree.y) }, // left
            (fromTree.x + 1 until forrest.size).map { Pos(it, fromTree.y) } // right
        )
    }

    /**
     * Return the number of trees that are visible in the given list. No trees beyond the
     * first tree with a height larger or equal to the ownHeight is visible.
     */
    private fun List<Pos>.numVisible(ownHeight: Char): Int {
        var visible = 0
        for (tree in this) {
            val otherHeight = forrest[tree]
            if (otherHeight < ownHeight) {
                visible++
            } else {
                return visible + 1
            }
        }
        return visible
    }

    fun solvePart1(): Int {
        var visible = 0
        for (tree in forrest.keys) {
            // all on an empty list returns true, which means trees on the edge is visible
            if (allTreesInAllDirections(tree).any { dir -> dir.all { forrest[tree] > forrest[it] } }) {
                visible++
            }
        }
        return visible
    }

    fun solvePart2(): Int {
        var bestScore = 0
        for (tree in forrest.keys) {
            // Trees on the edge will have at least one empty list which results in a score of 0
            bestScore = max(
                bestScore,
                allTreesInAllDirections(tree).fold(1) { acc, trees -> acc * trees.numVisible(forrest[tree]) })
        }
        return bestScore
    }
}