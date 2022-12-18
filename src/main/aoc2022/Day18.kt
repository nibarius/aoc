package aoc2022

class Day18(input: List<String>) {

    private data class Pos3D(val x: Int, val y: Int, val z: Int) {
        fun allNeighbours() = listOf(
            Pos3D(x, y, z - 1),
            Pos3D(x, y, z + 1),
            Pos3D(x, y + 1, z),
            Pos3D(x, y - 1, z),
            Pos3D(x + 1, y, z),
            Pos3D(x - 1, y, z),
        )
    }

    private val cubes = input
        .map { it.split(",").let { (x, y, z) -> Pos3D(x.toInt(), y.toInt(), z.toInt()) } }
        .toSet()

    // Count number of sides that is not next to another cube
    fun solvePart1(): Int {
        return cubes.sumOf { 6 - it.allNeighbours().count { n -> n in cubes } }
    }


    /**
     * Find all the air on the outside using a BFS approach
     */
    private fun findOutsideAir(xRange: IntRange, yRange: IntRange, zRange: IntRange): Set<Pos3D> {
        val air = mutableSetOf<Pos3D>()
        val toCheck = mutableListOf(Pos3D(xRange.first, yRange.first, zRange.first))
        while (toCheck.isNotEmpty()) {
            val current = toCheck.removeLast()
            air.add(current)
            current.allNeighbours().forEach {
                if (it !in cubes && it !in air && it.x in xRange && it.y in xRange && it.z in zRange) {
                    toCheck.add(it)
                }
            }
        }
        return air
    }

    // Count number of sides that is next to the air on the outside
    fun solvePart2(): Int {
        // bounding box that leaves a bit of air outside the outermost cube.
        val xRange = cubes.minBy { it.x }.x - 1..cubes.maxBy { it.x }.x + 1
        val yRange = cubes.minBy { it.y }.y - 1..cubes.maxBy { it.y }.y + 1
        val zRange = cubes.minBy { it.z }.z - 1..cubes.maxBy { it.z }.z + 1

        val outsideAir = findOutsideAir(xRange, yRange, zRange)
        return cubes.sumOf { it.allNeighbours().count { n -> n in outsideAir } }
    }
}