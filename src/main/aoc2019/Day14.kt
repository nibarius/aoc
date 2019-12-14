package aoc2019

import kotlin.math.min

class Day14(input: List<String>) {

    data class Reaction(val req: List<Pair<Int, String>>, val res: Pair<Int, String>)

    // Material name to reaction to create the material
    private val reactions = input.map { parseLine(it) }.map { it.res.second to it }.toMap()

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
    private fun create(amount: Int, material: String, available: MutableMap<String, Int>): Int {
        if (material == "ORE") {
            return amount
        }

        // If there is already some of the desired material created since an earlier reaction use that.
        val alreadyAvailable = min(amount, available.getOrDefault(material, 0))
        available[material] = available.getOrDefault(material, 0) - alreadyAvailable
        val toCreate = amount - alreadyAvailable

        val reaction = reactions[material] ?: error("Unknown material: $material")
        var created = 0
        var oreNeeded = 0
        while (created < toCreate) {
            // Run the reaction until enough material have been created
            oreNeeded += reaction.req.sumBy { create(it.first, it.second, available) }
            created += reaction.res.first
        }

        // Store any left over materials for future reactions
        val leftOver = created - toCreate
        available[material] = available.getOrDefault(material, 0) + leftOver

        return oreNeeded
    }

    fun solvePart1(): Int {
        return create(1, "FUEL", mutableMapOf())
    }

    fun solvePart2(): Int {
        val leftOvers = mutableMapOf<String, Int>()
        // state of leftovers to ore consumed
        val states = mutableMapOf<Map<String, Int>, Pair<Int,Long>>()
        states[leftOvers.toMap()] = Pair(0,0L)
        var oreSoFar = 0L

        var iterations = 0
        val loopLength: Int
        val orePerLoop: Long

        // First find a loop of in amount of leftovers
        do {
            oreSoFar += create(1, "FUEL", leftOvers)
            iterations++
            val state = leftOvers.toMutableMap().apply {
                values.removeIf { it == 0 }
            }.toMap()

            if (states.contains(state)) {
                println("repeating state found at $iterations, oreSoFar: $oreSoFar, matching state was at iter: ${states[state]!!.first}")
                loopLength = iterations - states[state]!!.first
                orePerLoop = oreSoFar - states[state]!!.second
                break
            } else {
                states[state] = iterations to oreSoFar
            }
        }while (true)

        // Then continue increasing by the length of the loop
        while (oreSoFar < 1_000_000_000_000 - orePerLoop) {
            oreSoFar += orePerLoop
            iterations += loopLength
        }

        // Finally do the last steps with the data calculated during loop detection
        val nextOres = states.values.sortedBy { it.first }
        val tooFar = nextOres.first { oreSoFar + it.second > 1_000_000_000_000 }.first
        iterations += nextOres[tooFar - 1].first
        return iterations
    }
}