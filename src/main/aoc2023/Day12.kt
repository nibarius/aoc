package aoc2023

class Day12(input: List<String>) {

    private data class State(val known: String, val unknown: String, val groups: List<Int>) {

        fun neighbours(): List<State> {
            when {
                unknown.isEmpty() -> return listOf()
                unknown[0] == '.' -> return listOf(State("$known.", unknown.drop(1), groups))
            }
            val ret = mutableListOf<State>()
            // One option with unknown states is to treat it as .
            if (unknown[0] == '?') {
                ret.add(State("$known.", unknown.drop(1), groups))
            }

            if (groups.isEmpty()) {
                // All . are eaten in the beginning. All ? just above
                // any remaining # would end up with an empty ret and return since it's not viable
                return ret
            }

            // First the same number of # and ? as in the next group followed by one ?, . or end of string
            val len = groups.first()
            val regex = """^[#?]{$len}(\.|\?|$)""".toRegex()
            if (unknown.contains(regex)) {
                val toAdd = "#".repeat(len) + "."
                ret.add(State("$known$toAdd", unknown.drop(toAdd.length), groups.drop(1)))
            }
            return ret
        }

    }

    data class Row(val springs: String, val groups: List<Int>) {

        private fun dfs(current: State): Long {
            if (current.groups.isEmpty() && current.unknown.isEmpty()) {
                return 1L
            } else if (current.unknown.isEmpty()) {
                return 0L
            } else if (current.unknown.length < current.groups.sumOf { it } + current.groups.size - 1) {
                // The remaining part is too small, no chance to find a solution
                return 0L
            }
            return current.neighbours().sumOf { dfs(it) }
        }

        fun calculateArrangements2(): Long {
            return dfs(State("", springs, groups))
        }
    }

    private val springs = input.map { line ->
        Row(line.substringBefore(" "), line.substringAfter(" ").split(",").map { it.toInt() })
    }

    private val springs2 = input.map { line ->
        val springs = line.substringBefore(" ")
        val unfoldedSprings = List(5) { springs }.joinToString("?")
        val unfoldedGroups = List(5){ line.substringAfter(" ") }.joinToString(",")
        Row(unfoldedSprings, unfoldedGroups.split(",").map { it.toInt() })
    }

    fun solvePart1(): Long {
        return springs.sumOf { it.calculateArrangements2() }
    }

    fun solvePart2(): Long {

        return springs2.sumOf { it.calculateArrangements2() }
    }
}