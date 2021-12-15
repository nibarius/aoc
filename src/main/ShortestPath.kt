import java.util.*
import kotlin.math.abs

// traversable specifies which characters in the map are traversable
// order specifies which direction have priority if two different directions result in the same length path
class ShortestPath(private val traversable: List<Char>,
                   private val order: List<Direction> = listOf(Direction.Up, Direction.Left, Direction.Right, Direction.Down)) {

    fun availableNeighbours(map: AMap, pos: Pos): List<Pos> {
        return order.map { dir -> dir.from(pos) }
                .filter { newPos -> map.containsKey(newPos) && traversable.contains(map[newPos]) }
    }

    private data class State(val currentPos: Pos, val previous: List<Pos>, val costSoFar: Int)

    fun find(map: AMap, from: Pos, to: Pos, costFn: (Pos) -> Int = { 1 }): List<Pos> {
        val toCheck = PriorityQueue(compareBy<State> { state ->
            state.costSoFar + abs(state.currentPos.x - to.x) + abs(state.currentPos.y - to.y)
        })
        availableNeighbours(map, from).forEach { toCheck.add(State(it, listOf(), costFn(it))) }
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
            availableNeighbours(map, current.currentPos)
                .filterNot { it in alreadyChecked }
                .forEach { toCheck.add(State(it, path, current.costSoFar + costFn(it))) }
        }
        return emptyList()
    }
}