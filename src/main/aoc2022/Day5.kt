package aoc2022

class Day5(input: List<String>) {

    /**
     * A list of all stacks, the top of each stack is position 0 in each stack.
     */
    private val stacks = parseStacks(input.first().split("\n"))

    /**
     * List of instructions, first entry is how many to move, second from index and last to index.
     */
    private val instructions = input.last().split("\n").map {
        val res = "move (\\d+) from (\\d) to (\\d)".toRegex().find(it)!!.groupValues
        listOf(res[1].toInt(), res[2].toInt() - 1, res[3].toInt() - 1)
    }

    /**
     * Parse the stack part of the input. Each stack takes up 4 characters of the input. Example line:
     *                 [B] [L]     [J]
     * 12341234123412341234123412341234
     *
     * Second character in the chunk for each stack holds the container name.
     */
    private fun parseStacks(stacks: List<String>): List<MutableList<Char>> {
        val numStacks = stacks.last().trim().substringAfterLast(" ").toInt()
        val ret = List(numStacks) { mutableListOf<Char>() }
        stacks.dropLast(1).map { layer ->
            layer.chunked(4).forEachIndexed { index, s ->
                if (s.isNotBlank()) {
                    ret[index].add(s[1])
                }
            }
        }
        return ret
    }

    /**
     * Solve the problem by either moving all containers at once (same order as in 'from' stack) or
     * one at a time (reverse order as in 'from' stack).
     */
    private fun solve(oneAtATime: Boolean): String {
        instructions.forEach { (n, from, to) ->
            // Remove items from 'from' and put them in a new list in the same order.
            val toMove = MutableList(n) { stacks[from].removeAt(0) }
                .apply { if (oneAtATime) this.reverse() } // Reverse if needed
            stacks[to].addAll(0, toMove)
        }
        return stacks.joinToString("") { it.first().toString() }
    }

    fun solvePart1(): String {
        return solve(true)
    }

    fun solvePart2(): String {
        return solve(false)
    }
}