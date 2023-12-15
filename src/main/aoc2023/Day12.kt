package aoc2023

class Day12(input: List<String>) {

    private data class Row(val springs: String, val groups: List<Int>)

    private val springs = input.map { line ->
        Row(line.substringBefore(" "), line.substringAfter(" ").split(",").map { it.toInt() })
    }

    /**
     * The current state of the processing.
     * @param groupOffset How far into the group that's currently being processed we've come
     * @param processedGroups How many complete groups have been processed
     * @param permutations The number of different permutations have come to this state
     */
    private data class State(val groupOffset: Int, val processedGroups: Int, val permutations: Long)

    /**
     * Calculate all permutations by processing one character at a time and store all valid combinations so far
     * in a list. Then process the next character for all entries in the list. Only valid states are saved,
     * invalid are discarded as soon as they are found.
     * Actions to take on each symbol that results in valid states
     *  - '.': Either continue when there is no group or finish a group of correct length
     *  - '#': Start processing a new group, or continue processing a group that is not finished yet
     *  - '?': Do the same as for both . and #. May lead to splitting one state into two for the next round.
     *
     *  After every time a character has been processed for all states, we combine the states that has the same
     *  amount of processed groups and the same group offset. Reduces the amount of states needed to be processed
     *  greatly.
     */
    private fun calculatePermutations(row: Row): Long {
        val current = mutableListOf(State(0, 0, 1L))
        // Add a trailing . to the row of springs to finish any groups that might end at the end of the row.
        row.springs.let { "$it." }.forEach { ch ->
            val next = mutableListOf<State>()
            current.forEach { current ->
                val nextGroupSize = row.groups.drop(current.processedGroups).firstOrNull() ?: 0
                when {
                    ch == '.' && current.groupOffset == 0 -> next.add(current)
                    ch == '.' && current.groupOffset == nextGroupSize -> { // Group finished
                        next.add(State(0, current.processedGroups + 1, current.permutations))
                    }

                    ch == '#' && current.groupOffset < nextGroupSize -> { // Extend a group
                        next.add(State(current.groupOffset + 1, current.processedGroups, current.permutations))
                    }

                    ch == '?' -> {
                        // Add the case for .
                        when (current.groupOffset) {
                            0 -> next.add(current)
                            nextGroupSize -> {
                                next.add(State(0, current.processedGroups + 1, current.permutations))
                            }
                        }
                        // And add the case for #
                        if (current.groupOffset < nextGroupSize) {
                            next.add(State(current.groupOffset + 1, current.processedGroups, current.permutations))
                        }
                    }
                }
            }
            current.clear()
            // combine all common states (shared group offset and processed groups) to reduce the
            // number of states to handle when processing the next character
            next.groupBy { it.groupOffset to it.processedGroups }
                .forEach { (key, value) ->
                    current.add(State(key.first, key.second, value.sumOf { it.permutations }))
                }
        }
        return current.filter { it.processedGroups == row.groups.size }.sumOf { it.permutations }

    }


    private val springs2 = input.map { line ->
        val springs = line.substringBefore(" ")
        val unfoldedSprings = List(5) { springs }.joinToString("?")
        val unfoldedGroups = List(5) { line.substringAfter(" ") }.joinToString(",")
        Row(unfoldedSprings, unfoldedGroups.split(",").map { it.toInt() })
    }

    fun solvePart1(): Long {
        return springs.sumOf { calculatePermutations(it) }
    }

    fun solvePart2(): Long {
        return springs2.sumOf { calculatePermutations(it) }
    }
}