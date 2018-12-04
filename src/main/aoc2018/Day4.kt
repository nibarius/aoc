package aoc2018

class Day4(input: List<String>) {
    private val guards = parseInput(input)

    private fun parseInput(input: List<String>): MutableMap<Int, IntArray> {
        val sorted = input.sorted()
        var currentGuard = -1
        var i = 0

        // Map of all guards, value is an array where each entry specifies how many days the guard was
        // sleeping during that minute
        val guards = mutableMapOf<Int, IntArray>()
        while (i < sorted.size) {
            val line = sorted[i++]
            // Example lines to parse (falls asleep is always followed by wakes up):
            // [1518-11-01 00:00] Guard #10 begins shift
            // [1518-11-01 00:05] falls asleep
            // [1518-11-01 00:25] wakes up
            if (line.endsWith("begins shift")) {
                currentGuard = line.substringAfter("#").substringBefore(" ").toInt()
                guards.putIfAbsent(currentGuard, IntArray(60))
                continue
            }

            val fallAsleep = line.substringAfter(":").take(2).toInt()
            val wakeup = sorted[i++].substringAfter(":").take(2).toInt()
            (fallAsleep until wakeup).forEach { guards.getValue(currentGuard)[it]++ }
        }
        return guards
    }

    private fun findGuardMinuteCombination(strategy: (Map.Entry<Int, IntArray>) -> Int): Int {
        val snooziestGuard = guards.maxBy { strategy(it) }!!
        val snooziestMinute = snooziestGuard.value.withIndex().maxBy { it.value }!!.index
        return snooziestGuard.key * snooziestMinute
    }

    fun solvePart1(): Int {
        // Find the guard with most total sleep time
        return findGuardMinuteCombination { guards -> guards.value.sum() }
    }

    fun solvePart2(): Int {
        // Find the guard with the maximum sleep time for a given minute
        return findGuardMinuteCombination { guards -> guards.value.max()!! }
    }
}