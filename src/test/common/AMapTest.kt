import org.junit.Assert.assertEquals
import org.junit.Test

class AMapTest {
    @Test
    fun testNumVisibleAllSeenWithDiagonals() {
        val aMap = AMap.parse("""
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
        assertEquals(8, aMap.numVisibleWithValue(Pos(3, 4), '#', listOf('.'), true))
    }

    @Test
    fun testNumVisibleAllSeenNoDiagonals() {
        val aMap = AMap.parse("""
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
        assertEquals(4, aMap.numVisibleWithValue(Pos(3, 4), '#', listOf('.'), false))
    }

    @Test
    fun testNumVisibleBlockingNonTransparent() {
        val aMap = AMap.parse("""
            .............
            .L.L.#.#.#.#.
            .............
        """.trimIndent().split("\n"))
        assertEquals(0, aMap.numVisibleWithValue(Pos(1, 1), '#', listOf('.'), true))
    }

    @Test
    fun testNumVisibleNoVisible() {
        val aMap = AMap.parse("""
            .##.##.
            #.#.#.#
            ##...##
            ...L...
            ##...##
            #.#.#.#
            .##.##.
        """.trimIndent().split("\n"))
        assertEquals(0, aMap.numVisibleWithValue(Pos(3, 3), '#', listOf('.'), true))
    }
}