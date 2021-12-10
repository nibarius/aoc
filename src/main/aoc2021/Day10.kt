package aoc2021

class Day10(val input: List<String>) {
    companion object {
        private val opposite = mapOf('{' to '}', '[' to ']', '(' to ')', '<' to '>')
        private val part1Scores = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)

        // We have a list of leftover opening characters without any missing characters
        // so scores need to be assigned to the corresponding opening character
        private val part2Values = mapOf('(' to 1, '[' to 2, '{' to 3, '<' to 4)
    }

    private sealed interface Result {
        class Corrupt(val invalid: Char) : Result {
            fun score() = part1Scores.getValue(invalid)
        }

        class Incomplete(val missing: List<Char>) : Result {
            fun score() = missing.fold(0L) { acc, c -> acc * 5 + part2Values.getValue(c) }
        }
    }

    private fun parse(line: String): Result {
        val stack = ArrayDeque<Char>()
        line.forEach { ch ->
            when {
                ch in opposite -> stack.add(ch)
                ch != opposite[stack.removeLast()] -> return Result.Corrupt(ch)
            }
        }
        return Result.Incomplete(stack.reversed())
    }


    fun solvePart1(): Int {
        return input
            .map { parse(it) }
            .filterIsInstance<Result.Corrupt>()
            .sumOf { it.score() }
    }

    fun solvePart2(): Long {
        return input
            .map { parse(it) }
            .filterIsInstance<Result.Incomplete>()
            .map { it.score() }
            .sorted()
            .let { it[it.size / 2] }
    }
}
