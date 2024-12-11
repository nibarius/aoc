package aoc2024

class Day11(input: List<String>) {

    val initial = input.first().split(" ").map { it.toLong() }

    private fun Long.split(): List<Long> {
        val str = this.toString()
        val half = str.length / 2
        return listOf(str.substring(0, half).toLong(), str.substring(half).toLong())
    }

    private fun blink(stone: Long): List<Long> {
        return when {
            stone == 0L -> listOf(1)
            stone.toString().length % 2 == 0 -> stone.split()
            else -> listOf(stone * 2024)
        }
    }

    // memory is a map of (stone number to blinks left) pairs as key
    // and with the number of stones it results in when done blinking
    private fun blink(currentStone: Long, blinksLeft: Int, memory: MutableMap<Pair<Long, Int>, Long>): Long {
        val key = Pair(currentStone, blinksLeft)
        return when {
            memory.containsKey(key) -> memory[key]!!
            blinksLeft == 0 -> 1L.also { memory[key] = it }
            else -> blink(currentStone)
                .sumOf { stone -> blink(stone, blinksLeft - 1, memory) }
                .also { memory[key] = it }
        }
    }

    private fun stonesAfterBlinking(blinks: Int): Long {
        return initial.sumOf { blink(it, blinks, mutableMapOf()) }
    }

    fun solvePart1(): Long {
        return stonesAfterBlinking(25)
    }

    fun solvePart2(): Long {
        return stonesAfterBlinking(75)
    }
}