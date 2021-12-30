import java.util.*

/**
 * Various common path finding algorithms. Based on https://www.redblobgames.com/pathfinding/a-star/introduction.html
 * and https://www.redblobgames.com/pathfinding/a-star/implementation.html which explains BFS, Djikstra and A*
 * in an easy-to-understand way.
 */
object Search {
    interface Location
    interface Graph {
        fun neighbours(id: Location): List<Location>
    }

    interface WeightedGraph : Graph {
        fun cost(from: Location, to: Location): Float
    }

    /**
     * Holds the result of a search.
     * @param cameFrom shows how to get to a certain location. "cameFrom[B] == A" means that the path
     * to B comes from A.
     * @param cost a map that shows the total cost to get to all visited locations
     */
    data class Result(val cameFrom: Map<Location, Location>, val cost: Map<Location, Float>)

    /**
     * Standard breadth first search. Good when all locations must be visited or when there is no cost
     * involved and there is no reasonable heuristic that can be used to find the distance to the goal.
     * @param graph the graph to search
     * @param start the location to start the search from
     * @param goal the location to search for, or null if the whole graph should be searched
     */
    fun bfs(graph: Graph, start: Location, goal: Location?): Result {
        val toCheck = ArrayDeque<Location>()
        toCheck.add(start)
        val cameFrom = mutableMapOf<Location, Location>()
        while (toCheck.isNotEmpty()) {
            val current = toCheck.removeFirst()
            if (current == goal) {
                break
            }
            for (next in graph.neighbours(current)) {
                if (next !in cameFrom) {
                    toCheck.add(next)
                    cameFrom[next] = current
                }
            }
        }
        return Result(cameFrom, mapOf())
    }

    /**
     * Good search algorithm when there is different cost involved in moving to different locations,
     * and there is no good heuristic for estimating the remaining cost to the goal.
     * @param graph the graph to search
     * @param start the location to start the search from
     * @param goal the location to search for
     */
    fun djikstra(graph: WeightedGraph, start: Location, goal: Location): Result {
        val toCheck = PriorityQueue(compareBy<Pair<Location, Float>> { it.second })
        toCheck.add(start to 0f)
        val cameFrom = mutableMapOf<Location, Location>()
        val costSoFar = mutableMapOf(start to 0.0f)
        while (toCheck.isNotEmpty()) {
            val (current, _) = toCheck.remove()
            if (current == goal) {
                break
            }
            for (next in graph.neighbours(current)) {
                val nextCost = costSoFar.getValue(current) + graph.cost(current, next)
                if (nextCost < costSoFar.getOrDefault(next, Float.MAX_VALUE)) {
                    toCheck.add(next to nextCost)
                    cameFrom[next] = current
                    costSoFar[next] = nextCost
                }
            }
        }
        return Result(cameFrom, costSoFar)
    }

    // Note: In both djikstra and a* the toCheck queue may have multiple entries of the same location but with different
    // cost. There is no additional early continue for those as all their neighbours will be more expensive than
    // what's been processed so far anyway so that path will lead to a dead end quickly.
    // This is also mentioned as point 5 in https://www.redblobgames.com/pathfinding/a-star/implementation.html#algorithm

    /**
     * A* is a good algorithm for finding the shortest path when there is cost involved, and it's possible to use
     * a heuristic to estimate the cost to get to the goal. The heuristic must be admissible (never overestimate
     * the cost). If the heuristic overestimates the cost it will not be guaranteed to find the shortest path.
     * @param graph the graph to search
     * @param start the location to start the search from
     * @param goal the location to search for
     * @param heuristic a heuristic function that estimates the cost to reach the goal. Parameters are start location
     * and goal location while the return value is the estimated cost to move from start to goal. Must never
     * overestimate the cost to reach the goal.
     */
    fun aStar(graph: WeightedGraph, start: Location, goal: Location, heuristic: (Location, Location) -> Float): Result {
        val toCheck = PriorityQueue(compareBy<Pair<Location, Float>> { it.second })
        toCheck.add(start to 0f)
        val cameFrom = mutableMapOf<Location, Location>()
        val costSoFar = mutableMapOf(start to 0.0f)
        while (toCheck.isNotEmpty()) {
            val (current, _) = toCheck.remove()
            if (current == goal) {
                break
            }
            for (next in graph.neighbours(current)) {
                val nextCost = costSoFar.getValue(current) + graph.cost(current, next)
                if (nextCost < costSoFar.getOrDefault(next, Float.MAX_VALUE)) {
                    toCheck.add(next to nextCost + heuristic(next, goal))
                    cameFrom[next] = current
                    costSoFar[next] = nextCost
                }
            }
        }
        return Result(cameFrom, costSoFar)
    }
}