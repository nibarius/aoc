package aoc2025

import MyMath.toRREF
import Search
import java.math.BigInteger
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min


class Day10(input: List<String>) {
    private data class Machine(val target: List<Boolean>, val buttons: List<List<Int>>, val joltage: List<Int>)

    private val machines = input.map { line ->
        val lights = line.drop(1)
            .substringBefore("]")
            .map { it == '#' }
        val buttons = line.substringAfter("] ")
            .substringBefore(" {")
            .split(" ")
            .map { group ->
                group.drop(1).dropLast(1)
                    .split(",")
                    .map { it.toInt() }
            }
        val joltage = line.substringAfter("{")
            .dropLast(1)
            .split(",")
            .map { it.toInt() }
        Machine(lights, buttons, joltage)
    }


    private class Graph(val machine: Machine) : Search.Graph<List<Boolean>> {
        override fun neighbours(id: List<Boolean>): List<List<Boolean>> {
            return machine.buttons.map { toPress ->
                val nextState = id.toMutableList()
                toPress.forEach { nextState[it] = !nextState[it] }
                nextState
            }
        }
    }

    private fun pressButtons(machine: Machine): Int {
        val g = Graph(machine)
        val r = Search.bfs(g, List(machine.target.size) { false }, machine.target)
        return r.getPath(machine.target).size
    }

    /**
     * Do a BFS to find the minimum number of required button pressess needed to get into the
     * desired state.
     */
    fun solvePart1(): Int {
        return machines.sumOf { pressButtons(it) }
    }

    /**
     * Create an equation system describing the given machine. Each button is one column
     * and the last column is the target joltages. Each row is the counters that the corresponding
     * button increases.
     * The first example machine: (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
     * has the equation:
     * [0, 0, 0, 0, 1, 1, 3]
     * [0, 1, 0, 0, 0, 1, 5]
     * [0, 0, 1, 1, 1, 0, 4]
     * [1, 1, 0, 1, 0, 0, 7]
     */
    private fun createEquation(machine: Machine): List<Array<BigInteger>> {
        val columns = machine.buttons.size + 1
        val rows = machine.joltage.size
        val equations = List(rows) { Array(columns) { BigInteger.ZERO } }
        machine.buttons.forEachIndexed { button, counters ->
            counters.forEach { counter ->
                equations[counter][button] = BigInteger.ONE
            }
        }
        machine.joltage.forEachIndexed { index, joltage ->
            equations[index][columns - 1] = joltage.toBigInteger()
        }
        return equations
    }

    /**
     * Find all free variables by going down diagonally along the pivot variables.
     * If we have to take more than one step to the right to find the next non-zero
     * value it means we are moving past free variables.
     * Index 3 is a free variable in
     * [1, 0, 0, 2, 0, 40]
     * [0, 1, 0, -1, 0, -7]
     * [0, 0, 1, 1, 0, 37]
     * [0, 0, 0, 0, 1, 7]
     */
    private fun findFreeVariables(e: List<Array<BigInteger>>): List<Int> {
        var col = 0
        var row = 0
        var horizontalMovesInARow = 0
        val ret = mutableListOf<Int>()
        while (col < e[0].size - 1 && row < e.size) {
            val curr = e[row][col]
            if (curr == BigInteger.ZERO) {
                horizontalMovesInARow++
                if (horizontalMovesInARow > 1) {
                    ret.add(col)
                }
                col++
            } else {
                row++
                horizontalMovesInARow = 0
            }
        }
        // Add all columns that are left after going past the last row
        (col + 1 until e[0].size - 1).forEach { ret.add(it) }
        return ret
    }

    /**
     * Finds the solution that requires the minimal number of button presses required
     * to solve the equation system. Returns the number of button presses required.
     */
    private fun findMinimalSolution(e: List<Array<BigInteger>>): Int {
        val free = findFreeVariables(e)
        if (free.isEmpty()) {
            // The simple case, no free variables, just solve the equations as is.
            return solve(e, IntArray(e[0].size - 1) { 0 })!!
        }

        val boundaries = findBoundaries(e, free)
        return bruteForceFreeVariables(e, boundaries, listOf())!!
    }

    /**
     * Recursively use brute force to find which combinations of free variables solves
     * all equations and provides the fewest amount of button presses.
     * @param e the equation system to solve
     * @param remainingFreeVariables the list of free variables and their possible ranges
     * @param fixedFreeVariables the list of free variables fixed to given values, index to value pairs
     * @return The minimum number of button presses needed, or null if it's not possible to
     *         solve the equation system with the provided free variables.
     */
    private fun bruteForceFreeVariables(
        e: List<Array<BigInteger>>,
        remainingFreeVariables: List<Pair<Int, IntRange>>,
        fixedFreeVariables: List<Pair<Int, Int>>
    ): Int? {
        return when (remainingFreeVariables.size) {
            1 -> findBestFreeVariableValue(e, remainingFreeVariables.first(), fixedFreeVariables)
            else -> {
                val (index, candidates) = remainingFreeVariables.first()
                candidates.mapNotNull {
                    bruteForceFreeVariables(e, remainingFreeVariables.drop(1), fixedFreeVariables + listOf(index to it))
                }.minOrNull()
            }
        }
    }

    /**
     * When there is only one undecided free variable to test we have a simple linear equation.
     * This means that the required button presses will strictly increase or decrease when the
     * value of the free variable increases. Because of this it's enough to check the value of
     * the first and the last valid solution and return the smallest of them.
     * @param e The equation system
     * @param candidates A pair of the free variable's index
     * @param freeVariables The other free variables with already fixed values
     */
    private fun findBestFreeVariableValue(
        e: List<Array<BigInteger>>,
        candidates: Pair<Int, IntRange>,
        freeVariables: List<Pair<Int, Int>>
    ): Int? {
        val solution = IntArray(e[0].size - 1) { 0 }
        freeVariables.forEach { (index, value) -> solution[index] = value }
        val (index, candidateValues) = candidates

        val first = candidateValues.findFirstSolution(e, index, solution) ?: return null
        val last = candidateValues.reversed().findFirstSolution(e, index, solution) ?: return null
        return min(first, last)
    }

    /**
     * Return the number of button presses for the first free variable value in this IntProgression or
     * null if there are no values in the IntProgression that solves the equation system.
     */
    private fun IntProgression.findFirstSolution(e: List<Array<BigInteger>>, index: Int, solution: IntArray): Int? {
        return firstNotNullOfOrNull { candidate ->
            solution.copyOf()
                .apply { this[index] = candidate }
                .let { solve(e, it) }
        }
    }

    /**
     * Solves the given equation system filling in the values of each variable
     * in the provided solution array. Any free variables have fixed values specified
     * in the solution array before this is called.
     * Returns true if there is a solution to the equation system, if that's the case
     * the solution array contains the solution.
     */
    private fun solve(e: List<Array<BigInteger>>, solution: IntArray): Int? {
        // test equations from bottom up and fill in variable values as we find their values.
        for (row in e.reversed()) {
            val pivot = row.indexOfFirst { it != BigInteger.ZERO }
            if (pivot == -1) {
                continue // zero row, we can ignore it
            }
            val factor = row[pivot].toInt()
            var rhs = row.last().toInt() // Right hand side
            (pivot + 1 until row.size - 1).forEach { col ->
                rhs -= row[col].toInt() * solution[col]
            }
            if (rhs >= 0 && rhs % factor == 0) {
                // non-negative and whole integer button clicks required
                // Example: 2 = 40 is ok
                solution[pivot] = rhs / factor
            } else {
                // solution not possible
                // Example: 2 = 39 or 1 = -12 is not ok.
                return null
            }
        }
        return solution.sum()
    }

    /** Divide two integers and round up. Both must be negative or positive */
    fun divRoundUp(a: Int, b: Int) = (a.absoluteValue + b.absoluteValue - 1) / b.absoluteValue

    /**
     * Find the boundaries of what values the free variables can have based on the
     * equation system. Since there are no negative button clicks the lower boundary is
     * always at least 0. The upper boundary is not always possible to determine but since
     * fewer button presses are desired it is arbitrarily set to something a bit higher than
     * the lower boundary.
     * @param free The list of the indices of the free variables
     * @return a lit of Free variable index to IntRange pairs specifying free variables' boundaries
     */
    private fun findBoundaries(e: List<Array<BigInteger>>, free: List<Int>): List<Pair<Int, IntRange>> {
        return free.map { freeIndex ->
            var low = 0
            var high = Int.MAX_VALUE
            for (row in e) {
                val freeVal = row[freeIndex].toInt()
                val rhs = row.last().toInt()
                if (freeVal < 0 && rhs < 0 && row.count { it.toInt() < 0 } == 2) {
                    // Current and right hand side are the only two negative, we can determine a lower bound
                    // Example: x - free = -10 ==> x = -10 + free ==> free >= 10
                    low = max(low, divRoundUp(rhs, freeVal))
                } else if (freeVal > 0 && row.all { it >= BigInteger.ZERO }) {
                    // All are non-negative, we can determine an upper bound
                    // Example: x + free1 + free2 = 10 ==> x = 10 - free1 - free2 ==> free1 <= 10
                    high = min(high, rhs / freeVal)
                }
            }
            if (high == Int.MAX_VALUE) {
                // Put an arbitrary upper limit to not have to search too far,
                // when there is no upper limit it's usually better to press buttons fewer times
                high = low + 20
            }
            freeIndex to low..high
        }
    }

    /**
     * Create an equation system for each machine, take the equation system
     * as close as possible to RREF and then use brute force to find the
     * suitable values for the free variables to get the minimum number of
     * button presses needed.
     */
    fun solvePart2(): Int {
        return machines.sumOf { machine ->
            val a = createEquation(machine).toRREF()
            findMinimalSolution(a)
        }
    }
}