package aoc2018

import AMap
import Pos
import Search

class Day15(val input: List<String>) {
    data class Player(val team: PlayerType, var pos: Pos, var attackPower: Int = 3, var hp: Int = 200) {
        fun isAlive() = hp > 0
    }

    enum class PlayerType(val symbol: Char) {
        ELF('E'),
        GOBLIN('G')
    }

    class Graph(val map: AMap) : Search.WeightedGraph<Pos> {
        override fun neighbours(id: Pos): List<Pos> {
            return id.allNeighbours().filter { map[it] == '.' }
        }

        override fun cost(from: Pos, to: Pos): Float {
            // Need to use fractions that are powers of two to avoid rounding errors when summing cost (floats).
            return when {
                from.y - to.y == 1 -> 1 + 1 / 1024f // up
                from.x - to.x == 1 -> 1 + 2 / 1024f // left
                from.x - to.x == -1 -> 1 + 3 / 1024f // right
                from.y - to.y == -1 -> 1 + 4 / 1024f // down
                else -> error("moving to invalid position")
            }
        }
    }

    private val players = mutableListOf<Player>()
    private val map = AMap()
    private val graph = Graph(map)
    private var lastAction = "Start of game" // Only used for debugging

    init {
        for (y in input.indices) {
            for (x in input[0].indices) {
                val char = input[y][x]
                val pos = Pos(x, y)
                map[pos] = char
                when (char) {
                    'G' -> players.add(Player(PlayerType.GOBLIN, pos))
                    'E' -> players.add(Player(PlayerType.ELF, pos))
                }
            }
        }
    }

    fun playerAt(pos: Pos) = players.find { it.pos == pos && it.isAlive() }
    fun printableMap(withHp: Boolean = false): List<String> {
        val ret = mutableListOf<String>()
        for (y in 0..map.keys.maxByOrNull { it.y }!!.y) {
            val hp = mutableListOf<Pair<Char, Int>>()
            ret.add("")
            for (x in 0..map.keys.maxByOrNull { it.x }!!.x) {
                val pos = Pos(x, y)
                val char = map[pos]!!
                ret[y] = ret[y] + char
                if (listOf('G', 'E').contains(char)) {
                    val p = players.find { it.pos == pos && it.isAlive() }!!
                    hp.add(Pair(p.team.symbol, p.hp))
                }
            }
            if (withHp) {
                val hpStrings = hp.map { "${it.first}(${it.second})" }
                ret[y] = "${ret[y]}   ${hpStrings.joinToString(", ")}"
            }
        }
        return ret
    }

    private fun move(player: Player, to: Pos) {
        map[player.pos] = '.'
        map[to] = player.team.symbol
        player.pos = to
    }

    private fun attack(player: Player) {
        val target = players.filter { it.team != player.team && it.isAlive() && player.pos isAdjacentTo it.pos }
            .sortedWith(compareBy({ it.pos.y }, { it.pos.x })) // If many adjacent and same hp, do reading order
            .minByOrNull { it.hp }!!
        target.hp -= player.attackPower
        if (target.hp <= 0) {
            map[target.pos] = '.'
        }
        lastAction = "${player.team} at ${player.pos} attacks ${target.team} (hp left: ${target.hp}) at ${target.pos}"
    }

    // Returns true if combat ends
    fun takeTurn(player: Player): Boolean {
        // Identify all possible targets
        val targets = players.filter { it.team != player.team && it.isAlive() }

        // If no targets remain, combat ends
        if (targets.isEmpty()) {
            lastAction = "${player.team} at ${player.pos} finds no targets, game ends"
            return true
        }

        // If adjacent to at least one target, attack without moving
        if (targets.any { enemy -> player.pos isAdjacentTo enemy.pos }) {
            attack(player)
            lastAction += " without moving"
            return false
        }

        // Find all adjacent open spaces next to all targets (list of paths to all targets, start is not part of path)
        val adjacent = targets.flatMap { it.pos.allNeighbours().filter { map[it] == '.' } }
            .map { Search.djikstra(graph, player.pos, it).getPath(it) }
            .filter { it.isNotEmpty() }

        if (adjacent.isEmpty()) {
            // No reachable open spaces, end turn
            lastAction = "${player.team} at ${player.pos} does nothing"
            return false
        }

        // Choose the closest open space (reading order if tied for distance) for moving toward
        val chosenPath = adjacent.groupBy { it.size }.minByOrNull { it.key }!!.value // closest distance
            .minWithOrNull(compareBy({ it.last().y }, { it.last().x }))!! // positions in reading order

        val posBeforeMove = player.pos
        // Take one step toward chosen position (reading order if many paths are available)
        move(player, chosenPath.first())

        // If in range of enemy, attack
        if (targets.any { enemy -> player.pos isAdjacentTo enemy.pos }) {
            attack(player)
            lastAction += " after moving from $posBeforeMove"
        } else {
            lastAction = "${player.team} moved from $posBeforeMove to ${player.pos}"
        }

        return false
    }

    // Returns true if combat ends
    fun doRound(): Boolean {
        players.filter { it.isAlive() }
                .sortedWith(compareBy({ it.pos.y }, { it.pos.x }))
                .forEach { player ->
                    if (player.isAlive()) {
                        val ends = takeTurn(player)
                        if (ends) {
                            return true
                        }
                    }
                }
        return false
    }


    private fun playGame(): Int {
        var round = 1
        while (true) {
            val ends = doRound()
            if (ends) {
                return round - 1
            }
            round++
            if (round > 1000) {
                println("Bailing out after 1000 rounds")
                return -1
            }
        }
    }

    private fun scoreForRounds(rounds: Int): Int {
        return rounds * players.filter { it.isAlive() }.sumOf { it.hp }
    }

    fun solvePart1(): Int {
        val rounds = playGame()
        return scoreForRounds(rounds)
    }


    private fun playWithPower(power: Int): Pair<Int, Int> {
        val game = Day15(input)
        game.players.filter { it.team == PlayerType.ELF }.forEach { it.attackPower = power }
        val rounds = game.playGame()
        val deadElves = game.players.filter { !it.isAlive() && it.team == PlayerType.ELF }.size
        return Pair(deadElves, game.scoreForRounds(rounds))
    }

    fun solvePart2(): Int {
        return binarySearch()
    }

    private fun binarySearch(): Int {
        var tooLow = 4
        var highEnough = Int.MAX_VALUE
        var score = 0
        while (tooLow < highEnough - 1) {
            val toTry = if (highEnough == Int.MAX_VALUE) tooLow * 2 else tooLow + (highEnough - tooLow) / 2
            val result = playWithPower(toTry)
            if (result.first == 0) {
                highEnough = toTry
                score = result.second
            } else {
                tooLow = toTry
            }
        }
        return score
    }
}