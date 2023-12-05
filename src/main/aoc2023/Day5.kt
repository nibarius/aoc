package aoc2023

import kotlin.math.min

class Day5(input: List<String>) {

    data class ElfMap(val source: LongRange, val diff: Long) {
        companion object {
            // Parse input and add all missing ranges between 0 and max UInt with zero
            // transformation to have ranges with complete coverage.
            fun parse(input: String): List<ElfMap> = buildList {
                val elfMaps = input.split("\n").drop(1).map { line ->
                    val numbers = line.split(" ").map { it.toLong() }
                    ElfMap(
                        numbers[1]..<numbers[1] + numbers[2],
                        numbers[0] - numbers[1]
                    )
                }.sortedBy { it.source.first }
                addAll(elfMaps)
                // Add missing numbers between 0 and the first range if needed
                if (elfMaps.first().source.first != 0L) {
                    add(ElfMap(0..<elfMaps.first().source.first, 0))
                }

                // Add missing numbers between two ranges if needed
                elfMaps.windowed(2).forEach { (first, second) ->
                    if (first.source.last + 1 != second.source.first) {
                        add(ElfMap(first.source.last + 1..<second.source.first, 0))
                    }
                }

                // Add missing numbers after the last range if needed
                if (elfMaps.last().source.last < 0xffffffffL) {
                    add(ElfMap(elfMaps.last().source.last + 1..0xffffffffL, 0))
                }
            }
        }
    }

    private val seeds = input[0].substringAfter(": ").split(" ").map { it.toLong() }
    private val seeds2 = input[0].substringAfter(": ").split(" ").map { it.toLong() }
        .windowed(2, 2).map { it.first()..<it.first() + it.last() }
    private val pipeline = (1..7).map { ElfMap.parse(input[it]) }


    fun solvePart1(): Long {
        return seeds.minOf { seed ->
            pipeline.fold(seed) { currentNumber, pipelineStep ->
                pipelineStep.firstNotNullOf { if (currentNumber in it.source) currentNumber + it.diff else null }
            }
        }
    }

    // For each of the source ranges, run them through this pipeline step and split them up in as many ranges
    // as needed to cover all the different destination ranges overlapping the source range.
    private fun processPipelineStep(sourceRanges: List<LongRange>, pipelineStep: List<ElfMap>): List<LongRange> {
        return buildList {
            sourceRanges.forEach { source ->
                var current = source.first
                while (current <= source.last) {
                    val mapping = pipelineStep.first { current in it.source }
                    add(current + mapping.diff..min(source.last, mapping.source.last) + mapping.diff)
                    current = mapping.source.last + 1
                }
            }
        }
    }

    fun solvePart2(): Long {
        return pipeline.fold(seeds2) { currentRanges, pipelineStep ->
            processPipelineStep(currentRanges, pipelineStep)
        }.minOf { it.first }

    }
}
