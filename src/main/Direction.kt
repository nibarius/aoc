enum class Direction(val dx: Int, val dy: Int) {
    // Order matters due to turnRight / turnLeft implementation
    Up(0, -1),
    Right(1, 0),
    Down(0, 1),
    Left(-1, 0);

    // Return a position the given amount of steps in this direction from the given position
    fun from(pos: Pos, steps: Int = 1) = Pos(pos.x + dx * steps, pos.y + dy * steps)

    fun turnRight() = next()
    fun turnLeft() = prev()
    fun opposite() = next().next()

    fun turn(dir: Direction) = when (dir) {
        Left -> turnLeft()
        Right -> turnRight()
        else -> throw IllegalArgumentException("Not possible to turn $dir, Left and Right are the only valid directions")
    }

    companion object {
        fun fromChar(dir: Char): Direction = when (dir) {
            in setOf('R', '>') -> Right
            in setOf('U', '^') -> Up
            in setOf('L', '<') -> Left
            in setOf('D', 'v') -> Down
            else -> throw IllegalArgumentException("Unknown direction: $dir")
        }
    }
}

fun Char.toDirection() = Direction.fromChar(this)