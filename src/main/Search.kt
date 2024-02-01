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
 * - Use maximizeValueDfs when a maximum value/cost should be found rather than the minimal, and it is possible
 *   to estimate the final value without doing an underestimation. This is a DFS algorithm so with a good estimate
 *   it's possible to cut off many non-viable branches very early on and find the optimal result quickly.
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
     * @param cameFrom shows how to get to a certain location. `cameFrom[B] == A` means that the path
     * to B comes from A. The start point points back to itself, so  `cameFrom[A] == A`.
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

    /**
     * A* but rather than searching for a known goal position it takes a goal function and keep searching until
     * the goal function returns true. The cost map in the result may contain many nodes that fulfill the goal
     * function as nodes are inserted into the cost map when they are queued for continued searching. The returned
     * node is the optimal one that triggered the goal function first.
     *
     * @param graph the graph to search
     * @param start the location to start the search from
     * @param goalFn a function that takes a node and should return true when the goal is found.
     * @param heuristic a heuristic function that estimates the cost to reach the goal. Start location
     * is given as parameter and the return value is the estimated cost to move from start to goal. Must never
     * overestimate the cost to reach the goal.
     * @param maxCost the maximum cost allowed to find a solution. No cost limit applied if not provided.
     * @return A pair of the node that triggered the goal function to return true to the search Result. If the goal
     *         isn't found the start node is returned instead.
     */
    fun <T> aStar(
        graph: WeightedGraph<T>,
        start: T,
        goalFn: (T) -> Boolean,
        heuristic: (T) -> Float,
        maxCost: Float = Float.MAX_VALUE
    ): Pair<T, Result<T>> {
        val toCheck = PriorityQueue(compareBy<Pair<T, Float>> { it.second })
        toCheck.add(start to 0f)
        val cameFrom = mutableMapOf(start to start)
        val costSoFar = mutableMapOf(start to 0.0f)
        while (toCheck.isNotEmpty()) {
            val (current, _) = toCheck.remove()
            if (goalFn(current)) {
                return current to Result(cameFrom, costSoFar)
            }
            for (next in graph.neighbours(current)) {
                val nextCost = costSoFar.getValue(current) + graph.cost(current, next)
                if (nextCost < maxCost && nextCost < costSoFar.getOrDefault(next, Float.MAX_VALUE)) {
                    toCheck.add(next to nextCost + heuristic(next))
                    cameFrom[next] = current
                    costSoFar[next] = nextCost
                }
            }
        }
        return start to Result(cameFrom, costSoFar)
    }

    /**
     * Search the given graph using a DFS approach in a way that maximises the value (rather than minimizing
     * the cost).
     *
     * @param graph The graph to search, cost is treated as value that should be maximised
     * @param start The start node
     * @param isGoal Function that returns true if the state it's given is a goal state
     * @param estimateBestCase Function that estimates the final value given a state. It must never underestimate
     *        the actual value. If it does the best solution might not be found.
     * @param accumulateCost If the cost should be accumulated between steps. Default is false, set to true if each
     *        state contains the total cost so far.
     * @return A pair of best state to Result which holds information about all visited nodes during the search.
     */
    fun <T> maximizeValueDfs(
        graph: WeightedGraph<T>,
        start: T,
        isGoal: (T) -> Boolean,
        estimateBestCase: (T) -> Float,
        accumulateCost: Boolean = true
    ): Pair<T, Result<T>> {
        val cameFrom = mutableMapOf(start to start)
        val costSoFar = mutableMapOf(start to 0f)
        val ret = dfs(graph, start, isGoal, estimateBestCase, start to 0f, cameFrom, costSoFar, accumulateCost)
        return ret.first to Result(cameFrom, costSoFar)
    }

    /**
     * Use recursive dfs to find the node with the highest value (cost). Since a heuristic is used to prune
     * branches that has no chance of beating the current best a recursive approach is better (faster) than an
     * iterative approach. This is because an iterative approach will have to enqueue a lot of states that could
     * otherwise be pruned away while it's iterating toward maximum depth.
     *
     * @return a pair of best state to the value (cost) at that state.
     */
    private fun <T> dfs(
        graph: WeightedGraph<T>,
        current: T,
        isGoal: (T) -> Boolean,
        estimateBestCase: (T) -> Float,
        previousBest: Pair<T, Float>,
        cameFrom: MutableMap<T, T>,
        costSoFar: MutableMap<T, Float>,
        accumulateCost: Boolean = true
    ): Pair<T, Float> {
        if (isGoal(current)) {
            return current to costSoFar.getValue(current)
        }
        var newBest = previousBest
        graph.neighbours(current)
            .filter {
                // Only examine states that can beat current best
                val soFar = if (accumulateCost) costSoFar.getValue(current) + graph.cost(current, it) else 0f
                soFar + estimateBestCase(it) > newBest.second
            }
            .forEach { next ->
                cameFrom[next] = current
                costSoFar[next] = graph.cost(current, next) + if (accumulateCost) costSoFar.getValue(current) else 0f
                val ret = dfs(graph, next, isGoal, estimateBestCase, newBest, cameFrom, costSoFar, accumulateCost)
                if (ret.second > newBest.second) {
                    newBest = ret
                }
            }
        return newBest
    }
}