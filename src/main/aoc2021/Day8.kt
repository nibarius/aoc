package aoc2021

class Day8(input: List<String>) {
    val parsed = input.map { it.split(" | ") }

    fun solvePart1(): Int {
        return parsed.sumOf { line -> line.last().split(" ").count { it.length in listOf(2, 3, 4, 7) } }
    }


    /**
     * Remove the first entry that matches the given condition and return it.
     */
    private fun <E> MutableList<E>.removeFirst(function: (E) -> Boolean): E {
        return removeAt(indexOfFirst { function(it) })
    }

    /*
   Segment positions:
         1111
        2    3
        2    3
         4444
        5    6
        5    6
         7777
     */

    private fun processOne(input: List<String>): Int {
        val pattern = input.first().split(" ")
        val remainingPatterns = pattern.map { it.toSet() }.toMutableList()
        val segmentPositionToWire = mutableMapOf<Int, Char>()
        val digitToWires = mutableMapOf<Int, Set<Char>>()

        // First identify the patterns that can only be one digit
        digitToWires[1] = remainingPatterns.removeFirst { it.size == 2 }
        digitToWires[4] = remainingPatterns.removeFirst { it.size == 4 }
        digitToWires[7] = remainingPatterns.removeFirst { it.size == 3 }
        digitToWires[8] = remainingPatterns.removeFirst { it.size == 7 }

        // Segment position 1 is the wire that's present in digit 7 but not in digit 1
        segmentPositionToWire[1] = (digitToWires[7]!! - digitToWires[1]!!).single()

        // The digit 6 has 6 segments, and it lacks one segment that digit 1 has.
        digitToWires[6] = remainingPatterns.removeFirst {
            it.size == 6 && !it.containsAll(digitToWires[1]!!)
        }

        // Segment position 3 is the wire that's missing from digit 6
        segmentPositionToWire[3] = (digitToWires[8]!! - digitToWires[6]!!).single()

        // Segment position 6 is the wire left after segment position 3 is removed from digit 1
        segmentPositionToWire[6] = (digitToWires[1]!! - setOf(segmentPositionToWire[3]!!)).single()

        // The digit 5 has 5 segments, and it lacks segment position 3
        digitToWires[5] = remainingPatterns.removeFirst {
            it.size == 5 && !it.contains(segmentPositionToWire[3])
        }

        // Starting with all segments, and subtracting all from 5 leaves segment position 3 and 5.
        // Subtracting all segments from 1 leaves only segment position 5
        segmentPositionToWire[5] = (digitToWires[8]!! - digitToWires[5]!! - digitToWires[1]!!).single()

        // Digit 9 has 6 segments and lacks segment 5
        digitToWires[9] = remainingPatterns.removeFirst {
            it.size == 6 && !it.contains(segmentPositionToWire[5])
        }

        // Digit 2 has 5 segments and lacks segment 6
        digitToWires[2] = remainingPatterns.removeFirst {
            it.size == 5 && !it.contains(segmentPositionToWire[6])
        }

        // Digit 3 has 5 segments and lacks segment 5
        digitToWires[3] = remainingPatterns.removeFirst {
            it.size == 5 && !it.contains(segmentPositionToWire[5])
        }

        // Now only digit 0 remains
        digitToWires[0] = remainingPatterns.single()

        // Segment position 4 is the wire that's missing from digit 0
        segmentPositionToWire[4] = (digitToWires[8]!! - digitToWires[0]!!).single()

        // Starting with all segments, and subtracting all from 2 leaves segment position 2 and 6.
        // Subtracting all segments from 1 leaves only segment position 2
        segmentPositionToWire[2] = (digitToWires[8]!! - digitToWires[2]!! - digitToWires[1]!!).single()

        // Segment position 7 is the only thing that remains
        segmentPositionToWire[7] = (digitToWires[8]!! - segmentPositionToWire.values.toSet()).single()

        // All segments and digits identified, map from wires to digits
        val wiresToDigits = digitToWires.map { (k, v) -> v to k }.toMap()

        return input.last()
            .split(" ")
            .map { wiresToDigits[it.toSet()]!! }
            .joinToString("")
            .toInt()
    }

    fun solvePart2(): Int {
        return parsed.sumOf { processOne(it) }
    }
}

