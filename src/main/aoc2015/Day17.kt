package aoc2015

class Day17(input: List<String>) {

    val parsedInput = input.map { it.toInt() }

    private fun fill(capacity: Int, emptyContainers: List<Int>, usedContainers: List<Int>, combinations: MutableList<List<Int>>) {
        if (capacity == 0) {
            combinations.add(usedContainers)
            return
        }

        val candidates = emptyContainers.filter { it <= capacity }
        if (candidates.isEmpty()) {
            return
        }

        val first = candidates.first()
        val rest = candidates.drop(1)

        // Either a container is used...
        fill(capacity - first,
                rest,
                usedContainers.toMutableList().apply { add(first) },
                combinations)
        // ... or it's not used
        fill(capacity, rest, usedContainers, combinations)
    }

    private fun findAllCombinations(capacity: Int): MutableList<List<Int>> {
        val allCombinations = mutableListOf<List<Int>>()
        fill(capacity, parsedInput, listOf(), allCombinations)
        return allCombinations
    }

    fun solvePart1(totalCapacity: Int = 150): Int {
        return findAllCombinations(totalCapacity).size
    }

    fun solvePart2(totalCapacity: Int = 150): Int {
        val combinations = findAllCombinations(totalCapacity)
        val min = combinations.minByOrNull { it.size }!!.size
        return combinations.filter { it.size == min }.size
    }
}