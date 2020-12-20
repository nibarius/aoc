class Grid(val size: Int) {
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

    val data: Array<CharArray> = Array(size) { CharArray(size) { '.' } }

    fun getSide(side: Direction, transformations: List<Transformation>): String {
        val coordinates = when (side) {
            Direction.Up -> List(size) { Pos(it, 0) }
            Direction.Left -> List(size) { Pos(0, it) }
            Direction.Down -> List(size) { Pos(it, size - 1) }
            Direction.Right -> List(size) { Pos(size - 1, it) }
        }
        return coordinates
                .applyTransformations(transformations)
                .map { data[it.y][it.x] }.joinToString("")
    }

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

    private fun List<Pos>.applyTransformations(transformations: List<Transformation>): List<Pos> {
        return map { pos -> transformations.fold(pos) { acc, trans -> transform(acc, trans) } }
    }

    // Return a new grid with the given transformations applied
    fun transformed(transformations: List<Transformation>): Grid {
        val str = toString(transformations)
        return Grid.parse(str.split("\n"))
    }

    fun count(ch: Char): Int {
        return data.sumBy { row -> row.count { it == ch } }
    }

    fun toString(transformations: List<Transformation> = listOf()): String {
        val coordinates = mutableListOf<Pos>()
        for (y in data.indices) {
            for (x in data[0].indices) {
                coordinates.add(Pos(x, y))
            }
        }
        val transformed = coordinates.applyTransformations(transformations)
        val ret = StringBuilder(size * size + size)
        for ((i, pos) in transformed.withIndex()) {
            if (i % size == 0 && i > 0) {
                ret.append("\n")
            }
            ret.append(data[pos.y][pos.x])
        }
        return ret.toString()
    }

    @Suppress("unused") // Useful for debugging
    fun print(transformations: List<Transformation> = listOf()) {
        println(toString(transformations))
    }

    fun writeAllWithOffset(otherData: Grid, offset: Pos) {
        for (y in 0 until otherData.size) {
            for (x in 0 until otherData.size) {
                data[offset.y + y][offset.x + x] = otherData.data[y][x]
            }
        }
    }

    companion object {
        fun parse(input: List<String>): Grid {
            val grid = Grid(input.size)
            for (y in input.indices) {
                for (x in input[0].indices) {
                    grid.data[y][x] = input[y][x]
                }
            }
            return grid
        }
    }
}