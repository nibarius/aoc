package aoc2016

class Day13(private val favoriteNumber: Int, private val target: Pair<Int, Int>) {

    private fun numOneBits(number: Int): Int = number.toString(2).count { it == '1' }
    private fun Pair<Int, Int>.isWall(): Boolean {
        val x = first
        val y = second
        val value = x * x + 3 * x + 2 * x * y + y + y * y + favoriteNumber
        return numOneBits(value) % 2 != 0
    }

    fun getMap(): String {
        var ret = "  0123456789\n"
        for (y in 0..6) {
            ret += "$y "
            for (x in 0..9) {
                ret += when (Pair(x, y).isWall()) {
                    true -> "#"
                    false -> "."
                }
            }
            ret += "\n"
        }
        return ret
    }

    private val visited = mutableListOf<Pair<Int, Int>>()

    private data class Step(val location: Pair<Int, Int>, val steps: Int)

    private fun enqueueAllPossibleSteps(currentStep: Step, toCheck: MutableList<Step>) {
        val movesToCheck = listOf(
                Pair(currentStep.location.first - 1, currentStep.location.second),
                Pair(currentStep.location.first + 1, currentStep.location.second),
                Pair(currentStep.location.first, currentStep.location.second - 1),
                Pair(currentStep.location.first, currentStep.location.second + 1))
        for (next in movesToCheck) {
            if (next.first >= 0 && next.second >= 0 &&
                    !next.isWall() &&
                    toCheck.none { it.location == next } &&
                    visited.none { it == next }) {
                toCheck.add(Step(next, currentStep.steps + 1))
            }
        }
    }


    private fun walk(maxSteps: Int): Int {
        val toCheck = mutableListOf(Step(Pair(1, 1), 0))
        while (toCheck.size > 0) {
            val step = toCheck.removeAt(0)
            if (step.steps > maxSteps) {
                continue
            }
            visited.add(step.location)
            if (step.location == target) {
                return step.steps
            }
            enqueueAllPossibleSteps(step, toCheck)
        }
        return -1
    }

    fun solvePart1(): Int {
        return walk(999)
    }

    fun solvePart2(): Int {
        walk(50)
        return visited.size
    }
}
