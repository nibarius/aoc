package aoc2020

class Day22(input: List<String>) {
    val players = input.map { player -> player.split("\n").drop(1).map { it.toInt() } }

    private fun normalGame(players: List<MutableList<Int>>): Pair<Int, MutableList<Int>> {
        while (players.all { it.isNotEmpty() }) {
            val cards = List(2) { players[it].removeAt(0) }
            val winner = cards.withIndex().maxByOrNull { it.value }!!.index
            players[winner].addAll(List(2) { cards[(winner + it) % 2] })
        }
        return 0 to players.first { it.isNotEmpty() } // who wins is not important
    }

    // returns a winner index to deck pair
    private fun recursiveGame(players: List<MutableList<Int>>): Pair<Int, MutableList<Int>> {
        val previousRounds = mutableSetOf<Int>()
        while (players.all { it.isNotEmpty() }) {
            val configuration = players.hashCode()
            if (previousRounds.contains(configuration)) {
                // player 1 win the game
                return 0 to players[0]
            }
            previousRounds.add(configuration)
            val cards = List(2) { players[it].removeAt(0) }
            val winner = if (cards.withIndex().all { (index, value) -> value <= players[index].size }) {
                // the winner of this round is the winner of a new recursive game
                val newCards = players.mapIndexed { playerIndex, cardList -> cardList.take(cards[playerIndex]).toMutableList() }
                val subGame = recursiveGame(newCards)
                subGame.first
            } else {
                cards.withIndex().maxByOrNull { it.value }!!.index
            }

            // add the cards to the winners deck, own card first, other players card later
            players[winner].addAll(List(2) { cards[(winner + it) % 2] })
        }
        return players.mapIndexed { index, cardList -> index to cardList }.first { it.second.isNotEmpty() }
    }

    private fun normalGame(game: (List<MutableList<Int>>) -> Pair<Int, MutableList<Int>>): Int {
        val p = players.map { it.toMutableList() }
        val winner = game.invoke(p).second
        return winner.mapIndexed { index, i -> (winner.size - index) * i }.sum()
    }

    fun solvePart1(): Int {
        return normalGame(this::normalGame)
    }

    fun solvePart2(): Int {
        return normalGame(this::recursiveGame)
    }
}