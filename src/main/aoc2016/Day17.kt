package aoc2016

class Day17(private val input: String) {

    private data class State(val x: Int, val y: Int, val history: String)

    private fun walkMaze(passcode: String, findShortestRoute: Boolean = true): String {
        val toCheck = mutableListOf(State(1, 1, ""))
        val isOpen = "bcdef"
        var longest = ""

        while (toCheck.size > 0) {
            val state = toCheck.removeAt(0)
            if (state.x == 4 && state.y == 4) {
                if (findShortestRoute) {
                    return state.history
                }
                // BFS, every new match is always at least as long as the previous one
                longest = state.history
                continue
            }

            val hash = "$passcode${state.history}".md5()
            if (state.y > 1 && isOpen.contains(hash[0])) {
                toCheck.add(State(state.x, state.y - 1, state.history + "U"))
            }
            if (state.y < 4 && isOpen.contains(hash[1])) {
                toCheck.add(State(state.x, state.y + 1, state.history + "D"))
            }
            if (state.x > 1 && isOpen.contains(hash[2])) {
                toCheck.add(State(state.x - 1, state.y, state.history + "L"))
            }
            if (state.x < 4 && isOpen.contains(hash[3])) {
                toCheck.add(State(state.x + 1, state.y, state.history + "R"))
            }
        }
        return longest

    }

    fun solvePart1(): String {
        return walkMaze(input)
    }

    fun solvePart2(): Int {
        return walkMaze(input, false).length
    }
}
