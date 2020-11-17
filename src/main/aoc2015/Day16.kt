package aoc2015

class Day16(input: List<String>) {

    private val realSue = """
        children: 3
        cats: 7
        samoyeds: 2
        pomeranians: 3
        akitas: 0
        vizslas: 0
        goldfish: 5
        trees: 3
        cars: 2
        perfumes: 1
    """.trimIndent().split("\n")
            .map { line -> line.split(": ").let { it.first() to it.last().toInt() } }
            .toMap()

    private val memories = parseInput(input)

    private fun parseInput(input: List<String>): List<Pair<Int, Map<String, Int>>> {
        return input.map {line ->
            val sue = line.substringBefore(":").substringAfter(" ").toInt()
            val list = line.substringAfter(": ")
            val parts = list.split(", ")
            val parsed = parts
                    .map { part -> part.split(": ").let { it.first() to it.last().toInt() } }
                    .toMap()
            sue to parsed
        }
    }

    fun solvePart1(): Int {
        memories.forEach {memory ->
            val found = memory.second.all { (key, value) ->
                realSue[key] == value
            }
            if (found) {
                return memory.first
            }
        }
        return -1
    }

    fun solvePart2(): Int {
        memories.forEach {memory ->
            var found = true
            memory.second.forEach { (key, value) ->
                found = when (key) {
                    "cat", "trees" -> found && value > realSue.getValue(key)
                    "pomeranians", "goldfish" -> found && value < realSue.getValue(key)
                    else -> found && realSue[key] == value
                }
            }
            if (found) {
                return memory.first
            }
        }
        return -1
    }
}