import org.junit.Assert
import org.junit.Test

class SearchTest {

    private val raw = """
        ..
        ..
    """.trimIndent().split("\n")
    private val map = AMap.parse(raw)
    private val graph = Graph(map)

    private class Graph(val map: AMap) : Search.WeightedGraph<Pos> {
        override fun neighbours(id: Pos): List<Pos> {
            return id.allNeighbours().filter {
                when {
                    map[it] == null -> false // outside map
                    map[it] == '.' -> true // can walk on .
                    else -> false // no other possible moves
                }
            }
        }

        override fun cost(from: Pos, to: Pos) = 1f
    }

    @Test
    fun testBfs() {
        val res = Search.bfs(graph, Pos(0, 0), Pos(1, 1))
        Assert.assertEquals(2, res.getPath(Pos(1, 1)).size)
    }

    @Test
    fun testDjikstra() {
        val res = Search.djikstra(graph, Pos(0, 0), Pos(1, 1))
        Assert.assertEquals(2, res.getPath(Pos(1, 1)).size)
    }
    @Test
    fun testAStar() {
        val res = Search.aStar(graph, Pos(0, 0), Pos(1, 1), { from, to -> from.distanceTo(to).toFloat() })
        Assert.assertEquals(2, res.getPath(Pos(1, 1)).size)
    }

    // Additional tests from past puzzles:
    // BFS - 2022 day 12
    // Djikstra - 2021 day 15
    // A* - 2018 day 22

}