package aoc2023

import Search
import increase

class Day25(input: List<String>) {
    private val edges = buildList {
        input.forEach { line ->
            val a = line.substringBefore(":")
            for (b in line.substringAfter(": ").split(" ").toSet()) {
                add(setOf(a, b))
            }
        }
    }

    private val parts = generateNeighbours(edges)

    /**
     * Take a list of edges and generate a map where each node exists as key and
     * the value is the set of all it's neighbours.
     */
    private fun generateNeighbours(edges: List<Set<String>>): Map<String, Set<String>> {
        return edges.flatten().toSet().associateWith { key ->
            buildSet {
                val x = edges.filter { key in it }
                    .flatten()
                    .toMutableSet()
                    .apply { remove(key) }
                addAll(x)
            }
        }
    }

    class MyGraph(private val parts: Map<String, Set<String>>) : Search.Graph<String> {
        override fun neighbours(id: String): List<String> {
            return parts[id]!!.toList()
        }
    }

    /**
     * Find the cutting points by randomly finding the paths between 1000 random nodes.
     * The three most visited edges will be the cutting points as they need to be used
     * whenever moving between the two group of nodes.
     */
    private fun findCuttingPoints(): List<Set<String>> {
        val g = MyGraph(parts)
        val visitedEdges = mutableMapOf<Set<String>, Int>()
        repeat(1000) {
            val start = parts.keys.random()
            val goal = parts.keys.random()
            Search.bfs(g, start, goal)
                .getPath(goal).toMutableList()
                .apply { add(0, start) }
                .zipWithNext { a, b -> setOf(a, b) }
                .forEach { visitedEdges.increase(it) }
        }
        return visitedEdges.toList().sortedByDescending { it.second }.take(3).map { it.first }
    }

    /**
     * Calculate the group sizes. First do the cut to create two distinct groups,
     * then find all connected nodes in one group to get the size of the first group.
     * The size of the second group is the remaining nodes.
     */
    private fun calculateGroupSizes(cuttingPoints: List<Set<String>>): Int {
        val remainingEdges = edges.filterNot { it in cuttingPoints }
        val g = MyGraph(generateNeighbours(remainingEdges))
        val res = Search.bfs(g, cuttingPoints.first().first(), null)
        val groupA = res.cameFrom.size
        return groupA * (parts.size - groupA)
    }

    fun solvePart1(): Int {
        val cp = findCuttingPoints()
        return calculateGroupSizes(cp)
    }
}