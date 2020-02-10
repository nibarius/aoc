package aoc2016

import Direction
import Pos
import org.magicwerk.brownies.collections.GapList
import java.util.*

class Day24(input: List<String>) {
    val theMap = Map(input)

    /**
     * A node in the graph.
     *
     * A node is a place where the road splits into more than one new direction
     * or a named location (where a number exists).
     * @property address the location of this node.
     * @property name the name of this node (0, 1, 2, etc), '.' if it doesn't have any name.
     * @property neighbours A list of all this nodes neighbours as a Pair of address, distance
     */
    data class Node(val address: Pos, val name: Char, val neighbours: MutableList<Pair<Pos, Int>>)

    /**
     * The current state of walking trough the graph while searching for the solution.
     * @property currentAddress the address where we're currently standing
     * @property steps the number of steps taken to come to this position
     * @property visitedPlaces a list of all places (0, 1, 2, 3 etc) that has been visited so far
     * @property visitedNodes a list of the coordinates of all nodes visited since the last
     * place. There is no reason to go back to a previously visited node until a new place
     * has been reached.
     * @property goingHome true if we have found all places and are trying to find the way back home
     */
    data class WalkedPath(val currentAddress: Pos, val steps: Int, val visitedPlaces: List<Char>,
                          val visitedNodes: List<Pos>, val goingHome: Boolean = false)

    // A representation of the map. Takes the ascii map as input and can generate a graph
    // that can be traversed more easily.
    data class Map(val theMap: List<String>) {
        private val theGraph = mutableMapOf<Pos, Node>()

        fun findStart(): Pos {
            theMap.forEachIndexed { y, s ->
                if (s.contains('0')) {
                    return Pos(s.indexOf('0'), y)
                }
            }
            return Pos(-1, -1)
        }

        // Get all the direction that it's possible to move in from the given coordinate
        private fun getPossibleDirectionsFrom(address: Pos): List<Direction> {
            val ret = mutableListOf<Direction>()
            for (direction in Direction.values()) {
                val next = theMap[address.y + direction.dy][address.x + direction.dx]
                if (next != '#') {
                    ret.add(direction)
                }
            }
            return ret
        }

        // Get the node at the given address and create one if needed
        private fun createNodeIfNeeded(address: Pos): Node {
            if (!theGraph.containsKey(address)) {
                theGraph[address] = Node(address, theMap[address.y][address.x], mutableListOf())
            }
            return theGraph[address]!!
        }

        // Start at the given coordinate and walk to the next node. Return the coordinate of the next node
        // and the distance walked to get to that node.
        private tailrec fun walkToNextNode(address: Pos,
                                           comingFrom: Direction, distanceWalked: Int): Pair<Pos, Int>? {
            val dirs = getPossibleDirectionsFrom(address).filterNot { it == comingFrom }
            return when {
                theMap[address.y][address.x] in '0'..'9' || dirs.size > 1 -> {
                    createNodeIfNeeded(address)
                    Pair(address, distanceWalked)
                }
                dirs.isEmpty() -> null
                dirs.size == 1 -> {
                    walkToNextNode(dirs[0].from(address), dirs[0].opposite(), distanceWalked + 1)
                }
                else -> throw(RuntimeException("Unexpected state"))
            }
        }


        // Given a node, find all it's neighbours and populate the neighbours list in order of closest to
        // furthers away.
        private fun findNeighboursOf(node: Node) {
            val dirs = getPossibleDirectionsFrom(node.address)
            // Room for improvement: No need to walk already known paths
            dirs.forEach { dir ->
                val nextNode = walkToNextNode(dir.from(node.address), dir.opposite(), 1)
                if (nextNode != null) {
                    node.neighbours.add(nextNode)
                }
            }
            node.neighbours.sortBy { it.second }
        }

        // Convert the ascii map into a graph that can be traversed more easily (a map of coordinates to nodes).
        fun buildGraph(): MutableMap<Pos, Node> {
            val nodesToCheck = ArrayDeque<Node>()
            nodesToCheck.add(createNodeIfNeeded(findStart()))
            while (nodesToCheck.size > 0) {
                val node = nodesToCheck.removeFirst()
                findNeighboursOf(node)
                node.neighbours.filter { theGraph[it.first]!!.neighbours.isEmpty() }.forEach {
                    if (!nodesToCheck.contains(theGraph[it.first])) {
                        nodesToCheck.add(theGraph[it.first])
                    }
                }
            }
            return theGraph
        }
    }

    // Possible improvement that I never had to try: First make a reduced graph with
    // the shortest distances between all individual named nodes. Then do the below
    // algorithm of that tiny graph. Should probably reduce the total amount of nodes
    // that needs to be checked greatly.

    // Start at 0 and search trough the whole graph for the shortest path that passes
    // by each place of interest at least once. Optionally also returns back to the
    // start when all places of interest have been visited.
    // Returns the number of steps required for this.
    private fun searchGraph(theGraph: MutableMap<Pos, Node>, returnToStartWhenAllFound: Boolean = false): Int {
        val numPlacesToVisit = theGraph.filter { it.value.name != '.' }.size
        val alreadyChecked = mutableSetOf<Pair<Pos, List<Char>>>()
        val goingHomeFrom = mutableSetOf<Char>()
        val toCheck = GapList<WalkedPath>() // Time to solve Part 1 with: GapList ~6s, ArrayList: ~7s LinkedList: ~3m
        toCheck.add(WalkedPath(theMap.findStart(), 0, listOf(), listOf()))
        while (toCheck.isNotEmpty()) {
            val pathSoFar = toCheck.removeAt(0)
            if (!pathSoFar.visitedPlaces.containsAll(goingHomeFrom)) {
                // This path haven't yet found at least one node that a faster
                // path is already returning home from. There is no way this
                // path can be faster at finding all and going back to the goal
                // than that one.
                continue
            }

            // Check the current node that we're currently standing at
            val currentNode = theGraph[pathSoFar.currentAddress]!!
            alreadyChecked.add(Pair(pathSoFar.currentAddress, pathSoFar.visitedPlaces))
            val visitedNodes = pathSoFar.visitedNodes.toMutableList()
            val visitedPlaces = pathSoFar.visitedPlaces.toMutableList()
            var goingHome = pathSoFar.goingHome
            if (!pathSoFar.goingHome && currentNode.name != '.' && !visitedPlaces.contains(currentNode.name)) {
                // We're visiting a place of interest for the first time
                visitedPlaces.add(currentNode.name)
                if (visitedPlaces.size == numPlacesToVisit) {
                    if (returnToStartWhenAllFound) {
                        goingHome = true
                        goingHomeFrom.add(currentNode.name)
                    } else {
                        // When all places have been visited and we have no interest in
                        // going back home, abort and return
                        return pathSoFar.steps
                    }
                }
                // After visiting a place of interest, it's OK to go back to a previous node again.
                // The shortest path might be by going back in the same direction we just came from.
                visitedNodes.clear()
            } else if (pathSoFar.goingHome && currentNode.name == '0') {
                // We're home and now we're finally done
                return pathSoFar.steps

            }
            visitedNodes.add(currentNode.address)

            // The node we're standing in wasn't the goal. We need to check all the neighbours.
            // But skip neighbours that we have already visited in the past
            val neighboursToCheck =
                    currentNode.neighbours.filterNot { alreadyChecked.contains(Pair(it.first, visitedPlaces)) }
            for (neighbourNodeToCheck in neighboursToCheck) {
                val totalDistance = pathSoFar.steps + neighbourNodeToCheck.second
                val nextPath = WalkedPath(neighbourNodeToCheck.first, totalDistance,
                        visitedPlaces, visitedNodes, goingHome)

                // Insert the WalkedPath at the correct place in the queue based on distance.
                if (toCheck.isEmpty()) {
                    toCheck.add(nextPath)
                } else {
                    val queuedWithSameState = toCheck.toList().filter { queuedPath ->
                        queuedPath.currentAddress == nextPath.currentAddress &&
                                queuedPath.visitedPlaces == nextPath.visitedPlaces
                    }
                    var i = toCheck.size - 1
                    if (queuedWithSameState.isNotEmpty()) {
                        if (queuedWithSameState.first().steps > totalDistance) {
                            // remove the already queued one with more steps from the queue and start
                            // searching for the correct place to add the new node at the place of the
                            // removed one
                            i = toCheck.indexOf(queuedWithSameState.first()) - 1
                            toCheck.remove(queuedWithSameState.first())

                        } else {
                            // the one we are about to insert has more steps than something in the queue
                            // already. Don't insert it at all. Move on to the next one instead.
                            continue
                        }

                    }

                    while (i >= 0) {
                        if (totalDistance > toCheck[i].steps) {
                            toCheck.add(i + 1, nextPath)
                            break
                        } else if (i == 0) {
                            toCheck.add(0, nextPath)
                            break
                        }
                        i--
                    }
                }
            }
        }
        return -1
    }

    fun solvePart1(): Int {
        val graph = theMap.buildGraph()
        return searchGraph(graph)
    }

    fun solvePart2(): Int {
        val graph = theMap.buildGraph()
        return searchGraph(graph, true)
    }
}
