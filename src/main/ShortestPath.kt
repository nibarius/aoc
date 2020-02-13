import org.magicwerk.brownies.collections.GapList
import kotlin.math.abs

// traversable specifies which characters in the map are traversable
// order specifies which direction have priority if two different directions result in the same length path
class ShortestPath(private val traversable: List<Char>,
                   private val order: List<Direction> = listOf(Direction.Up, Direction.Left, Direction.Right, Direction.Down)) {

    fun availableNeighbours(map: AMap, pos: Pos): List<Pos> {
        return order.map { dir -> dir.from(pos) }
                .filter { newPos -> map.containsKey(newPos) && traversable.contains(map[newPos]) }
    }

    fun adjacentTo(pos1: Pos, pos2: Pos): Boolean {
        return (pos1.x == pos2.x && abs(pos1.y - pos2.y) == 1) ||
                (abs(pos1.x - pos2.x) == 1 && pos1.y == pos2.y)
    }

    private data class State(val currentPos: Pos, val previous: List<Pos>)

    fun find(map: AMap, from: Pos, to: Pos): List<Pos> {

        val toCheck = GapList<State>()
        availableNeighbours(map, from).forEach { toCheck.add(State(it, listOf())) }
        val alreadyChecked = mutableSetOf<Pos>()
        while (toCheck.isNotEmpty()) {
            val current = toCheck.remove()
            if (alreadyChecked.contains(current.currentPos)) continue
            val path = current.previous.toMutableList()
            path.add(current.currentPos)
            if (current.currentPos == to) {
                return path
            }
            alreadyChecked.add(current.currentPos)
            availableNeighbours(map, current.currentPos).forEach { toCheck.add(State(it, path)) }
        }
        return emptyList()
    }
}