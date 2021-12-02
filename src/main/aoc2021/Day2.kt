package aoc2021

class Day2(input: List<String>) {

    private data class Command(val dir: String, val amount: Int)

    private val commands = input.map {
        val (dir, amount) = """(\w+) (\d+)""".toRegex().find(it)!!.destructured
        Command(dir, amount.toInt())
    }

    fun solvePart1(): Int {
        var h = 0
        var v = 0
        commands.forEach { (dir, amount) ->
            when (dir) {
                "up" -> v -= amount
                "down" -> v += amount
                "forward" -> h += amount
            }
        }
        return h * v
    }

    fun solvePart2(): Int {
        var h = 0
        var v = 0
        var aim = 0
        commands.forEach { (dir, amount) ->
            when (dir) {
                "up" -> aim -= amount
                "down" -> aim += amount
                "forward" -> {
                    h += amount
                    v += aim * amount
                }
            }
        }
        return v * h
    }
}