package aoc2020

class Day1(input: List<String>) {
    // Most of the entries in the input will go past 2020 when summed
    // so sort the list to start with smaller numbers to find the solution faster
    private val entries = input.map { it.toInt() }.sorted()


    fun solve(partOne: Boolean = false, partTwo: Boolean = false): Int {
        // Keep a set of already seen numbers to save one for loop and reduce complexity
        // from O(n^2) to O(n) in part 1 and O(n^3) to O(n^2) in part 2
        val seen = mutableSetOf<Int>()
        for (x in entries.indices) {
            if (partOne) {
                seen.add(entries[x])
                val lookingFor = 2020 - entries[x]
                if (seen.contains(lookingFor)) {
                    return entries[x] * lookingFor
                }
            }
            if (partTwo) {
                for (y in entries.indices) {
                    if (x != y) {
                        seen.add(entries[y])
                        val lookingFor = 2020 - entries[x] - entries[y]
                        if (seen.contains(lookingFor)) {
                            return entries[x] * entries[y] * lookingFor
                        }
                    }
                }
            }
        }
        return 0
    }

    fun solvePart1(): Int {
        return solve(partOne = true)
    }

    fun solvePart2(): Int {
        return solve(partTwo = true)
    }
}