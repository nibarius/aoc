package aoc2024

class Day3(private val input: List<String>) {

    private fun scanAndMultiply(line: String, doAll: Boolean): Int {
        val regex = """mul\((\d+),(\d+)\)|do\(\)|don't\(\)""".toRegex()
        val matches = regex.findAll(line)
        var includeNextMul = true

        return matches.sumOf {
            val instruction = it.groupValues.first()
            when {
                instruction == "don't()" -> includeNextMul = false
                instruction == "do()" -> includeNextMul = true
                doAll || includeNextMul -> { // only mul() instruction remains
                    return@sumOf it.groupValues[1].toInt() * it.groupValues[2].toInt()
                }
            }
            return@sumOf 0
        }
    }

    private fun scanAndMultiply(doAll: Boolean): Int {
        return scanAndMultiply(input.joinToString("\n"), doAll)
    }

    fun solvePart1(): Int {
        return scanAndMultiply(true)
    }

    fun solvePart2(): Int {
        return scanAndMultiply(false)
    }
}