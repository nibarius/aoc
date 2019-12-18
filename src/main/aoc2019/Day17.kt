package aoc2019

import next
import prev
import java.lang.RuntimeException

class Day17(input: List<String>) {
    val parsedInput = input.map { it.toLong() }


    fun Map<Pos, Char>.xRange() = keys.minBy { it.x }!!.x..keys.maxBy { it.x }!!.x
    fun Map<Pos, Char>.yRange() = keys.minBy { it.y }!!.y..keys.maxBy { it.y }!!.y
    // Used for debugging
    @Suppress("unused")
    private fun printArea(map: Map<Pos, Char>): String {
        return (map.yRange()).joinToString("\n") { y ->
            (map.xRange()).joinToString("") { x ->
                map.getOrDefault(Pos(x, y), '#').toString()
            }
        }
    }

    data class Pos(val x: Int, val y: Int)
    enum class Dir(val dx: Int, val dy: Int) {
        Up(0, -1),
        Left(-1, 0),
        Down(0, 1),
        Right(1, 0);

        fun from(pos: Pos) = Pos(pos.x + dx, pos.y + dy)

        // facing.turn(left) or facing.turn(right)
        fun turn(dir: Dir) = when (dir) {
            Left -> this.next()
            Right -> this.prev()
            else -> throw RuntimeException("Left and Right are the only valid directions to turn to")
        }
    }

    private fun countIntersections(map: Map<Pos, Char>): Int {
        var sum = 0
        for (y in 1 until map.yRange().last) {
            for (x in 1 until map.xRange().last) {
                if (map[Pos(x - 1, y)] == '#' &&
                        map[Pos(x + 1, y)] == '#' &&
                        map[Pos(x, y - 1)] == '#' &&
                        map[Pos(x, y + 1)] == '#' &&
                        map[Pos(x, y)] == '#') {
                    sum += x * y
                }
            }
        }
        return sum
    }

    private fun generateMap(): Map<Pos, Char> {
        val c = Intcode(parsedInput)
        c.run()
        var x = 0
        var y = 0
        val map = mutableMapOf<Pos, Char>()
        c.output.forEach {
            if (it == 10L) {
                y++
                x = 0
            } else {
                map[Pos(x++, y)] = it.toChar()
            }
        }
        return map
    }

    private fun findPath(map: Map<Pos, Char>): List<String> {
        var facing = Dir.Up // Robot is facing up at first
        var pos = map.filterValues { it == '^' }.keys.first()
        val path = mutableListOf<String>()
        while(true) {
            val code: String
            val toLeft = map.getOrDefault(facing.turn(Dir.Left).from(pos), '.')
            val toRight = map.getOrDefault(facing.turn(Dir.Right).from(pos), '.')
            if (toLeft == '.' && toRight == '.') {
                // Found the end
                break
            }
            else if (toLeft == '#') {
                facing = facing.turn(Dir.Left)
                code = "L"
            }
            else {
                facing = facing.turn(Dir.Right)
                code = "R"
            }

            var steps = 0
            do {
                pos = facing.from(pos)
                steps++
            } while (map.getOrDefault(facing.from(pos), '.') == '#') // Move until the pipe turns
            path.add("$code,$steps")
        }
        return path
    }

    private fun dropPrefix(list: List<String>, prefix: List<String>): List<String> {
        var offset = 0
        while (offset < list.size) {
            for (i in prefix.indices) {
                if (offset + i >= list.size || list[offset + i] != prefix[i]) {
                    // This is not a prefix
                    return list.subList(offset, list.size)
                }
            }
            offset += prefix.size
        }
        return emptyList()
    }

    private fun findPatterns(path: List<String>): List<Pair<String, List<String>>> {
        // Max 20 characters per function means max 5 instructions per function
        val maxInstructions = 5
        val minInstructions = 1
        var remaining = path
        outer@for (i in minInstructions .. maxInstructions) {
            val candidate1 = path.subList(0, i)
            remaining = dropPrefix(path, candidate1)
            for (j in minInstructions .. maxInstructions) {
                var remaining2 = remaining
                val candidate2 = remaining2.subList(0, j)
                do {
                    val remSizeBefore = remaining2.size
                    remaining2 = dropPrefix(remaining2, candidate2)
                    remaining2 = dropPrefix(remaining2, candidate1)
                    val remSizeAfter = remaining2.size
                } while (remSizeAfter != remSizeBefore)
                if (remaining2.isEmpty()) {
                    continue@outer
                }
                for (k in minInstructions .. maxInstructions) {
                    var remaining3 = remaining2
                    val candidate3 = remaining3.subList(0, k)
                    do {
                        val remSizeBefore = remaining3.size
                        remaining3 = dropPrefix(remaining3, candidate3)
                        remaining3 = dropPrefix(remaining3, candidate2)
                        remaining3 = dropPrefix(remaining3, candidate1)
                        val remSizeAfter = remaining3.size
                    } while (remSizeAfter != remSizeBefore)
                    if (remaining3.isEmpty()) {
                        println("three candidates found")
                        println("candidate1: $candidate1")
                        println("candidate2: $candidate2")
                        println("candidate3: $candidate3")
                        return listOf("A" to candidate1, "B" to candidate2, "C" to candidate3)
                    }
                }
            }
        }
        println("couldn't find anything :(")
        return emptyList()

    }

    fun makeFunctions(patterns: List<Pair<String, List<String>>>, path: List<String>): String {
        var ret = path.joinToString(",")
        patterns.forEach { (function, pat) ->
            val old = pat.joinToString(",")
            ret = ret.replace(old, function)
        }
        return ret
    }

    fun solvePart1(): Int {
        val map = generateMap()
        println(printArea(map))
        return countIntersections(map)
    }

    fun solvePart2(): Long {
        val map = generateMap()
        val path = findPath(map)
        println(path.joinToString(","))
        val patterns = findPatterns(path)
        val functions = makeFunctions(patterns, path).map { it.toLong() }.toMutableList().apply { add(10L) }
        println(functions)
        val actualProgram = parsedInput.toMutableList().apply { this[0] = 2L }.toList()
        val c = Intcode(actualProgram)
        c.input.addAll(functions)
        patterns.forEach { (fn, moves) ->
            val input = moves.joinToString(",").map { it.toLong() }.toMutableList().apply { add(10L) }
                    .also { println("Function $fn: $it") }
            c.input.addAll(input)
        }
        c.input.add(110L) //n
        c.input.add(10L) // newline
        c.run()
        return c.output.last()
    }
}