package aoc2020

class Day23(input: String) {

    private val nineCups = input.map { it.toString().toInt() }

    private val millionCups = (0 until 1_000_000).map {
        if (it < 9) input[it].toString().toInt() else it + 1
    }

    // cups: (3) 8  9  1  2  5  4  6  7
    // Index: 1, 2, 3, 4, 5, 6, 7, 8, 9
    // value: 2  5  8  6  4  7  3  9  1
    private data class Cups(val capacity: Int) {
        // Index = cup (label), value = index of next cup
        val data = IntArray(capacity + 1) // item at position 0 is not used, cup 1 is at index 1.
        fun init(input: List<Int>) {
            input.indices.forEach { index ->
                data[input[index]] = input[(index + 1) % input.size]
            }
        }

        fun print(): String {
            var ret = ""
            var label = 1
            repeat(capacity - 1) {
                val next = data[label]
                ret += next
                label = next
            }
            return ret
        }
    }

    /**
     * There is only remove/insert and access based on cup name, so use
     * an array based linked list. The index of the array is the label
     * of the cup (which we need to access) and the value is a pointer
     * to the next item in the list.
     */
    private fun play(moves: Int, cups: Cups, startCup: Int): Cups {
        var currentCup = startCup
        repeat(moves) {
            val firstRemoved = cups.data[currentCup]
            val middleRemoved = cups.data[firstRemoved]
            val lastRemoved = cups.data[middleRemoved]
            val afterRemoval: Int = cups.data[lastRemoved]
            val destinationCup = generateSequence(currentCup) { if (it == 1) cups.capacity else it - 1 }
                    .drop(1)
                    .first { it !in listOf(firstRemoved, middleRemoved, lastRemoved) }
            val afterDestinationCup = cups.data[destinationCup]

            cups.data[currentCup] = afterRemoval
            cups.data[destinationCup] = firstRemoved
            cups.data[lastRemoved] = afterDestinationCup

            currentCup = afterRemoval
        }
        return cups
    }

    fun solvePart1(): String {
        val cups = Cups(9).apply { init(nineCups) }
        play(100, cups, nineCups.first())
        return cups.print()
    }

    fun solvePart2(): Long {
        val cups = Cups(1_000_000).apply { init(millionCups) }
        play(10_000_000, cups, millionCups.first())
        return cups.data[1].toLong() * cups.data[cups.data[1]].toLong()
    }
}
