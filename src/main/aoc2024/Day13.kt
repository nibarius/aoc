package aoc2024

import MyMath
import java.math.BigInteger

class Day13(input: List<String>) {

    private data class Machine(val ax: BigInteger, val ay: BigInteger, val bx: BigInteger, val by: BigInteger, val px: BigInteger, val py: BigInteger) {
        // Solve the two equations made up by the movements and the target using gaussian elimination. If a solution
        // can't be found the claw will not be able to hit the target exactly.
        fun priceToWin(toAdd: BigInteger): Long {
            val equations = listOf(
                arrayOf(ax, bx, px + toAdd),
                arrayOf(ay, by, py + toAdd)
            )
            return try {
                val res = MyMath.gaussianElimination(equations)
                res[0] * 3 + res[1]
            } catch (e: IllegalStateException) {
                0
            }
        }
    }

    private val machines = input.map { m ->
        val parts = m.split("\n")
        Machine(
            parts[0].substringAfter("X+").substringBefore(",").toBigInteger(),
            parts[0].substringAfter("Y+").toBigInteger(),
            parts[1].substringAfter("X+").substringBefore(",").toBigInteger(),
            parts[1].substringAfter("Y+").toBigInteger(),
            parts[2].substringAfter("X=").substringBefore(",").toBigInteger(),
            parts[2].substringAfter("Y=").toBigInteger(),
        )
    }

    fun solvePart1(): Long {
        return machines.sumOf { it.priceToWin(BigInteger("0")) }
    }

    fun solvePart2(): Long {
        return machines.sumOf { it.priceToWin(BigInteger("10000000000000")) }
    }
}