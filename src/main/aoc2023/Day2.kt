package aoc2023

class Day2(input: List<String>) {
    private val gameData = input.associate { line ->
        //line: "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"
        val gameId = line.substringAfter(" ").substringBefore(":").toInt()
        val sets = line.substringAfter(": ").split("; ")
        //sets: "3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"
        val y = sets.map { set ->
            // set: "3 blue, 4 red"
            val colors = set.split(", ")
            val x = colors.associate { color ->
                val pairs = color.split(" ")
                pairs.last() to pairs.first().toInt()
            }
            CubeSet(
                x.getOrDefault("red", 0),
                x.getOrDefault("blue", 0),
                x.getOrDefault("green", 0)
            )
        }
        gameId to Game(y)
    }

    private data class Game(val sets: List<CubeSet>) {
        private fun minCubeSet() = CubeSet(
            sets.maxOf { it.red },
            sets.maxOf { it.blue },
            sets.maxOf { it.green },
        )
        fun power() = minCubeSet().let { it.red * it.blue * it.green }
        fun isPossible() = sets.all { it.red <= 12 && it.green <= 13 && it.blue <= 14 }
    }

    private data class CubeSet(val red: Int, val blue: Int, val green: Int)

    fun solvePart1(): Int {
        return gameData.filterValues { it.isPossible() }.keys.sum()
    }

    fun solvePart2(): Int {
        return gameData.values.sumOf { it.power() }
    }
}