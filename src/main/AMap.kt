// A map where coordinates are Pos and values are Char
class AMap(private val map: MutableMap<Pos, Char> = mutableMapOf()) {
    operator fun get(pos: Pos): Char? = map[pos]
    operator fun set(pos: Pos, ch: Char) {
        map[pos] = ch
    }

    val keys
        get() = map.keys
    val values
        get() = map.values

    fun getOrDefault(pos: Pos, defaultValue: Char) = map.getOrDefault(pos, defaultValue)
    fun xRange() = map.xRange()
    fun yRange() = map.yRange()
    fun toMap() = map.toMap()
    fun copy() = AMap(map.toMutableMap())
    fun containsKey(key: Pos) = map.containsKey(key)

    /**
     * Returns the number of neighbours with the given value. Neighbours outside of
     * the map are not counted.
     */
    fun numNeighboursWithValue(pos: Pos, value: Char, includeDiagonals: Boolean = false): Int {
        return pos.allNeighbours(includeDiagonals)
                .filter { map[it] == value }
                .size
    }

    fun toString(emptySpace: Char = '#'): String {
        return (yRange()).joinToString("\n") { y ->
            (xRange()).joinToString("") { x ->
                getOrDefault(Pos(x, y), emptySpace).toString()
            }
        }
    }

    @Suppress("unused") // Useful for debugging
    fun printArea(emptySpace: Char = '#') {
        toString(emptySpace).split('\n').forEach { println(it) }
    }

    private fun Map<Pos, Char>.xRange() = keys.minBy { it.x }!!.x..keys.maxBy { it.x }!!.x
    private fun Map<Pos, Char>.yRange() = keys.minBy { it.y }!!.y..keys.maxBy { it.y }!!.y

    companion object {
        fun parse(input: List<String>): AMap {
            val map = AMap()
            for (y in input.indices) {
                for (x in input[0].indices) {
                    map[Pos(x, y)] = input[y][x]
                }
            }
            return map
        }
    }
}