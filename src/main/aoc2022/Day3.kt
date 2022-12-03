package aoc2022

class Day3(input: List<String>) {

    /**
     * List of rucksacks, each rucksack have two compartment with a set of items that the compartment holds.
     */
    private val rucksacks = input.map { line ->
        line.chunked(line.length / 2) { it.toList().toSet() }
    }

    private val priorities = let {
        var priority = 1
        (('a'..'z') + ('A'..'Z')).associateWith { priority++ }
    }

    private fun Char.priority() = priorities.getValue(this)

    /**
     * List of elf groups, each elf in each group have a rucksack with a set containing all items
     * (ignoring the compartments)
     */
    private val elfGroups = input.map { it.toList().toSet() }.chunked(3)

    fun solvePart1(): Int {
        return rucksacks.sumOf { (it[0] intersect it[1]).single().priority() }
    }

    fun solvePart2(): Int {
        return elfGroups.sumOf { (it[0] intersect it[1] intersect it[2]).single().priority() }
    }
}