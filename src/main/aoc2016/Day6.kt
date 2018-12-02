package aoc2016

import increase

class Day6(input: List<String>) {

    private val characters = countCharacters(input)

    private fun countCharacters(input: List<String>): Array<MutableMap<Char, Int>> {
        val characters = Array(input[0].length) { _ -> mutableMapOf<Char, Int>() }
        input.forEach {
            it.forEachIndexed { index: Int, c: Char ->
                characters[index].increase(c)
            }
        }
        return characters
    }

    fun solvePart1(): String {
        return characters.map { it.maxBy { it.value }!!.key }.joinToString("")
    }

    fun solvePart2(): String {
        return characters.map { it.minBy { it.value }!!.key }.joinToString("")
    }
}
