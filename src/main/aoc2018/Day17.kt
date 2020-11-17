package aoc2018

class Day17(input: List<String>, isGraph: Boolean = false) {
    data class Pos(val x: Int, val y: Int)

    private val area = mutableMapOf<Pos, Char>()

    init {
        if (isGraph) {
            for (y in input.indices) {
                for (x in input[0].indices) {
                    area[Pos(x, y)] = input[y][x]
                }
            }
        } else {
            input.forEach { line ->
                val first = line.drop(2).substringBefore(",").toInt()
                val second1 = line.substringAfterLast("=").substringBefore("..").toInt()
                val second2 = line.substringAfterLast("..").toInt()
                val ranges = mapOf(
                        line.first() to first..first,
                        line.substringAfter(", ").first() to second1..second2)

                for (y in ranges.getValue('y')) {
                    for (x in ranges.getValue('x')) {
                        area[Pos(x, y)] = '#'
                    }
                }
                area[Pos(500, 0)] = '+'
            }
        }
    }

    fun printArea() {
        for (y in area.keys.minByOrNull { it.y }!!.y..area.keys.maxByOrNull { it.y }!!.y) {
            for (x in area.keys.minByOrNull { it.x }!!.x..area.keys.maxByOrNull { it.x }!!.x) {
                print("${area.getOrDefault(Pos(x, y), '.')}")
            }
            println()
        }
    }

    // Makes a straight column of | from the given position (not included) until it hits something
    // else than air. Returns y position -1 if the water shouldn't continue flowing
    private fun fall(from: Pos): Pos {
        var currY = from.y
        while (true) {
            currY++
            val content = area.getOrDefault(Pos(from.x, currY), '.')
            if (currY > area.keys.maxByOrNull { it.y }!!.y) {
                // Stop when it falls to infinity
                return Pos(from.x, -1)
            }
            if (content == '|') {
                // Don't continue falling if it hit other flowing water
                return Pos(from.x, -1)
            } else if (content == '#' || content == '~') {
                // Stop falling and allow filling when it hit clay or water
                return Pos(from.x, currY - 1)
            }
            area[Pos(from.x, currY)] = '|'
        }
    }

    private fun fillDirection(from: Pos, direction: Int): List<Pos> {
        var currX = from.x
        var toSide = area.getOrDefault(Pos(currX + direction, from.y), '.')
        var below = area.getOrDefault(Pos(currX, from.y + 1), '.')
        while (listOf('.', '|').contains(toSide) && below != '.') {
            currX += direction
            area[Pos(currX, from.y)] = '~'
            toSide = area.getOrDefault(Pos(currX + direction, from.y), '.')
            below = area.getOrDefault(Pos(currX, from.y + 1), '.')
        }
        if (below == '.') {
            return listOf(Pos(currX, from.y))
        }
        return listOf()
    }

    // Fill a container with ~ from the given starting point. Returns a list of
    // all points where it overflows.
    private fun fill(from: Pos): List<Pos> {
        val overflows = mutableListOf<Pos>()
        var currY = from.y
        while (overflows.isEmpty()) {
            // start position's column
            area[Pos(from.x, currY)] = '~'

            // to left and right of start position's column
            listOf(-1, 1).forEach { direction -> overflows.addAll(fillDirection(Pos(from.x, currY), direction)) }
            currY--
        }

        return overflows
    }

    // Converts all ~ to | in the overflowing row given that the overflow happened
    // at the position 'from'. Returns 'from'
    private fun overflow(from: Pos): Pos {
        val direction = if (area[Pos(from.x + 1, from.y)] == '~') 1 else -1
        var currX = from.x
        var content = area.getOrDefault(Pos(currX, from.y), '.')
        while (content == '~') {
            area[Pos(currX, from.y)] = '|'
            currX += direction
            content = area.getOrDefault(Pos(currX, from.y), '.')
        }
        return from
    }

    private fun fillEverything() {
        val toFall = mutableSetOf<Pos>()
        toFall.add(area.filter { it.value == '+' }.keys.first())
        while (toFall.isNotEmpty()) {
            //println("$iter, toFall size: ${toFall.size}, ${toFall}")
            //printArea()
            val next = toFall.first()
            toFall.remove(next)
            val toFill = fall(next)
            if (toFill.y != -1) {
                val overflows = fill(toFill)
                overflows.forEach { pos ->
                    overflow(pos)
                    toFall.add(pos)
                }
            }
        }
    }

    fun solvePart1(): Int {
        fillEverything()
        val minY = area.filter { it.value == '#' }.minByOrNull { it.key.y }!!.key.y
        return area.filter { it.key.y >= minY }.count { it.value == '|' || it.value == '~' }
    }


    fun solvePart2(): Int {
        fillEverything()
        return area.values.count { it == '~' }
    }
}