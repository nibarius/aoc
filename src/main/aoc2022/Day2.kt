package aoc2022

class Day2(input: List<String>) {
    // Represent the shapes (and desired outcomes) as the numbers 0-2.
    // rock -> paper -> scissors -> rock
    // 0    -> 1     -> 2        -> 0
    val parsedInput = input.map { line ->
        line.split(" ").let { it.first().first() - 'A' to it.last().first() - 'X' }
    }


    /**
     * Score one game given the opponent's and my shape.
     */
    private fun scoreGame(opponent: Int, me: Int): Int {
        return when {
            opponent == me -> 3 // draw
            (opponent + 1) % 3 == me -> 6 // one step ahead of opponent is a win
            (opponent + 2) % 3 == me -> 0 // two steps ahead of opponent is a loss
            else -> error("Impossible state")
        }
    }

    /**
     * Get the score for the given shape
     */
    private fun Int.getShapeScore() = this + 1

    /**
     * Get the shape that results with the desired outcome given the opponents shape
     */
    private fun getDesiredShape(opponent: Int, desiredOutcome: Int): Int {
        return when (desiredOutcome) {
            0 -> (opponent + 2) % 3 // two steps ahead of opponent is a loss
            1 -> opponent
            2 -> (opponent + 1) % 3 // one step ahead of opponent is a win
            else -> error("Impossible state")
        }
    }

    private fun calculateTotalScore(getOwnShape: (Int, Int) -> Int): Int {
        return parsedInput.sumOf {
            val myShape = getOwnShape(it.first, it.second)
            scoreGame(it.first, myShape) + myShape.getShapeScore()
        }
    }

    fun solvePart1(): Int {
        // X, Y, Z represent the shape I should use
        return calculateTotalScore { _, myShape -> myShape }
    }

    fun solvePart2(): Int {
        // X, Y, Z represents the desired outcome
        return calculateTotalScore { opponent, desiredResult -> getDesiredShape(opponent, desiredResult) }
    }
}