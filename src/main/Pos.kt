import kotlin.math.abs
import kotlin.math.sign

data class Pos(val x: Int, val y: Int) : Comparable<Pos> {
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

    private lateinit var allNb: List<Pos>
    private lateinit var strictNb: List<Pos>

    /**
     * Returns all neighbours of this point. The neighbours are cached after calculated the first time
     * so calling this several times is quick.
     * @param includeDiagonals if diagonals should be seen as neighbours or not.
     */
    fun allNeighbours(includeDiagonals: Boolean = false): List<Pos> {
        return if (includeDiagonals) {
            if (!this::allNb.isInitialized) {
                allNb = allDeltas(includeDiagonals).map { delta -> Pos(x + delta.x, y + delta.y) }
            }
            allNb
        } else {
            if (!this::strictNb.isInitialized) {
                strictNb = allDeltas(includeDiagonals).map { delta -> Pos(x + delta.x, y + delta.y) }
            }
            strictNb
        }
    }

    /**
     * Compare two positions by it's distance to origin, then x coordinate, then y coordinate.
     */
    override fun compareTo(other: Pos): Int = compareValuesBy(this, other, { it.distanceToOrigin }, { it.x }, { it.y })

    override fun toString() = "($x, $y)"

    operator fun times(other: Int) = Pos(x * other, y * other)
    operator fun plus(other: Pos) = Pos(x + other.x, y + other.y)
    operator fun minus(other: Pos) = Pos(x - other.x, y - other.y)

    /**
     * Rotates the position around origin in the given direction and returns the result as a new position.
     * Left is counter-clockwise and right is clockwise.
     */
    fun rotate(dir: Direction): Pos {
        return when (dir) {
            Direction.Left -> Pos(y, -x)
            Direction.Right -> Pos(-y, x)
            else -> throw IllegalArgumentException("Not possible to rotate $dir, Left and Right are the only valid directions")
        }
    }

    /**
     * Returns the sign of the position. Positive values becomes 1, negative -1 and zero stays 0.
     * Example: Pos(5,-23).sign() ==> Pos(1, -1)
     */
    fun sign() = Pos(x.sign, y.sign)

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