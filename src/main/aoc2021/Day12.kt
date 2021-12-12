package aoc2021

class Day12(input: List<String>) {

    private val graph = parseInput(input)

    private fun String.isEnd() = equals("end")
    private fun String.isStart() = equals("start")
    private fun String.isSmall() = first().isLowerCase() // It's all lowercase or all uppercase

    private fun parseInput(input: List<String>): Map<String, Set<String>> {
        return mutableMapOf<String, Set<String>>()
            .withDefault { setOf() }
            .apply {
                input.map { it.split("-") }
                    .forEach { (first, second) ->
                        this[first] = getValue(first) + second
                        this[second] = getValue(second) + first
                    }
            }
    }

    private fun canVisit(node: String, visited: List<String>, revisitsLeft: Int): Boolean {
        return when {
            !node.isSmall() -> true // can always go to large caves
            node.isStart() -> false // can't go back to the start
            revisitsLeft > 0 -> true // it's still possible to revisit small caves
            node in visited -> false // no further revisits are allowed
            else -> true
        }
    }

    /**
     * Recursive function to count the number of paths leading to the end.
     */
    private fun countPaths(currentNode: String, visited: List<String>, revisitsRemaining: Int): Int {
        if (currentNode.isEnd()) {
            return 1
        }

        val possibilities = graph.getValue(currentNode).filter { canVisit(it, visited, revisitsRemaining) }
        val nextVisited = visited.toMutableList().also { it.add(currentNode) }
        return possibilities.sumOf { next ->
            val remaining = if (next.isSmall() && next in visited) revisitsRemaining - 1 else revisitsRemaining
            countPaths(next, nextVisited, remaining)
        }
    }

    fun solvePart1(): Int {
        return countPaths("start", listOf(), 0)
    }

    fun solvePart2(): Int {
        return countPaths("start", listOf(), 1)
    }
}