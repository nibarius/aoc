import org.junit.Assert
import org.junit.Test

class ShortestPathTest {
    private fun List<String>.createMap(): Map<Pos, Char> {
        val ret = mutableMapOf<Pos, Char>()
        for (y in 0 until size) {
            for (x in 0 until this[0].length) {
                ret[Pos(x, y)] = this[y][x]
            }
        }
        return ret
    }

    @Test
    fun testOneStep() {
        val map = listOf(
                "####",
                "#AB#",
                "#..#",
                "####").createMap()
        val pathfinder = ShortestPath(listOf('.', 'A', 'B'))
        // A -> B
        Assert.assertEquals(listOf(Pos(1, 2)), pathfinder.find(map, Pos(1, 1), Pos(1, 2)))
        // B -> A
        Assert.assertEquals(listOf(Pos(1, 1)), pathfinder.find(map, Pos(2, 1), Pos(1, 1)))
    }

    @Test
    fun testTwoSteps() {
        val map = listOf(
                "####",
                "#A.#",
                "#.B#",
                "####").createMap()
        val pathfinder = ShortestPath(listOf('.', 'A', 'B'))
        // A -> B
        Assert.assertEquals(listOf(Pos(2, 1), Pos(2, 2)), pathfinder.find(map, Pos(1, 1), Pos(2, 2)))
        // B -> A
        Assert.assertEquals(listOf(Pos(2, 1), Pos(1, 1)), pathfinder.find(map, Pos(2, 2), Pos(1, 1)))
    }

    @Test
    fun testFourSteps() {
        val map = listOf(
                "#######",
                "#.S...#",
                "#.....#",
                "#...G.#",
                "#######").createMap()
        val pathfinder = ShortestPath(listOf('.', 'S', 'G'))
        Assert.assertEquals(listOf(Pos(3, 1), Pos(4, 1), Pos(4, 2), Pos(4, 3)), pathfinder.find(map, Pos(2, 1), Pos(4, 3)))
    }

    @Test
    fun testBlockedPath() {
        val map = listOf(
                "#######",
                "#.S...#",
                "#.###.#",
                "#...G##",
                "#######").createMap()
        val pathfinder = ShortestPath(listOf('.', 'S', 'G'))
        val expected = listOf(Pos(1, 1), Pos(1, 2), Pos(1, 3),
                Pos(2, 3), Pos(3,3), Pos(4, 3))
        Assert.assertEquals(expected, pathfinder.find(map, Pos(2, 1), Pos(4, 3)))
    }

    @Test
    fun testNoPath() {
        val map = listOf(
                "#######",
                "#.S...#",
                "#.###.#",
                "#..#G##",
                "#######").createMap()
        val pathfinder = ShortestPath(listOf('.', 'S', 'G'))
        Assert.assertEquals(listOf<Pos>(), pathfinder.find(map, Pos(2, 1), Pos(4, 3)))
    }
}