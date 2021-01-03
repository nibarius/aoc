package aoc2020

class Day17(val input: List<String>) {

    data class Point(val x: Int, val y: Int, val z: Int, val w: Int, private val dimensions: Int) {
        // We're accessing neighbours frequently, only calculate the neighbours once and return
        // a cached value if calling this later. In this case, much (~30%) faster than a by lazy property
        // https://stackoverflow.com/questions/65543747/why-does-using-by-lazy-make-my-kotlin-code-slower/
        @OptIn(ExperimentalStdlibApi::class)
        fun getNeighbours(): Set<Point> {
            if (!this::nb.isInitialized) {
                nb = buildSet {
                    for (dx in -1..1) {
                        for (dy in -1..1) {
                            for (dz in -1..1) {
                                for (dw in if (dimensions == 4) -1..1 else 0..0) {
                                    add(Point(x + dx, y + dy, z + dz, w + dw, dimensions))
                                }
                            }
                        }
                    }
                    remove(this@Point)
                }
            }
            return nb
        }

        private lateinit var nb: Set<Point>
    }


    private fun parseInput(dimensions: Int): Map<Point, Char> {
        return input.indices.flatMap { y ->
            input[0].indices.map { x -> Point(x, y, 0, 0, dimensions) to input[y][x] }
        }.toMap()
    }

    private fun runGame(space: Map<Point, Char>): Map<Point, Char> {
        var current = space
        repeat(6) {
            val next = current.toMutableMap()
            val points = next.keys.flatMap { it.getNeighbours() }.toSet() // any neighbour can be transformed
            for (point in points) {
                val activeNeighbours = point.getNeighbours().filter { current[it] == '#' }.count()
                when (current.getOrDefault(point, '.')) {
                    '#' -> if (activeNeighbours !in 2..3) next[point] = '.'
                    '.' -> if (activeNeighbours == 3) next[point] = '#'
                }
            }
            current = next.toMap()
        }
        return current
    }

    fun solvePart1(): Int {
        return runGame(parseInput(3)).values.count { it == '#' }
    }

    fun solvePart2(): Int {
        return runGame(parseInput(4)).values.count { it == '#' }
    }
}