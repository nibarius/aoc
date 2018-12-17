import org.magicwerk.brownies.collections.GapList
import kotlin.math.abs

class ShortestPath(private val traversable: List<Char>) {

    data class Pos(val x: Int, val y: Int) {
        override fun toString(): String {
            return "($x, $y)"
        }
    }

    // Order of directions specifies which is preferred when several paths is shortest
    private enum class Dir(val dx: Int, val dy: Int) {
        UP(0, -1),
        LEFT(-1, 0),
        RIGHT(1, 0),
        DOWN(0, 1);

        fun from(pos: Pos) = Pos(pos.x + dx, pos.y + dy)
    }

    fun availableNeighbours(map: Map<Pos, Char>, pos: Pos): List<Pos> {
        return Dir.values().map { dir -> dir.from(pos) }
                .filter {newPos -> map.containsKey(newPos) && traversable.contains(map[newPos])}
    }

    fun adjacentTo(pos1: Pos, pos2: Pos): Boolean {
        return (pos1.x == pos2.x && abs(pos1.y - pos2.y) == 1) ||
                (abs(pos1.x - pos2.x) == 1 && pos1.y == pos2.y)
    }

    private data class State(val currentPos: Pos, val previous: List<Pos>)

    fun find(map: Map<Pos, Char>, from: Pos, to: Pos): List<Pos> {

        val toCheck = GapList<State>()
        availableNeighbours(map, from).forEach { toCheck.add(State(it, listOf())) }
        val alreadyChecked = GapList<Pos>()
        while (toCheck.isNotEmpty()) {
            val current = toCheck.removeAt(0)
            val path = current.previous.toMutableList()
            path.add(current.currentPos)
            if (current.currentPos == to) {
                return path
            }
            alreadyChecked.add(current.currentPos)
            availableNeighbours(map, current.currentPos)
                    .filter { !alreadyChecked.contains(it) } // Exclude pos that is part of any shorter path
                    .filter { neighbour -> toCheck.find { it.currentPos == neighbour } == null } // Exclude already queued position
                    .forEach { toCheck.add(State(it, path)) }
        }
        return emptyList()
    }

}