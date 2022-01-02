package aoc2019

class Day6(input: List<String>) {

    // child to parent map
    private val parsedInput = input.map { it.split(")") }.associate { it[1] to it[0] }

    private tailrec fun countParents(child: String, parents: Int = 0): Int {
        return if (child == "COM") {
            parents
        } else {
            countParents(parsedInput.getValue(child), 1 + parents)
        }
    }

    fun solvePart1(): Int {
        return parsedInput.keys.sumOf { countParents(it) }
    }

    private fun allParents(child: String): List<String> {
        val parents = mutableListOf<String>()
        var currentChild = child
        do {
            currentChild = parsedInput.getValue(currentChild)
            parents.add(currentChild)
        } while (currentChild != "COM")
        return parents.reversed()
    }

    fun solvePart2(): Int {
        val you = allParents("YOU")
        val san = allParents("SAN")
        val commonParent = you.zip(san).last { it.first == it.second }.first
        val sharedDistance = countParents(commonParent)
        return you.size - sharedDistance - 1 +
                san.size - sharedDistance - 1
    }
}