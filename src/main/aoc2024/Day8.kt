package aoc2024

import AMap
import Pos
import com.marcinmoskala.math.combinations

class Day8(input: List<String>) {
    private val map = AMap.parse(input)

    // List of antennas grouped by their signal frequency.
    private val antennas = map.keys.filter { map[it] != '.' }
        .groupBy { map[it] }
        .values
        .map { it.toSet() }

    private fun getAntinodes(pair: Set<Pos>): Set<Sequence<Pos>> {
        val diff = pair.last() - pair.first()
        return setOf(
            generateSequence(pair.first()) { prev -> prev - diff },
            generateSequence(pair.last()) { prev -> prev + diff })
    }

    private fun getAllAntinodes(filter: (Sequence<Pos>) -> Sequence<Pos>): Collection<Pos> {
        return buildSet {
            antennas.forEach { antennaGroup ->
                antennaGroup.combinations(2).forEach { antennaPair ->
                    getAntinodes(antennaPair).forEach {
                        addAll(filter(it))
                    }
                }
            }
        }
    }

    fun solvePart1(): Int {
        // Take the first antinode that is not the antenna in each direction if it's in the map
        return getAllAntinodes { s -> s.drop(1).take(1).filter { map.contains(it) } }.size
    }

    fun solvePart2(): Int {
        // Take all antinodes within the map.
        return getAllAntinodes { s -> s.takeWhile { map.contains(it) } }.size
    }
}