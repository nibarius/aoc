package aoc2024

class Day25(input: List<String>) {
    private val schematics = input.map { Schematic.parse(it.split("\n")) }

    private data class Schematic(val type: Char, val columns: List<Int>) {
        fun fits(other: Schematic): Boolean {
            return type != other.type && columns.zip(other.columns).all { (a, b) -> a + b <= 5 }
        }

        companion object {
            fun parse(input: List<String>): Schematic {
                val type = input.first().first()
                val columns = (0..4).map { column -> input.count { it[column] == '#' } - 1 }
                return Schematic(if (type == '#') 'l' else 'k', columns)
            }
        }
    }

    fun solvePart1(): Int {
        val keys = schematics.filter { it.type == 'k' }
        val locks = schematics.filter { it.type == 'l' }
        return locks.sumOf { lock -> keys.count { key -> lock.fits(key) } }
    }

    fun solvePart2(): Int {
        return -1
    }
}