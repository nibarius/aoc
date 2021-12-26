package aoc2021

class Day22(input: List<String>) {
    data class Cuboid(val xr: IntRange, val yr: IntRange, val zr: IntRange) {
        // The ranges are continuous and fairly large, avoid using count() which iterates over the whole range.
        private fun IntRange.size(): Int = last - first + 1
        fun size() = xr.size().toLong() * yr.size() * zr.size()
        fun isInInitializationArea(): Boolean {
            return xr.first >= -50 && zr.last <= 50 &&
                    yr.first >= -50 && yr.last <= 50 &&
                    zr.first >= -50 && zr.last <= 50
        }

        private fun IntRange.overlaps(other: IntRange) = first in other || other.first in this
        private fun overlaps(other: Cuboid) = xr.overlaps(other.xr) && yr.overlaps(other.yr) && zr.overlaps(other.zr)

        /**
         * Subtracting one cuboid from another results in 0-6 smaller cuboids that doesn't cover the subtrahend
         *
         * 2D illustration of subtracting a small cuboid from a larger one
         *   +---------+
         *   |   :C:   |
         *   |   +-+   |   In three dimensions there is also a E and F part in front and
         *   | A | | B |   behind the subtrahend. Depending on how the subtrahend covers
         *   |   +-+   |   the minuend any, or all of A-F could be non-existing.
         *   |   :D:   |
         *   +---------+
         *
         */
        fun minus(other: Cuboid): List<Cuboid> {
            if (!overlaps(other)) {
                return listOf(this)  // No overlap, nothing removed
            }
            val ret = mutableListOf<Cuboid>()
            // Create the A and B cuboids if needed.
            var xStart = xr.first
            if (xr.first < other.xr.first) {
                ret.add(Cuboid(xr.first until other.xr.first, yr, zr))
                xStart = other.xr.first
            }
            var xEnd = xr.last
            if (other.xr.last < xr.last) {
                ret.add(Cuboid(other.xr.last + 1..xr.last, yr, zr))
                xEnd = other.xr.last
            }

            // Create the C and D cuboids if needed.
            var yStart = yr.first
            if (yr.first < other.yr.first) {
                ret.add(Cuboid(xStart..xEnd, yr.first until other.yr.first, zr))
                yStart = other.yr.first
            }
            var yEnd = yr.last
            if (other.yr.last < yr.last) {
                ret.add(Cuboid(xStart..xEnd, other.yr.last + 1..yr.last, zr))
                yEnd = other.yr.last
            }

            // Create the E and F cuboids if needed.
            if (zr.first < other.zr.first) {
                ret.add(Cuboid(xStart..xEnd, yStart..yEnd, zr.first until other.zr.first))
            }
            if (other.zr.last < zr.last) {
                ret.add(Cuboid(xStart..xEnd, yStart..yEnd, other.zr.last + 1..zr.last))
            }
            return ret
        }

        // Subtract this cuboid from all cuboids in 'subtractFrom' and return the list of remaining cuboids.
        fun subtractFrom(subtractFrom: List<Cuboid>): List<Cuboid> {
            return subtractFrom.flatMap { it.minus(this) }
        }

        // Creates the union of all cuboids in 'current' (which has no overlapping cuboids) and this cuboid,
        // which may overlap one or many cuboids in 'current'
        fun unionWith(current: List<Cuboid>): List<Cuboid> {
            var toBeAdded = listOf(this)
            current.filter { it.overlaps(this) } // Take the cuboids in 'current' that overlaps this cuboid,
                .forEach { cuboidWithOverlap ->        // and subtract the overlap from the cuboids that will be added
                    toBeAdded = cuboidWithOverlap.subtractFrom(toBeAdded)
                }
            return current + toBeAdded
        }

    }

    sealed class Instruction(val cuboid: Cuboid) {
        class On(cuboid: Cuboid) : Instruction(cuboid)
        class Off(cuboid: Cuboid) : Instruction(cuboid)

        fun isPartOfInitialization() = cuboid.isInInitializationArea()
    }

    private fun String.toIntRange() = split("..").let { (a, b) -> a.toInt()..b.toInt() }

    val steps = input.map { line ->
        val parts = line.split(",").map { it.substringAfter("=").toIntRange() }
        when (line[1]) { // second letter of the line
            'n' -> Instruction.On(Cuboid(parts[0], parts[1], parts[2]))
            else -> Instruction.Off(Cuboid(parts[0], parts[1], parts[2]))
        }
    }

    private fun solve(instructions: List<Instruction>): Long {
        var currentlyOn = listOf<Cuboid>()
        instructions.forEach {
            currentlyOn = when (it) {
                is Instruction.On -> it.cuboid.unionWith(currentlyOn)
                is Instruction.Off -> it.cuboid.subtractFrom(currentlyOn)
            }
        }
        return currentlyOn.sumOf { it.size() }
    }


    fun solvePart1(): Long {
        return solve(steps.filter { it.isPartOfInitialization() })
    }

    fun solvePart2(): Long {
        return solve(steps)
    }
}