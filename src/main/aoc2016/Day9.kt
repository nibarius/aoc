package aoc2016

import java.math.BigInteger

class Day9(private val input: String) {

    private fun decompress(compressed: String): String {
        var currentIndex = 0
        var decompressed = ""
        while (currentIndex < compressed.length) {
            val markerStart = compressed.indexOf('(', currentIndex)
            if (markerStart == -1) {
                decompressed += compressed.substring(currentIndex)
                currentIndex = compressed.length
            } else {
                decompressed += compressed.substring(currentIndex, markerStart)
                val afterMarkerEnd = compressed.indexOf(')', markerStart) + 1
                val marker = compressed.substring(markerStart, afterMarkerEnd)
                val length = marker.drop(1).substringBefore('x').toInt()
                val repeats = marker.substringAfter('x').dropLast(1).toInt()
                val afterMarker = markerStart + marker.length
                repeat(repeats) {
                    decompressed += compressed.substring(afterMarker, afterMarker + length)
                }
                currentIndex = afterMarker + length
            }
        }
        return decompressed
    }

    private fun calculateV2UncompressedLength(compressed: String): BigInteger {
        val markerStart = compressed.indexOf('(')
        if (markerStart == -1) {
            return compressed.length.toBigInteger()
        }

        val afterMarkerEnd = compressed.indexOf(')', markerStart) + 1
        val marker = compressed.substring(markerStart, afterMarkerEnd)
        val length = marker.drop(1).substringBefore('x').toInt()
        val repeats = marker.substringAfter('x').dropLast(1).toInt()
        val afterMarker = markerStart + marker.length
        val compressedPart = compressed.substring(afterMarker, afterMarker + length)
        val remainder = compressed.substring(afterMarker + length)

        return markerStart.toBigInteger() + // part before first compressed part
                repeats.toBigInteger() * calculateV2UncompressedLength(compressedPart) + // the first compressed part
                calculateV2UncompressedLength(remainder) // the rest of the data after the first compressed part
    }

    fun solvePart1(): Int {
        return decompress(input).length
    }

    fun solvePart2(): BigInteger {
        return calculateV2UncompressedLength(input)
    }
}
