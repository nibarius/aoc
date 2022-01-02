package aoc2016

class Day4(input: List<String>) {
    companion object {
        private val alphabet = ('a'..'z').toList().joinToString("")
        fun decrypt(name: String, sectorId: Int): String {
            return name.map {
                when (it) {
                    '-' -> ' '
                    else -> alphabet[(alphabet.indexOf(it) + sectorId) % alphabet.length]
                }
            }.joinToString("")
        }
    }

    private data class Room(val name: String, val sectorId: Int, val checksum: String) {
        fun isReal(): Boolean {
            // Count number of each letter in the name
            val occurrences = mutableMapOf<Char, Int>()
            name.filterNot { it == '-' }.forEach {
                if (occurrences.containsKey(it)) {
                    occurrences[it] = occurrences[it]!! + 1
                } else {
                    occurrences[it] = 1
                }
            }
            // first number of occurrences (larger first), after that alphabetical order (lower first)
            val sorted = occurrences.toList().sortedWith(compareBy({ -it.second }, { it.first }))

            // take the letter (it.first) of the first 5 items and make a string of it
            val chk: String = sorted.take(5).map { it.first }.joinToString("")

            return chk == checksum
        }

        fun decryptName() = decrypt(name, sectorId)
    }

    private val rooms = process(input)

    private fun process(input: List<String>): List<Room> {
        val rooms = mutableListOf<Room>()
        input.forEach {
            rooms.add(Room(it.substringBeforeLast("-"),
                    it.substringAfterLast("-").substringBefore("[").toInt(),
                    it.substringAfter("[").substringBefore("]")))
        }
        return rooms
    }



    fun solvePart1(): Int {
        return rooms.filter { it.isReal() }.sumOf { it.sectorId }
    }

    fun solvePart2(): Int {
        return rooms.filter { it.isReal() }.find { it.decryptName().contains("northpole") }!!.sectorId
    }
}