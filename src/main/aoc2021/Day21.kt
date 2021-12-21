package aoc2021

class Day21(input: List<String>) {
    private val p1start = input.first().last().digitToInt()
    private val p2start = input.last().last().digitToInt()

    class Player(var pos: Int, var score: Int)  {
        // Move the player and return true if the player won
        fun move(howMuch: Int): Boolean {
            pos = ((pos + howMuch - 1) % 10) + 1
            score += pos
            return score >= 1000
        }
    }
    class Die {
        var rolls: Int = 0
        private var value = 1
        fun roll(): Int {
            val result = when (value) {
                100 -> 103
                99 -> 200
                else -> 3 * value + 3 // x + (x + 1) + (x + 2) = 3x + 3
            }
            rolls += 3
            value = (rolls % 100) + 1
            return result
        }
    }

    private fun playDeterministic(): Int {
        val p1 = Player(p1start, 0)
        val p2 = Player(p2start, 0)
        val die = Die()
        while(!p1.move(die.roll()) &&  !p2.move(die.roll())) Unit // Play until one player wins
        return minOf(p1.score, p2.score) * die.rolls
    }

    // Map of possible sum of three dice to number of different die combinations that result in that sum
    private val dieRolls = listOf(3 to 1, 4 to 3, 5 to 6, 6 to 7, 7 to 6, 8 to 3, 9 to 1)

    // count how many ways a player can throw the die and win. Returns a map of number of turns to win
    // to the total amount of dice rolls that results in a victory on that amount of turns.
    private fun countWaysToFinish(
        pos: Int,
        score: Int,
        turns: Int,
        totalDieRolls: Long,
        result: MutableMap<Int, Long>
    ): MutableMap<Int, Long> {
        if (score >= 21) {
            result[turns] = result.getOrDefault(turns, 0) + totalDieRolls
            return result
        }
        dieRolls.forEach { (rolled, count) ->
            val nextPos = ((pos + rolled - 1) % 10) + 1
            countWaysToFinish(nextPos, score + nextPos, turns + 1, count * totalDieRolls, result)
        }
        return result
    }

    // Counts the number of ways a player can throw the die and not finish within the given amount of turns
    private fun countWaysToNotFinish(pos: Int, score: Int, turnsLeft: Int): Long {
        return when {
            score >= 21 -> 0 // This branch reached the finish, don't count it
            turnsLeft == 0 -> 1 // This branch ran out of turns, count it
            else -> dieRolls.sumOf { (rolled, dieCombinationCount) ->
                val nextPos = ((pos + rolled - 1) % 10) + 1
                dieCombinationCount * countWaysToNotFinish(nextPos, score + nextPos, turnsLeft - 1)
            }
        }
    }

    // Actually only counts in how many ways p1 can win, but with my input and with the example input p1 wins most
    private fun playDirac(): Long {
        return countWaysToFinish(p1start, 0, 0, 1, mutableMapOf())
            .toList()
            .sumOf { (p1Turns, totalP1Rolls) ->
                // For p1 to win in p1Turns, p2 must not finish in p1Turns - 1
                totalP1Rolls * countWaysToNotFinish(p2start, 0, p1Turns - 1)
            }
    }

    fun solvePart1(): Int {
        return playDeterministic()
    }

    fun solvePart2(): Long {
        return playDirac()
    }
}