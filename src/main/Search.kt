import java.util.*

/**
 * Various common path finding algorithms. Based on https://www.redblobgames.com/pathfinding/a-star/introduction.html
 * and https://www.redblobgames.com/pathfinding/a-star/implementation.html which explains BFS, Djikstra and A*
 * in an easy-to-understand way.
 *
 * Which algorithm to use when:
 * - Use BFS if there is no cost involved, or the whole graph have to be searched.
 * - Use Djikstra if there is a different cost depending on how you move, but there is no way
 *   to estimate the remaining cost.
 * - Use A* if there is cost involved, and it's possible estimate the remaining cost without giving an
 *   over estimation. With an over estimation the shortest path is not guaranteed to be found.
 *
 * How to use:
 * - Create a class that implement the suitable Graph interface depending on algorithm
 * - The class holds the representation of the thing to search through and just provides the available neighbours
 *   and possible cost for any given node.
 * - Call the desired search function with the provided graph.
 */
object Search {
    /**
     * Interface that should be implemented by a class to do BFS. To search through a AMap, which uses Pos for
     * indexing, create a class that implements Graph<Pos> and pass that to the bfs() function.
     */
    interface Graph<T> {
        /**
         * Returns all neighbours that it's possible to reach from the given node id.
         */
        fun neighbours(id: T): List<T>
    }

    /**
     * Interface that should be implemented by a class when Djikstra or A* search is wanted.
     */
    interface WeightedGraph<T> : Graph<T> {
        /**
         * The cost for moving between the two given nodes.
         *
         * Note, since cost returns a float, make sure to return fractions that are powers of two if it's important
         * to avoid rounding errors. For example when it's important which path is taken if two paths have exactly
         * the same cost (for example WWSS vs SSWW)
         */
        fun cost(from: T, to: T): Float
    }

    /**
     * Holds the result of a search.
     * @param cameFrom shows how to get to a certain location. "cameFrom[B] == A" means that the path
     * to B comes from A. The start point points back to itself, so  cameFrom[A] == A.
     * @param cost a map that shows the total cost to get to all visited locations
     */
    data class Result<T>(val cameFrom: Map<T, T>, val cost: Map<T, Float>) {
        /**
         * Gives the best path to the given location from the start point of the search (start point
         * not included in the returned path).
         */
        fun getPath(to: T): List<T> {
            return buildList {
                var current = to
                while (cameFrom[current] != null && cameFrom[current] != current) {
                    add(current)
                    current = cameFrom.getValue(current)
                }
            }.asReversed()
        }
    }

    /**
     * Standard breadth first search. Good when all locations must be visited or when there is no cost
     * involved and there is no reasonable heuristic that can be used to find the distance to the goal.
     * @param graph the graph to search
     * @param start the location to start the search from
     * @param goal the location to search for, or null if the whole graph should be searched
     */
    fun <T> bfs(graph: Graph<T>, start: T, goal: T?): Result<T> {
        val toCheck = ArrayDeque<T>()
        toCheck.add(start)
        val cameFrom = mutableMapOf(start to start)
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
     * @param maxCost the maximum cost allowed to find a solution. No cost limit applied if not provided.
     */
    fun <T> djikstra(graph: WeightedGraph<T>, start: T, goal: T, maxCost: Float = Float.MAX_VALUE): Result<T> {
        val toCheck = PriorityQueue(compareBy<Pair<T, Float>> { it.second })
        toCheck.add(start to 0f)
        val cameFrom = mutableMapOf(start to start)
        val costSoFar = mutableMapOf(start to 0.0f)
        while (toCheck.isNotEmpty()) {
            val (current, _) = toCheck.remove()
            if (current == goal) {
                break
            }
            for (next in graph.neighbours(current)) {
                val nextCost = costSoFar.getValue(current) + graph.cost(current, next)
                if (nextCost < maxCost && nextCost < costSoFar.getOrDefault(next, Float.MAX_VALUE)) {
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
     * @param maxCost the maximum cost allowed to find a solution. No cost limit applied if not provided.
     */
    fun <T> aStar(
        graph: WeightedGraph<T>,
        start: T,
        goal: T,
        heuristic: (T, T) -> Float,
        maxCost: Float = Float.MAX_VALUE
    ): Result<T> {
        val toCheck = PriorityQueue(compareBy<Pair<T, Float>> { it.second })
        toCheck.add(start to 0f)
        val cameFrom = mutableMapOf(start to start)
        val costSoFar = mutableMapOf(start to 0.0f)
        while (toCheck.isNotEmpty()) {
            val (current, _) = toCheck.remove()
            if (current == goal) {
                break
            }
            for (next in graph.neighbours(current)) {
                val nextCost = costSoFar.getValue(current) + graph.cost(current, next)
                if (nextCost < maxCost && nextCost < costSoFar.getOrDefault(next, Float.MAX_VALUE)) {
                    toCheck.add(next to nextCost + heuristic(next, goal))
                    cameFrom[next] = current
                    costSoFar[next] = nextCost
                }
            }
        }
        return Result(cameFrom, costSoFar)
    }
}