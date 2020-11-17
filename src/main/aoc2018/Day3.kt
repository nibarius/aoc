package aoc2018

class Day3(input: List<String>) {
    private data class Claim(val id: Int, val x: Int, val y: Int, val w: Int, val h: Int)

    private val claims: List<Claim>
    // Each coordinate in the fabric hold a list of all claims covering that coordinate
    private val fabric: Array<Array<MutableList<Int>>>

    init {
        claims = parseInput(input)
        fabric = setupFabric(claims)
        mapAllClaims()
    }

    private fun parseInput(input: List<String>): List<Claim> {
        return input.map {
            // Line to parse: "#1 @ 1,3: 4x4"
            Claim(
                    it.substringBefore(" ").drop(1).toInt(),
                    it.substringAfter("@ ").substringBefore(",").toInt(),
                    it.substringAfter(",").substringBefore(":").toInt(),
                    it.substringAfter(": ").substringBefore("x").toInt(),
                    it.substringAfter("x").toInt()
            )
        }
    }

    private fun setupFabric(claims: List<Claim>): Array<Array<MutableList<Int>>> {
        val maxX = claims.maxByOrNull { it.w + it.x }!!
        val maxY = claims.maxByOrNull { it.h + it.y }!!
        return Array(maxY.y + maxY.h + 1) {
            Array(maxX.x + maxX.w + 1) { mutableListOf() }
        }
    }

    private fun mapAllClaims() {
        for (claim in claims) {
            for (y in claim.y until claim.y + claim.h) {
                for (x in claim.x until claim.x + claim.w) {
                    fabric[y][x].add(claim.id)
                }
            }
        }
    }


    fun solvePart1(): Int {
        return fabric.flatten().count { it.size > 1 }
    }

    fun solvePart2(): Int {
        // Create a set of all claim ids
        val ids = claims.map { it.id }.toMutableSet()
        // Remove all claim ids that are overlapped by another claim
        fabric.flatten().filter { it.size > 1 }.forEach { id -> id.forEach { ids.remove(it) } }
        // Only the claim with no overlap remains
        return ids.first()
    }
}