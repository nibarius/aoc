package aoc2019

import kotlin.math.max


class Day22(val input: List<String>) {

    sealed class Technique {
        abstract fun nextPositionForCard(card: Long, deckSize: Long): Long
        data class Cut(val n: Long) : Technique() {
            override fun nextPositionForCard(card: Long, deckSize: Long) = Math.floorMod(card - n, deckSize)
        }

        data class Increment(val n: Long) : Technique() {
            override fun nextPositionForCard(card: Long, deckSize: Long) = Math.floorMod(card * n, deckSize)
        }

        companion object {
            fun fromString(input: String, deckSize: Long): List<Technique> {
                fun String.getNumber() = this.split(" ").last().toLong()
                return when {
                    input.startsWith("deal with") -> listOf(Increment(input.getNumber()))
                    input.startsWith("cut") -> listOf(Cut(input.getNumber()))
                    input.startsWith("deal into") -> listOf(
                            Increment(deckSize - 1),
                            Cut(1)
                    )
                    else -> throw IllegalStateException("Unknown shuffle technique: $input")
                }
            }
        }

        // Multiplying two very large longs will overflow, so use BigInteger to calculate
        // "a * b % mod" to avoid overflows
        private fun mulMod(a: Long, b: Long, mod: Long): Long {
            return a.toBigInteger().multiply(b.toBigInteger()).mod(mod.toBigInteger()).longValueExact()
        }

        // Rules for combining techniques from:
        // https://www.reddit.com/r/adventofcode/comments/ee56wh/2019_day_22_part_2_so_whats_the_purpose_of_this/fc0xvt5/
        fun combine(other: Technique, deckSize: Long): List<Technique> {
            return when {
                this is Cut && other is Cut -> listOf(Cut(Math.floorMod(n + other.n, deckSize)))
                this is Increment && other is Increment -> listOf(Increment(mulMod(n, other.n, deckSize)))
                this is Cut && other is Increment -> listOf(Increment(other.n), Cut(mulMod(n, other.n, deckSize)))
                else -> throw IllegalStateException("Invalid technique combination: $this and $other")
            }
        }

        // Everything except Increment followed by Cut can be combined
        fun canBeCombinedWith(other: Technique) = !(this is Increment && other is Cut)
    }

    fun parseInput(deckSize: Long) = input.map { Technique.fromString(it, deckSize) }.flatten()

    // Find the position that the given card will have after the shuffle process has been finished
    fun finalPositionForCard(card: Long,
                             deckSize: Long,
                             process: List<Technique> = reduceShuffleProcess(deckSize, parseInput(deckSize))): Long {
        return process.fold(card) { pos, technique -> technique.nextPositionForCard(pos, deckSize) }
    }

    // Reduce the shuffle process to just two techniques by combining techniques that
    // lies next to each other
    private fun reduceShuffleProcess(deckSize: Long, initialProcess: List<Technique>): List<Technique> {
        var process = initialProcess
        while (process.size > 2) {
            var offset = 0
            while (offset < process.size - 1) {
                if (process[offset].canBeCombinedWith(process[offset + 1])) {
                    // Combine current + next technique into one
                    val combined = process[offset].combine(process[offset + 1], deckSize)
                    process = process.subList(0, offset) + combined + process.subList(offset + 2, process.size)
                    // Next time try to combine previous technique (if there is any) with the new one
                    offset = max(0, offset - 1)
                } else {
                    // Not possible to combine current + next, step ahead to check the next two
                    offset++
                }
            }
        }
        return process
    }

    fun solvePart1(): Int {
        return finalPositionForCard(2019L, 10007L).toInt()
    }

    // Create a reduced shuffle process repeated the given number of times by doubling the amount
    // of iterations until reaching the final count
    @Suppress("SameParameterValue")
    private fun repeatShuffleProcess(process: List<Technique>, times: Long, deckSize: Long): List<Technique> {
        var current = process
        val res = mutableListOf<Technique>()
        // iterate trough the bits in the binary representation of the number of times to repeat
        // from least significant to most significant
        for (bit in times.toString(2).reversed()) {
            if (bit == '1') {
                // Obviously, a number is the sum of the value of all the ones in the binary representation
                // Store the process for all bits that are set
                res.addAll(current)
            }
            // double the amount of iterations in the shuffle process and reduce it to two operations
            current = reduceShuffleProcess(deckSize, current + current)
        }
        // res now holds all the steps needed to repeat the shuffle process the given number of times,
        // do a final reduction to get a reduced process with only two steps
        return reduceShuffleProcess(deckSize, res)
    }


    // Draws a lot of inspiration from:
    // https://www.reddit.com/r/adventofcode/comments/ee56wh/2019_day_22_part_2_so_whats_the_purpose_of_this/fbr0vjb/
    fun solvePart2(): Long {
        val deckSize = 119_315_717_514_047
        val repeats = 101_741_582_076_661
        val targetPosition = 2020L

        // Deck becomes sorted again after every deckSize - 1 repeats of the shuffle
        // according to Euler's Theorem as mentioned in:
        // https://www.reddit.com/r/adventofcode/comments/ee56wh/2019_day_22_part_2_so_whats_the_purpose_of_this/fbs6s6z/
        //
        // We're interested in what cards ends up at position 2020 after 'repeats' number of repeats of the shuffle
        // process. Calculate the number of times extra that the shuffle process has to be done to get to the
        // original state.
        val shufflesLeftUntilInitialState = deckSize - 1 - repeats

        // If we run the shuffle process 'shufflesLeftUntilInitialState' times and see at what position
        // the card that was at position 2020 ends up at we have the answer to the problem.

        // So first create a reduced shuffle process of two steps with the desired amount of shuffles
        val reduced = reduceShuffleProcess(deckSize, parseInput(deckSize))
        val repeated = repeatShuffleProcess(reduced, shufflesLeftUntilInitialState, deckSize)

        // Then check where the card at 2020 moves to with this shuffle process.
        return finalPositionForCard(targetPosition, deckSize, repeated)
    }
}
