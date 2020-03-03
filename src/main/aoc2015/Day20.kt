package aoc2015

class Day20(input: String) {
    private val magicNumber = input.toInt()

    private fun visitHouses(presentsPerHouse: Int, maxVisits: Int): Int {
        val houses = IntArray(1000000) // Just an arbitrary large number
        for (elf in 1 until houses.size) {
            var house = elf
            var visits = 0
            while (house < houses.size && ++visits <= maxVisits) {
                houses[house] += presentsPerHouse * elf
                house += elf
            }
        }
        return houses.withIndex().first { it.value >= magicNumber }.index
    }

    fun solvePart1(): Int {
        return visitHouses(10, 100000)
    }

    fun solvePart2(): Int {
        return visitHouses(11, 50)
    }
}