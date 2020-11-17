package aoc2018

class Day10(input: List<String>) {
    private data class Pos(val x: Int, val y: Int) {
        fun hasNeighbour(allPoints: Set<Pos>): Boolean {
            return List(3) { nx -> List(3) { ny -> Pos(x - 1 + nx, y - 1 + ny) } }
                    .flatten()
                    .filterNot { it == this }
                    .any { allPoints.contains(it) }
        }
    }

    private data class Point(val x: Int, val y: Int, val dx: Int, val dy: Int) {
        fun posAt(time: Int) = Pos(x + dx * time, y + dy * time)
    }

    private val initialState = parseInput(input)
    private fun List<Point>.at(time: Int) = map { it.posAt(time) }.toSet()

    private fun parseInput(input: List<String>): List<Point> {
        return input.map {
            // To parse: position=< 3,  6> velocity=<-1, -1>
            Point(
                    it.substringAfter("position=<").substringBefore(",").trim().toInt(),
                    it.substringAfter(",").substringBefore(">").trim().toInt(),
                    it.substringAfter("velocity=<").substringBefore(",").trim().toInt(),
                    it.substringAfterLast(",").dropLast(1).trim().toInt()
            )
        }
    }

    private fun findMessage(): Int {
        for (time in 1..100000) {
            val currentArrangement = initialState.at(time)
            if (currentArrangement.all { it.hasNeighbour(currentArrangement) }) {
                return time
            }
        }
        return -1
    }

    private fun printMessage(points: Set<Pos>) {
        for (y in points.minByOrNull { it.y }!!.y..points.maxByOrNull { it.y }!!.y) {
            for (x in points.minByOrNull { it.x }!!.x..points.maxByOrNull { it.x }!!.x) {
                print(if (points.contains(Pos(x, y))) '#' else ' ')
            }
            println()
        }
    }

    fun solvePart1(): Int {
        val messageTime = findMessage()
        printMessage(initialState.at(messageTime))
        return messageTime
    }

    fun solvePart2(): Int {
        return findMessage()
    }
}