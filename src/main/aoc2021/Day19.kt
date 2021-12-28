package aoc2021

import java.lang.Integer.max
import kotlin.math.abs

// typealias is global, so use a name that won't collide with coordinates in other puzzles
/**
 * Each beacon position is just a list of three coordinates. Due to scanner alignment issues different
 * indexes in the list may refer to different axis between beacons in different scanners.
 */
typealias Coordinate21d19 = List<Int>


class Day19(input: List<String>) {

    companion object {
        operator fun Coordinate21d19.minus(other: Coordinate21d19): Coordinate21d19 = zip(other) { a, b -> a - b }
        operator fun Coordinate21d19.plus(other: Coordinate21d19): Coordinate21d19 = zip(other) { a, b -> a + b }
        fun Coordinate21d19.abs(): Coordinate21d19 = map { abs(it) }

        // Distance to another coordinate as a set, as order must not be considered when comparing distances
        // between beacons in different scanners.
        fun Coordinate21d19.distanceTo(other: Coordinate21d19) = (this - other).abs().toSet()
        fun Coordinate21d19.manhattanDistanceTo(other: Coordinate21d19) = (this - other).abs().sum()
    }

    data class Scanner(val beacons: List<Coordinate21d19>) {
        // the offset of this scanner compared to the reference scanner
        lateinit var scannerOffset: Coordinate21d19

        // The positions in the beacons using the reference scanners coordinate system
        lateinit var realPositions: List<Coordinate21d19>

        // The distance between all beacons in the scanner
        val allDistances = beacons.map { beacon ->
            buildSet {
                for (i in beacons.indices) {
                    val distance = beacon.distanceTo(beacons[i])
                    add(distance)
                }
            }
        }

        /**
         * Align this scanner based on the difference between the same two beacons in two different scanners.
         *
         * It also uses the position of one beacon in the two scanners to find the offset between the scanners
         * to be able to calculate the true positions of all the beacons in this scanner in the reference scanner's
         * coordinate system.
         *
         * Uses the distances between the beacons to find the correct alignment.
         * @param expected the expected result when taking the difference of the coordinates in the coordinate system
         * of the reference scanner
         * @param actual the actual result when using this scanners coordinate system. The order and sign of the
         * entries in actual may be different compared to expected.
         * @param alignedPosition the position of one beacon using aligned coordinates
         * @param unalignedPosition the position of the same beacon in this scanners coordinate system.
         */
        fun alignScanner(
            expected: Coordinate21d19, actual: Coordinate21d19,
            alignedPosition: Coordinate21d19,
            unalignedPosition: Coordinate21d19
        ) {
            val expectedMagnitudes = expected.abs()
            val actualMagnitudes = actual.abs()
            // axisMapping[0] is the coordinate that corresponds to the first coordinate in the reference scanner
            val axisMapping = listOf(
                actualMagnitudes.indexOf(expectedMagnitudes[0]),
                actualMagnitudes.indexOf(expectedMagnitudes[1]),
                actualMagnitudes.indexOf(expectedMagnitudes[2])
            )

            // align the axis without regards to sign
            val axisAligned = realignPosition(actual, axisMapping, listOf(1, 1, 1))
            val signMapping = axisAligned.zip(expected) { a, b -> if (a == b) 1 else -1 }

            // Find the scanner offset compared to the reference scanner
            scannerOffset = alignedPosition - realignPosition(unalignedPosition, axisMapping, signMapping)
            realPositions = beacons.map { realignPosition(it, axisMapping, signMapping) + scannerOffset }
        }

        // Realign the given position using the provided axes and sign mappings.
        private fun realignPosition(
            pos: Coordinate21d19, axisMapping: List<Int>, signMapping: List<Int>
        ): Coordinate21d19 {
            return listOf(
                pos[axisMapping[0]] * signMapping[0],
                pos[axisMapping[1]] * signMapping[1],
                pos[axisMapping[2]] * signMapping[2]
            )
        }
    }

    private val scanners: List<Scanner>
    private val allBeacons = mutableSetOf<Coordinate21d19>()

    init {
        val lineIterator = input.iterator()
        val scanners = mutableListOf<Scanner>()
        while (lineIterator.hasNext()) {
            lineIterator.next() // skip scanner name, it's of no interest
            val beacons: List<Coordinate21d19> = buildList {
                for (line in lineIterator) {
                    if (line.isEmpty()) {
                        break
                    }
                    add(line.split(",").map { it.toInt() })
                }
            }
            scanners.add(Scanner(beacons))
        }

        // Initialize the reference scanner
        scanners[0].apply {
            scannerOffset = listOf(0, 0, 0)
            realPositions = beacons
        }
        this.scanners = scanners
        alignAllScanners()
    }


    // Aligns two scanners with each other. Identifying if two scanners can be aligned
    // is done by measuring the distance between all beacons seen by the first scanner
    // and then comparing with the distances seen by the second scanner. If there are at
    // least 12 beacons with the same distance between them the scanners overlap.
    private fun alignScanners(s1: Scanner, s2: Scanner): Boolean {
        // Note: assumes that the allDistances set is ordered in the same way that they were added
        s1.allDistances.forEachIndexed { s1Index, s1Distances ->
            // for each beacon in s2. check how many common distances they have
            s2.allDistances.forEachIndexed { s2Index, s2Distances ->
                val common = s1Distances intersect s2Distances
                if (common.size >= 12) {
                    // At least 12 beacons overlap. Scanner s1 and s2 has an overlap

                    // Beacon at s1Index is the same beacon as the one at s2Index
                    // Find another common beacon for both s1 and s2. It needs to have different distances
                    // on all three axis to be able to calculate which axis correspond to each other.
                    val distanceToSecondBeacon = common.first { it.size == 3 }
                    val s1Beacon2Index = s1Distances.indexOf(distanceToSecondBeacon)
                    val s2Beacon2Index = s2Distances.indexOf(distanceToSecondBeacon)

                    s2.alignScanner(
                        s1.realPositions[s1Index] - s1.realPositions[s1Beacon2Index],
                        s2.beacons[s2Index] - s2.beacons[s2Beacon2Index],
                        s1.realPositions[s1Index], s2.beacons[s2Index]
                    )
                    allBeacons.addAll(s2.realPositions)
                    return true
                }
            }
        }
        return false
    }

    /**
     * Align all scanners by searching for all scanners overlapping the reference scanner.
     * Then continue searching for other scanners that overlaps any of the identified
     * scanners until there are no unaligned scanners left.
     */
    private fun alignAllScanners() {
        // Record all beacons for the first scanner and use it as the reference scanner
        allBeacons.addAll(scanners[0].realPositions)
        val alignedScanners = mutableListOf(scanners[0])
        val remainingScanners = scanners.drop(1).toMutableList()
        // set of scanners that has already been identified as unalignable
        val alignmentHistory = mutableSetOf<Pair<Scanner, Scanner>>()
        baseLoop@ while (remainingScanners.isNotEmpty()) {
            for (aligned in alignedScanners) {
                for (unaligned in remainingScanners.filterNot { aligned to it in alignmentHistory }) {
                    val res = alignScanners(aligned, unaligned)
                    if (res) {
                        alignedScanners.add(unaligned)
                        remainingScanners.remove(unaligned)
                        continue@baseLoop
                    }
                    alignmentHistory.add(aligned to unaligned)
                }
            }
        }
    }

    fun solvePart1(): Int {
        return allBeacons.size
    }

    fun solvePart2(): Int {
        var max = 0
        val offsets = scanners.map { it.scannerOffset }
        for (i in offsets.indices) {
            for (j in offsets.indices) {
                max = max(offsets[i].manhattanDistanceTo(offsets[j]), max)
            }
        }
        return max
    }
}