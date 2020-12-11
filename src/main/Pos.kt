import kotlin.math.abs

data class Pos(val x: Int, val y: Int) {
    val distanceToOrigin: Int
        get() {
            return abs(x) + abs(y)
        }

    /**
     * Manhattan distance to another point
     */
    fun distanceTo(other: Pos) = abs(x - other.x) + abs(y - other.y)

    /**
     * Return the position at the given amount of steps in the given direction
     */
    fun move(dir: Direction, steps: Int = 1) = dir.from(this, steps)

    infix fun isAdjacentTo(other: Pos): Boolean {
        return (x == other.x && abs(y - other.y) == 1) ||
                (abs(x - other.x) == 1 && y == other.y)
    }

    fun allNeighbours(includeDiagonals: Boolean = false): List<Pos> {
        return allDeltas(includeDiagonals).map { delta ->  Pos(x + delta.x, y + delta.y) }
    }

    override fun toString() = "($x, $y)"

    companion object {
        fun allDeltas(includeDiagonals: Boolean = false): List<Pos> {
            val ret = mutableListOf<Pos>()
            for (dy in -1..1) {
                for (dx in -1..1) {
                    if (dy == 0 && dx == 0) continue
                    if (includeDiagonals || dy == 0 || dx == 0) {
                        ret.add(Pos(dx, dy))
                    }
                }
            }
            return ret
        }
    }
}