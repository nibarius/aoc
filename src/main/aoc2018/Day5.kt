package aoc2018

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.magicwerk.brownies.collections.GapList
import kotlin.math.abs
import kotlin.math.max

class Day5(private val input: String) {

    private infix fun Char.reactsWith(other: Char) = abs(this - other) == 32

    private fun react(input: String): String {
        val ret = GapList<Char>(input.length)
        ret.addAll(input.toList())
        var i = 0
        while (i < ret.size - 1) {
            if (ret[i] reactsWith ret[i + 1]) {
                ret.remove(i, 2)
                i = max(0, i - 1)
            } else {
                i++
            }
        }
        return ret.joinToString("")
    }

    fun solvePart1(): Int {
        return react(input).length
    }

    fun solvePart2(): Int {
        return runBlocking {
            input.groupBy { it.lowercaseChar() }.keys // Letters to check
                .map { toRemove ->
                    async {
                        // Check all letters in parallel
                        react(input.replace("$toRemove", "", true)).length
                    }
                }
                    .minByOrNull { it.await() }!!.await() // return the shortest length
        }
    }
}