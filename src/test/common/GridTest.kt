import org.junit.Assert.assertEquals
import org.junit.Test

class GridTest {
    @Test
    fun testRotateLeft() {
        val grid = Grid.parse("""
            123
            abc
            ABC
        """.trimIndent().split("\n"))
        val expected = """
                3cC
                2bB
                1aA
            """.trimIndent()
        assertEquals(expected, grid.toString(listOf(Grid.Transformation.RotateLeft)))
    }

    @Test
    fun testRotateRight() {
        val grid = Grid.parse("""
            123
            abc
            ABC
        """.trimIndent().split("\n"))
        val expected = """
            Aa1
            Bb2
            Cc3
        """.trimIndent()
        assertEquals(expected, grid.toString(listOf(Grid.Transformation.RotateRight)))
    }

    @Test
    fun testRotate180() {
        val grid = Grid.parse("""
            123
            abc
            ABC
        """.trimIndent().split("\n"))
        val expected = """
            CBA
            cba
            321
        """.trimIndent()
        assertEquals(expected, grid.toString(listOf(Grid.Transformation.Rotate180)))
    }

    @Test
    fun testFlipHorizontal() {
        val grid = Grid.parse("""
            123
            abc
            ABC
        """.trimIndent().split("\n"))
        val expected = """
            321
            cba
            CBA
        """.trimIndent()
        assertEquals(expected, grid.toString(listOf(Grid.Transformation.FlipHorizontal)))
    }

    @Test
    fun testFlipVertical() {
        val grid = Grid.parse("""
            123
            abc
            ABC
        """.trimIndent().split("\n"))
        val expected = """
            ABC
            abc
            123
        """.trimIndent()
        assertEquals(expected, grid.toString(listOf(Grid.Transformation.FlipVertical)))
    }

    @Test
    fun testAllPossibleTransformations() {
        val grid = Grid.parse("""
            123
            abc
            ABC
        """.trimIndent().split("\n"))
        val all = Grid.possibleTransformations.map { grid.toString(it) to it }.sortedBy { it.first }
        assertEquals(8, all.size)
        assertEquals(all.size, all.map { it.first }.toSet().size)
    }

    @Test
    fun testNumVisibleAllSeenWithDiagonals() {
        val grid = Grid.parse("""
            .......#.
            ...#.....
            .#.......
            .........
            ..#L....#
            ....#....
            .........
            #........
            ...#.....
        """.trimIndent().split("\n"))
        assertEquals(8, grid.numVisibleWithValue(Pos(3, 4), '#', listOf('.'), true))
    }

    @Test
    fun testNumVisibleAllSeenNoDiagonals() {
        val grid = Grid.parse("""
            .......#.
            ...#.....
            .#.......
            .........
            ..#L....#
            ....#....
            .........
            #........
            ...#.....
        """.trimIndent().split("\n"))
        assertEquals(4, grid.numVisibleWithValue(Pos(3, 4), '#', listOf('.'), false))
    }

    @Test
    fun testNumVisibleBlockingNonTransparent() {
        val grid = Grid.parse("""
            .............
            .L.L.#.#.#.#.
            .............
        """.trimIndent().split("\n"))
        assertEquals(0, grid.numVisibleWithValue(Pos(1, 1), '#', listOf('.'), true))
    }

    @Test
    fun testNumVisibleNoVisible() {
        val grid = Grid.parse("""
            .##.##.
            #.#.#.#
            ##...##
            ...L...
            ##...##
            #.#.#.#
            .##.##.
        """.trimIndent().split("\n"))
        assertEquals(0, grid.numVisibleWithValue(Pos(3, 3), '#', listOf('.'), true))
    }
}