package aoc2020

// Input is the input file split on \n\n (each group of passengers).
// So each entry in the list is one group. Within each group the passengers are separated by \n.
class Day6(val input: List<String>) {
    fun solvePart1(): Int {
        return input.sumOf { group ->
            group.toSet().filter { it.isLetter() }.size
        }
    }

    fun solvePart2(): Int {
        return input.sumOf {
            it.split("\n")
                    .map { person -> person.toSet() }
                    .reduce { acc, person -> acc intersect person }
                    .size
        }
    }
}