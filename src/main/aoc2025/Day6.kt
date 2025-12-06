package aoc2025

class Day6(val input: List<String>) {
    /**
     * Read the input left-to-right (and top-down) and create a
     * list of all columns in the input.
     */
    private fun readLeftToRight(): List<List<String>> {
        // Split input on whitespace so each row becomes a list of strings
        val data = input.mapNotNull { line ->
            line.trim().split("\\s+".toRegex()).takeIf { it.isNotEmpty() }
        }

        // Pivot the data so that each entry in the list is a column instead of a row.
        return data.first().indices.map { x ->
            buildList {
                for (y in data.indices) {
                    add(data[y][x])
                }
            }
        }
    }

    /**
     * Read the input right-to-left and top-down by rotating
     * the input 90 degrees counterclockwise.
     *
     * 123      36
     * 456  ==> 25
     * +        14+
     *
     * Then generate the problems based on that
     * [[36, 25, 14, +]]
     */
    private fun readTopDown(): List<List<String>> {
        val width = input[0].length
        val height = input.size
        return buildList {
            val currentProblem = mutableListOf<String>()
            for (x in width - 1 downTo 0) {
                val number = buildString {
                    for (y in 0 until height) {
                        append(input[y][x])
                    }
                }
                if (number.last() != ' ') {
                    // this number ends with the operator, split it into
                    // the actual number and the operator and finalize
                    // the current problem.
                    currentProblem.add(number.dropLast(1).trim())
                    currentProblem.add(number.takeLast(1))
                    add(currentProblem.toList())
                    currentProblem.clear()
                } else if (number.trim().isNotEmpty()) {
                    // add numbers to the current problem (and ignore the blank
                    // separator column)
                    currentProblem.add(number.trim())
                }
            }
        }
    }


    private fun solveProblem(problem: List<String>): Long {
        val op: (Long, Long) -> Long = if (problem.last() == "+") Long::plus else Long::times
        val numbers = problem.dropLast(1).map { it.toLong() }
        return numbers.reduce(op)
    }

    fun solvePart1(): Long {
        return readLeftToRight().sumOf { solveProblem(it) }
    }

    fun solvePart2(): Long {
        return readTopDown().sumOf { solveProblem(it) }
    }
}