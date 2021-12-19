package aoc2021

import kotlin.math.abs
import kotlin.math.sign

class Day19(input: List<String>) {

    data class Pos3d(val x: Int, val y: Int, val z: Int) {
        fun manhattanDistanceTo(other: Pos3d) = abs(x - other.x) + abs(y - other.y) + abs(z - other.z)

        // The coordinate system between beacons is not reliable, -x for one pos could be +z for another
        // So keep the distance (absolute values) in a set where order of the individual distances doesn't matter
        fun distanceTo(other: Pos3d) = setOf(abs(x - other.x), abs(y - other.y), abs(z - other.z))

        fun distanceToOrdered(other: Pos3d) = listOf(abs(x - other.x), abs(y - other.y), abs(z - other.z))
    }

    data class Scanner(val beacons: List<Pos3d>) {
        // The sign (-1 or 1) of the three coordinates compared to the reference scanner.
        // [1, 1, -1] means the first two coordinates have the same direction as the reference scanner while the
        // last one points in the opposite way.
        lateinit var sign: IntArray

        // Specifies which beacon coordinates maps to which coordinates in the reference scanner.
        // [2, 0, 1] means that the first coordinate is this scanner corresponds to the last coordinate in the reference
        // scanner, while the second corresponds to the first and the last corresponds to the second.
        lateinit var alignment: IntArray
        lateinit var revAlignment: IntArray // revAlignment[0] is the coordinate that corresponds to the first coordinate in the reference scanner

        // Example:
        // A beacon with coordinates (4, 6, 5) in a scanner with sign [-1, 1, 1] and alignment [2, 1, 0]
        // corresponds to the coordinates (5, 6 -4) in the reference scanner.

        // the offset of this scanner compared to the reference scanner
        lateinit var scannerOffset: Pos3d //Todo: why is this a Pos3d and most other things a list of Ints?

        // Get the real coordinates for the given beacon in the coordinate system of the reference scanner
        fun realCoordinatesForBeacon(index: Int): Pos3d {
            // Todo: a lot of this is a copy paste from alignScanners.
            val s2b1AsList = listOf(beacons[index].x, beacons[index].y, beacons[index].z)
            val s2b1beforeRebase = listOf(
                s2b1AsList[revAlignment[0]] * sign[0],
                s2b1AsList[revAlignment[1]] * sign[1],
                s2b1AsList[revAlignment[2]] * sign[2]
            )
            return Pos3d(
                s2b1beforeRebase[0] + scannerOffset.x,
                s2b1beforeRebase[1] + scannerOffset.y,
                s2b1beforeRebase[2] + scannerOffset.z
            )
        }

        val allDistances = beacons.mapIndexed { index, pos3d ->
            buildList {
                for (i in beacons.indices) {
                    val distance = pos3d.distanceTo(beacons[i])
                    add(distance)
                }
            }
        }
    }

    private val scanners: List<Scanner>

    init {
        val lineIterator = input.iterator()
        val scanners = mutableListOf<Scanner>()
        while (lineIterator.hasNext()) {
            val scanner = lineIterator.next().split(" ")[2].toInt()
            val beacons = buildList {
                for (line in lineIterator) {
                    if (line.isEmpty()) {
                        break
                    }
                    val (x, y, z) = line.split(",").map { it.toInt() }
                    add(Pos3d(x, y, z))
                }
            }
            scanners.add(Scanner(beacons))
        }

        // Initialize the reference scanner
        scanners[0].apply {
            sign = intArrayOf(1, 1, 1)
            alignment = intArrayOf(0, 1, 2)
            revAlignment = intArrayOf(0, 1, 2)
            scannerOffset = Pos3d(0, 0, 0)
        }
        this.scanners = scanners
    }

    private fun alignScanners(
        s1b1: Pos3d, s1b2: Pos3d, s2: Scanner,
        s2b1: Pos3d, s2b2: Pos3d
    ) {
        //Todo: instead of calculating distance and sign. Calculate the diff b2-b1 instead and check the sign of that.

        // Calculate alignment by checking which coordinates correspond to each other ([a,b,c] vs [c,a,b])
        val s1diff = s1b1.distanceToOrdered(s1b2)
        val s2diff = s2b1.distanceToOrdered(s2b2)
        s2.alignment = intArrayOf(s1diff.indexOf(s2diff[0]), s1diff.indexOf(s2diff[1]), s1diff.indexOf(s2diff[2]))
        s2.revAlignment = intArrayOf(s2diff.indexOf(s1diff[0]), s2diff.indexOf(s1diff[1]), s2diff.indexOf(s1diff[2]))

        // Calculate sign by checking the sign of pos 1 - pos 2 ([1, 1, -1] vs [-1, -1 -1])
        val s1sign = listOf((s1b2.x - s1b1.x).sign, (s1b2.y - s1b1.y).sign, (s1b2.z - s1b1.z).sign)
        val s2sign = listOf((s2b2.x - s2b1.x).sign, (s2b2.y - s2b1.y).sign, (s2b2.z - s2b1.z).sign)

        // Align sign with reference scanner
        val s2alignedSign = listOf(s2sign[s2.revAlignment[0]], s2sign[s2.revAlignment[1]], s2sign[s2.revAlignment[2]])
        val signDiff = s2alignedSign.zip(s1sign).map { if (it.first == it.second) 1 else -1 }

        s2.sign = signDiff.toIntArray()

        // Find the scanner offset compared to the reference scanner
        val s2b1AsList = listOf(s2b1.x, s2b1.y, s2b1.z)
        val s2b1beforeRebase = listOf(
            s2b1AsList[s2.revAlignment[0]] * s2.sign[0],
            s2b1AsList[s2.revAlignment[1]] * s2.sign[1],
            s2b1AsList[s2.revAlignment[2]] * s2.sign[2]
        )

        s2.scannerOffset =
            Pos3d(s1b1.x - s2b1beforeRebase[0], s1b1.y - s2b1beforeRebase[1], s1b1.z - s2b1beforeRebase[2])
    }

    // Aligns two scanners with each other. Identifying if two scanners can be aligned
    // is done by measuring the distance between all beacons seen by the first scanner
    // and then comparing with the distances seen by the second scanner. If there are at
    // least 12 beacons with the same distance between them the scanners overlap.
    private fun alignScanners(s1: Scanner, s2: Scanner): Boolean {
        s1.allDistances.forEachIndexed { s1Index, s1Distances ->
            // for each beacon in s2. check how many common distances they have
            s2.allDistances.forEachIndexed { s2Index, s2Distances ->
                val common = s1Distances intersect s2Distances.toSet()
                if (common.size >= 12) {
                    // At least 12 beacons overlap. Scanner s1 and s2 has an overlap

                    // Beacon at s1Index is the same beacon as the one at s2Index
                    // Find another common beacon for both s1 and s2. It needs to have different distances
                    // on all three axis to be able to calculate which axis correspond to each other.
                    val distanceToSecondBeacon = common.first { it.size == 3 }
                    val s1Beacon2Index = s1Distances.indexOf(distanceToSecondBeacon)
                    val s2Beacon2Index = s2Distances.indexOf(distanceToSecondBeacon)

                    alignScanners(
                        s1.realCoordinatesForBeacon(s1Index), s1.realCoordinatesForBeacon(s1Beacon2Index),
                        s2, s2.beacons[s2Index], s2.beacons[s2Beacon2Index]
                    )
                    recordAllBeacons(s2)

                    return true
                }
            }
        }
        return false
    }

    private val allBeacons = mutableSetOf<Pos3d>()
    private fun recordAllBeacons(s: Scanner) {
        s.beacons.indices.forEach { allBeacons.add(s.realCoordinatesForBeacon(it)) }
    }

    // todo: takes 6 seconds, a bit too slow for my liking
    fun solvePart1(): Int {
        // find all scanners that overlap with 0
        // calculate coordinates of each scanner relative to scanner 0
        // calculate beacon coordinates for the scanners in scanner 0 coordinate system

        // remove identified scanners
        // use next identified scanner to find all scanners that overlap
        // calculate coordinates and beacons

        // when all scanners has been identified the complete list of beacons shoudl be known
        recordAllBeacons(scanners[0])

        val alignedScanners = mutableListOf(scanners[0])
        val remainingScanners = scanners.drop(1).toMutableList()
        while (remainingScanners.isNotEmpty()) {
            for (aligned in alignedScanners.indices) {
                for (unaligned in remainingScanners.size -1 downTo 0) {
                    val res = alignScanners(alignedScanners[aligned], remainingScanners[unaligned])
                    if (res) {
                        alignedScanners.add(remainingScanners[unaligned])
                        remainingScanners.removeAt(unaligned)
                    }
                }
            }
        }
        return allBeacons.size
    }

    fun solvePart2(): Int {
        // todo: copy paste of part 1, just to align all scanners
        recordAllBeacons(scanners[0])
        val alignedScanners = mutableListOf(scanners[0])
        val remainingScanners = scanners.drop(1).toMutableList()
        while (remainingScanners.isNotEmpty()) {
            for (aligned in alignedScanners.indices) {
                for (unaligned in remainingScanners.size -1 downTo 0) {
                    val res = alignScanners(alignedScanners[aligned], remainingScanners[unaligned])
                    if (res) {
                        alignedScanners.add(remainingScanners[unaligned])
                        remainingScanners.removeAt(unaligned)
                    }
                }
            }
        }

        val offsets = scanners.map { it.scannerOffset }
        return buildList {
            for (i in offsets.indices) {
                for (j in offsets.indices) {
                    add(offsets[i].manhattanDistanceTo(offsets[j]))
                }
            }
        }.maxOf { it }

    }
}