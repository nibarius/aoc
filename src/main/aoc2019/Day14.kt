package aoc2019

import kotlin.math.ceil
import kotlin.math.min

class Day14(input: List<String>) {

    data class Reaction(val req: List<Pair<Int, String>>, val res: Pair<Int, String>)

    // Material name to reaction to create the material
    private val reactions = input.map { parseLine(it) }.associateBy { it.res.second }

    // 165 ORE => 6 DCFZ
    // 44 XJWVT, 5 KHKGT, 1 QDVJ, 29 NZVS, 9 GPVTF, 48 HKGWZ => 1 FUEL
    private fun parseLine(line: String): Reaction {
        val req = mutableListOf<Pair<Int, String>>()
        val (before, after) = line.split(" => ")
        before.split(", ").forEach { req.add(getAmountMaterialPair(it)) }
        return Reaction(req, getAmountMaterialPair(after))
    }

    private fun getAmountMaterialPair(s: String): Pair<Int, String> {
        val (amount, material) = s.split(" ")
        return Pair(amount.toInt(), material)
    }

    // Returns the amount of ore needed to create the given material
    private fun create(amount: Long, material: String, available: MutableMap<String, Long>): Long {
        if (material == "ORE") {
            return amount
        }

        // If there is already some of the desired material created since an earlier reaction use that.
        val alreadyAvailable = min(amount, available.getOrDefault(material, 0))
        available[material] = available.getOrDefault(material, 0) - alreadyAvailable
        val toCreate = amount - alreadyAvailable

        // Create more materials to fill the need.
        val reaction = reactions[material] ?: error("Unknown material: $material")
        val numReactions = ceil(toCreate.toDouble() / reaction.res.first).toLong()
        val oreNeeded = reaction.req.sumByLong { create(numReactions * it.first, it.second, available) }
        val created = numReactions * reaction.res.first

        // Store any left over materials for future reactions.
        val leftOver = created - toCreate
        available[material] = available.getOrDefault(material, 0) + leftOver

        return oreNeeded
    }

    fun solvePart1(): Long {
        return create(1L, "FUEL", mutableMapOf())
    }

    fun solvePart2(): Long {
        val capacity = 1_000_000_000_000
        val oreForOne = create(1L, "FUEL", mutableMapOf())
        val lowerBound = capacity / oreForOne
        var start = lowerBound
        var end = lowerBound * 2
        var candidate = 0L

        while (start <= end) {
            val mid = start + (end - start) / 2
            val currentValue = create(mid, "FUEL", mutableMapOf())
            if (currentValue > capacity) {
                end = mid - 1
            } else {
                start = mid + 1
                candidate = mid
            }
        }

        return candidate
    }

    private inline fun <T> Iterable<T>.sumByLong(selector: (T) -> Long): Long {
        var sum = 0L
        for (element in this) {
            sum += selector(element)
        }
        return sum
    }
}