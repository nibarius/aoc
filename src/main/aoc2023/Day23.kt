package aoc2023

import AMap
import Direction
import Pos
import Search

class Day23(input: List<String>) {
    private val map = AMap.parse(input, listOf('#'))
    private val startPos = Pos(1, 0)
    private val goalPos = Pos(map.xRange().last, map.yRange().last)
    private val intermediateEdges = walkAllPaths()
    private val posToNodeId =
        intermediateEdges.flatMap { setOf(it.from, it.to) }.withIndex().associate { (i, pos) -> pos to i.toByte() }

    private val nodes = posToNodeId.values.toSet()
    private val edges = intermediateEdges.map { Edge(posToNodeId[it.from]!!, posToNodeId[it.to]!!, it.distance) }
    private val start = posToNodeId[startPos]!!
    private val goal = posToNodeId[goalPos]!!

    /**
     * A directional edge that can only be traversed in one direction.
     */
    private data class IntermediateEdge(val from: Pos, val to: Pos, val distance: Int)

    private data class Edge(val from: Byte, val to: Byte, val distance: Int)

    /**
     * Walk through all paths one step at a time and generate a set of edges where each edge is
     * represented by the two crossroads that makes up the edge and the distance between them.
     */
    private fun walkAllPaths(): MutableSet<IntermediateEdge> {
        val toWalk = mutableListOf(startPos)
        val allEdges = mutableSetOf<IntermediateEdge>()
        while (toWalk.isNotEmpty()) {
            val current = toWalk.removeFirst()
            val newEdges = walkPathsFrom(current)
            allEdges.addAll(newEdges)
            toWalk.addAll(newEdges.map { it.to }.toSet())
        }
        return allEdges
    }

    /**
     * Walk all the different paths leading away from the given position.
     * @return a list of all edges originating at this position.
     */
    private fun walkPathsFrom(start: Pos): List<IntermediateEdge> {
        val exits = getExits(start)
        val paths = exits.map { walkToIntersection(start, it) }
        return paths.map { IntermediateEdge(start, it.first, it.second) }
    }

    /**
     * Gets all exits from this position that's possible to take.
     * Slopes pointing the wrong direction are not included.
     */
    private fun getExits(currentPos: Pos): List<Pos> {
        when (currentPos) {
            startPos -> return listOf(startPos.move(Direction.Down)) // Start is always at the top
            goalPos -> return listOf() // You can't go anywhere from the goal
        }
        return currentPos.allNeighbours().filter {
            val ch = map[it]
            // Valid exits are neighbours you can walk to by walking in the direction of the slope.
            ch != null && Direction.fromChar(ch).from(currentPos) == it
        }
    }

    /**
     * Walk to the next intersection.
     * @return the position of the next intersection and the number of steps to reach it
     */
    private fun walkToIntersection(start: Pos, firstStep: Pos): Pair<Pos, Int> {
        var current = firstStep
        val seen = mutableSetOf(start)
        while (true) {
            val next = current.allNeighbours().filter { it in map && it !in seen }
            if (next.size > 1 || current == goalPos) {
                // Found an intersection
                return current to seen.size
            }
            seen.add(current)
            current = next.single()
        }
    }

    /**
     * Find the longest path by moving along the directional edges and try all options.
     */
    private fun findLongestPath(): Int {
        val nodes = edges.groupBy { it.from }
        val distances = mutableMapOf<Byte, Int>()
        val toCheck = mutableListOf(start to 0)
        while (toCheck.isNotEmpty()) {
            val (currentPos, currentDistance) = toCheck.removeFirst()
            if (distances.getOrDefault(currentPos, 0) > currentDistance) {
                // We've been here before coming from a longer route, abort this traversal
                continue
            }
            distances[currentPos] = currentDistance

            nodes[currentPos]?.forEach { nextEdge ->
                val nextDistance = currentDistance + nextEdge.distance
                val nextPos = nextEdge.to
                // If we already queued the next node but with a worse distance, remove it.
                toCheck.removeIf { queued -> queued.first == nextPos && queued.second < nextDistance }
                // Add new route to the processing queue
                toCheck.add(nextPos to nextDistance)
            }
        }
        return distances.getOrDefault(goal, -1)
    }

    fun solvePart1(): Int {
        return findLongestPath()
    }

    fun solvePart2(): Int {
        return findLongestPath2()
    }

    private fun findLongestPath2(): Int {
        val edgeLengths = edges.associate { edge -> setOf(edge.to, edge.from) to edge.distance }
        val edgeConnections = edges.map { e -> setOf(e.to, e.from) }
        val neighbouringNodes = nodes.associateWith { pos ->
            edgeConnections
                .filter { pos in it } // Edges from this node
                .flatten().toSet() - pos // Nodes that it leads to
        }
        // Node to list of edges, each edge is a pair of edge-id and length. Will be used to estimate remaining length
        val bestEdgesForNode = nodes.associateWith { node ->
            edgeLengths.toList().withIndex().filter { (_, pair) -> node in pair.first } // all edges from this node
                .sortedByDescending { it.value.second } // sort by longest edge first
                .map { it.index.toByte() to it.value.second }
        }

        val graph = Graph(neighbouringNodes, edgeLengths)

        val res = Search.maximizeValueDfs(
            graph, State(start, nodes - start),
            isGoal = { it.currentNode == goal },
            estimateBestCase = {
                // Assume we will walk past every node and always take the best edge that hasn't
                // already been taken. Fairly easy heuristic, but not quite as good as taking the best remaining
                // edges. But calculating all the remaining edges takes too much time. Including start node
                // as we'll walk away from it and excluding the goal node since we'll stay there.
                val seen = mutableSetOf<Byte>()
                (it.remainingNodes + it.currentNode - goal).sumOf { node ->
                    val edge = bestEdgesForNode[node]!!.first { edge -> edge.first !in seen }
                    seen.add(edge.first)
                    edge.second
                }.toFloat()
            }
        )
        return res.second.cost[res.first]!!.toInt()
    }

    data class State(val currentNode: Byte, val remainingNodes: Set<Byte>, val numVisitedNodes: Int = 1)
    private class Graph(val neighboursOf: Map<Byte, Set<Byte>>, val edgeLengths: Map<Set<Byte>, Int>) :
        Search.WeightedGraph<State> {

        override fun neighbours(id: State): List<State> {
            val candidates = neighboursOf[id.currentNode]!!.intersect(id.remainingNodes)
            return candidates.map { pos ->
                // Nodes that we don't walk to is a dead end if they only connect to the node we're leaving.
                val deadEnds = (candidates - pos)
                    .filter { neighboursOf[it]!!.intersect(id.remainingNodes).size == 1 }
                    .toSet()
                State(pos, id.remainingNodes - pos - deadEnds, id.numVisitedNodes + 1)
            }
        }

        override fun cost(from: State, to: State): Float {
            return edgeLengths[setOf(from.currentNode, to.currentNode)]!!.toFloat()
        }
    }
}