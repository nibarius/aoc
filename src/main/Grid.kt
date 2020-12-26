/**
 * A fixed square grid of Chars
 * @param size specifies the size of each side of the grid.
 */
class Grid(val size: Int) {
    private val data: Array<CharArray> = Array(size) { CharArray(size) { '.' } }

    /**
     * Transformations that can be applied to a Grid.
     */
    sealed class Transformation {
        abstract class Rotation : Transformation()
        abstract class Flip : Transformation()
        object NoRotation : Rotation()
        object RotateLeft : Rotation()
        object Rotate180 : Rotation() // The same as flipping both vertically and horizontally
        object RotateRight : Rotation()
        object NoFlip : Flip()
        object FlipHorizontal : Flip()
        object FlipVertical : Flip()
    }

    /**
     * Get the value at the given position, using a Pos for addressing.
     */
    operator fun get(pos: Pos): Char = data[pos.y][pos.x]

    /**
     * Get the row for the given y coordinate, used for Grid[y][x] addressing.
     */
    operator fun get(y: Int): CharArray = data[y]

    /**
     * Get all the given side of the Grid as a string
     * @param side the side to return
     * @param transformations a list of transformations that should be applied on the Grid before returning the side
     */
    fun getSide(side: Direction, transformations: List<Transformation>): String {
        val coordinates = when (side) {
            Direction.Up -> List(size) { Pos(it, 0) }
            Direction.Left -> List(size) { Pos(0, it) }
            Direction.Down -> List(size) { Pos(it, size - 1) }
            Direction.Right -> List(size) { Pos(size - 1, it) }
        }
        return coordinates
                .map { transform(it, transformations) }
                .map { this[it] }.joinToString("")
    }

    /**
     * Returns a new grid with the given transformations applied.
     */
    fun transformed(transformations: List<Transformation>): Grid {
        return subGrid(transformations, Pos(0, 0), size)
    }

    /**
     * Returns a new Grid with the given size that is a sub section of the current grid starting from
     * the offset (upper-left corner). The subGrid must be fully contained in this Grid.
     */
    fun subGrid(transformations: List<Transformation>, offset: Pos, newSize: Int): Grid {
        val new = Grid(newSize)
        for (y in 0 until newSize) {
            for (x in 0 until newSize) {
                val transformed = transform(Pos(x + offset.x, y + offset.y), transformations)
                new[y][x] = this[transformed]
            }
        }
        return new
    }

    /**
     * Count the number of occurrences of ch in this Grid
     */
    fun count(ch: Char) = data.sumBy { row -> row.count { it == ch } }

    /**
     * Return this Grid as a string.
     * @param transformations a list of transformations to apply to the returned grid
     */
    fun toString(transformations: List<Transformation> = listOf()): String {
        return transformed(transformations).data.joinToString("\n") { it.joinToString("") }
    }

    @Suppress("unused") // Useful for debugging
    fun print(transformations: List<Transformation> = listOf()) {
        println(toString(transformations))
    }

    /**
     * Write all the data in the otherGrid to this grid. The other grid must fit completely within
     * this grid when starting at the given offset.
     * @param otherGrid source grid to copy data from
     * @param offset offset for where to start writing the other grid's data (top left corner)
     */
    fun writeAllWithOffset(otherGrid: Grid, offset: Pos) {
        for (y in 0 until otherGrid.size) {
            for (x in 0 until otherGrid.size) {
                this[offset.y + y][offset.x + x] = otherGrid[y][x]
            }
        }
    }

    /**
     * Apply the given transformation the given position and return the resulting position.
     */
    private fun transform(pos: Pos, transformation: Transformation): Pos {
        return when (transformation) {
            Transformation.NoRotation -> pos
            Transformation.NoFlip -> pos
            Transformation.RotateLeft -> Pos(size - 1 - pos.y, pos.x)
            Transformation.Rotate180 -> Pos(size - 1 - pos.x, size - 1 - pos.y)
            Transformation.RotateRight -> Pos(pos.y, size - 1 - pos.x)
            Transformation.FlipHorizontal -> Pos(size - 1 - pos.x, pos.y)
            Transformation.FlipVertical -> Pos(pos.x, size - 1 - pos.y)
            else -> error("unreachable")
        }
    }

    /**
     * Apply all the transformations on the given position and return the resulting position.
     */
    private fun transform(pos: Pos, transformations: List<Transformation>): Pos {
        return transformations.fold(pos) { acc, trans -> transform(acc, trans) }
    }

    companion object {
        /**
         * Generates a new Grid based on the given input. The input must be square.
         */
        fun parse(input: List<String>): Grid {
            val grid = Grid(input.size)
            for (y in input.indices) {
                for (x in input[0].indices) {
                    grid.data[y][x] = input[y][x]
                }
            }
            return grid
        }

        /**
         * Set of all possible transformations that can be applied to this Grid that would result in a different Grid.
         */
        val possibleTransformations = setOf(
                listOf(Transformation.NoRotation, Transformation.NoFlip),
                listOf(Transformation.RotateLeft, Transformation.NoFlip),
                listOf(Transformation.Rotate180, Transformation.NoFlip),
                listOf(Transformation.RotateRight, Transformation.NoFlip),
                listOf(Transformation.NoRotation, Transformation.FlipHorizontal), // Same as 180 flip vertical
                listOf(Transformation.NoRotation, Transformation.FlipVertical), // Same as 180 flip horizontal
                listOf(Transformation.RotateLeft, Transformation.FlipHorizontal), // Same as right + vertical flip
                listOf(Transformation.RotateLeft, Transformation.FlipVertical), // Same as right + horizontal flip
        )
    }
}