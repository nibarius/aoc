import kotlin.math.abs

data class Pos(val x: Int, val y: Int) {
    val distanceToOrigin: Int
        get() {
            return abs(x) + abs(y)
        }

    // Manhattan distance to another point
    fun distanceTo(other: Pos) = abs(x - other.x) + abs(y - other.y)

    // Return the position at the given amount of steps in the given direction
    fun move(dir: Direction, steps: Int = 1) = dir.from(this, steps)

    override fun toString() = "($x, $y)"
}