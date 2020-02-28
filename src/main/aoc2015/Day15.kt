package aoc2015

import kotlin.math.max

class Day15(input: List<String>) {

    // List of ingredients, each entry is a list of parameters describing the ingredient (last is calories)
    private val ingredients = parseInput(input)

    private fun parseInput(input: List<String>): List<List<Int>> {
        return input.map { line ->
            line.split(" ").let {
                listOf(it[2].dropLast(1).toInt(),
                        it[4].dropLast(1).toInt(),
                        it[6].dropLast(1).toInt(),
                        it[8].dropLast(1).toInt(),
                        it[10].toInt()
                )
            }
        }
    }

    private fun score(amounts: List<Int>): Int {
        val scores = MutableList(4) { 0 }
        for (property in 0..3) {
            for ((index, ingredient) in ingredients.withIndex()) {
                scores[property] += amounts[index] * ingredient[property]
            }
        }
        return scores
                .map { if (it < 0) 0 else it }
                .fold(1) { total, item -> item * total }
    }

    private fun calories(amounts: List<Int>): Int {
        return ingredients.withIndex().fold(0) { total, item -> total + amounts[item.index] * item.value.last() }
    }

    private fun allCombinations(num: Int, calorieLimit: Boolean = false): Int {
        var best = 0
        if (num == 4) {
            for (i in 0..100) {
                for (j in 0..100 - i) {
                    for (k in 0..100 - i - j) {
                        val amounts = listOf(i, j, k, 100 - i - j - k)
                        if (!calorieLimit || calories(amounts) == 500) {
                            best = max(best, score(amounts))
                        }
                    }
                }
            }
        } else if (num == 2) {
            for (i in 0..100) {
                val amounts = listOf(i, 100 - i)
                if (!calorieLimit || calories(amounts) == 500) {
                    best = max(best, score(amounts))
                }
            }
        }
        return best
    }

    fun solvePart1(): Int {
        return allCombinations(ingredients.size)
    }

    fun solvePart2(): Int {
        return allCombinations(ingredients.size, true)
    }
}