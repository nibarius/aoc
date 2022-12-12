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
     * Return the position of the only occurrence of 'value' in the map.
     */
    fun positionOf(value: Char): Pos {
        return map.filter { it.value == value  }.keys.single()
    }

    /**
     * Returns the number of neighbours with the given value. Neighbours outside of
     * the map are not counted.
     */
    fun numNeighboursWithValue(pos: Pos, value: Char, includeDiagonals: Boolean = false): Int {
        return pos.allNeighbours(includeDiagonals)
                .filter { map[it] == value }
                .size
    }

    /**
     * Return the number of visible items with the given value in all directions.
     */
    fun numVisibleWithValue(pos: Pos, value: Char, transparent: List<Char>, includeDiagonals: Boolean = false): Int {
        return Pos.allDeltas(includeDiagonals)
                .map { delta ->
                    generateSequence(pos) { Pos(it.x + delta.x, it.y + delta.y) }
                            .drop(1)
                            .map { map[it] }
                            .first { it !in transparent }
                }
                .sumBy { if (it == value) 1 else 0 }
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