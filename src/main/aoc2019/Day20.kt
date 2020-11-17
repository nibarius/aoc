package aoc2019

import org.magicwerk.brownies.collections.GapList

class Day20(input: List<String>) {

    data class Bounds(val u: Int, val iu: Int, val id: Int, val d: Int, val l: Int, val il: Int, val ir: Int, val r: Int)

    val parsedInput = parseInput(input)
    private val bounds = findBounds(parsedInput)

    private fun Map<Pos, Char>.maxY() = keys.maxByOrNull { it.y }!!.y
    private fun Map<Pos, Char>.maxX() = keys.maxByOrNull { it.x }!!.x

    private fun parseInput(input: List<String>): Map<Pos, Char> {
        val ret = mutableMapOf<Pos, Char>()
        for (y in input.indices) {
            for (x in input[y].indices) {
                ret[Pos(x, y)] = input[y][x]
            }
        }
        return ret
    }

    private fun findBounds(map: Map<Pos, Char>): Bounds {
        val bounds = mutableListOf(2)

        val fixedX = map.maxY() / 2
        for (y in 2..map.maxY()) {
            if (bounds.size == 1 && !listOf('.', '#').contains(map[Pos(fixedX, y)])) {
                bounds.add(y - 1)
            }
            if (bounds.size == 2 && listOf('.', '#').contains(map[Pos(fixedX, y)])) {
                bounds.add(y)
            }
            if (bounds.size == 3 && !listOf('.', '#').contains(map[Pos(fixedX, y)])) {
                bounds.add(y - 1)
                break
            }
        }

        bounds.add(2)
        val fixedY = map.maxX() / 2
        for (x in 2..map.maxX()) {
            if (bounds.size == 5 && !listOf('.', '#').contains(map[Pos(x, fixedY)])) {
                bounds.add(x - 1)
            }
            if (bounds.size == 6 && listOf('.', '#').contains(map[Pos(x, fixedY)])) {
                bounds.add(x)
            }
            if (bounds.size == 7 && !listOf('.', '#').contains(map[Pos(x, fixedY)])) {
                bounds.add(x - 1)
                break
            }
        }

        return Bounds(bounds[0], bounds[1], bounds[2], bounds[3], bounds[4], bounds[5], bounds[6], bounds[7])
    }

    private fun findPortals(map: Map<Pos, Char>): Pair<MutableMap<Pos, Pos>, MutableMap<String, MutableSet<Pos>>> {
        val portals = map.filter { listOf('.').contains(it.value) }.keys
                .filter {
                    it.y in listOf(bounds.u, bounds.d) || // Outer top or bottom
                            it.x in listOf(bounds.l, bounds.r) || // Outer left or right
                            (it.y in listOf(bounds.iu, bounds.id) && it.x in bounds.il..bounds.ir) || // inner top/bottom
                            (it.x in listOf(bounds.il, bounds.ir) && it.y in bounds.iu..bounds.id) // inner left/right
                }
        val labels = mutableMapOf<String, MutableSet<Pos>>()

        portals.forEach {
            val label = labelForPortal(map, it)
            if (!labels.containsKey(label)) {
                labels[label] = mutableSetOf()
            }
            labels[label]!!.add(it)
        }

        val portalMap = mutableMapOf<Pos, Pos>()
        labels.values.forEach { coordinates ->
            if (coordinates.size > 1) { // Ignore entry and exit
                portalMap[coordinates.first()] = coordinates.last()
                portalMap[coordinates.last()] = coordinates.first()
            }
        }
        return Pair(portalMap, labels)
    }

    private fun labelForPortal(map: Map<Pos, Char>, portal: Pos): String = when {
        portal.y in listOf(bounds.u, bounds.id) ->
            "${map[Pos(portal.x, portal.y - 2)]}${map[Pos(portal.x, portal.y - 1)]}"
        portal.y in listOf(bounds.iu, bounds.d) ->
            "${map[Pos(portal.x, portal.y + 1)]}${map[Pos(portal.x, portal.y + 2)]}"
        portal.x in listOf(bounds.l, bounds.ir) ->
            "${map[Pos(portal.x - 2, portal.y)]}${map[Pos(portal.x - 1, portal.y)]}"
        portal.x in listOf(bounds.il, bounds.r) ->
            "${map[Pos(portal.x + 1, portal.y)]}${map[Pos(portal.x + 2, portal.y)]}"
        else -> throw IllegalStateException("Not a portal: $portal")
    }

    private fun shortestPath(withFloors: Boolean): Int {
        val (portals, labels) = findPortals(parsedInput)
        val start = labels["AA"]!!.first()
        val end = labels["ZZ"]!!.first()
        return find(parsedInput, start, end, portals, withFloors).size
    }

    fun solvePart1(): Int {
        return shortestPath(false)
    }

    fun solvePart2(): Int {
        return shortestPath(true)
    }

    private fun portalDirection(portal: Pos) =
            when {
                portal.x in listOf(bounds.l, bounds.r) || portal.y in listOf(bounds.u, bounds.d) -> -1
                else -> 1
            }

    data class Pos(val x: Int, val y: Int, val z: Int = 0) {
        override fun toString() =  "($x, $y, $z)"
        fun flatten() = Pos(x, y, 0)
    }

    // Order of directions specifies which is preferred when several paths is shortest
    private enum class Dir(val dx: Int, val dy: Int) {
        UP(0, -1),
        LEFT(-1, 0),
        RIGHT(1, 0),
        DOWN(0, 1);

        fun from(pos: Pos) = Pos(pos.x + dx, pos.y + dy, pos.z)
    }

    private fun availableNeighbours(map: Map<Pos, Char>, pos: Pos, portals: Map<Pos, Pos>, withFloors: Boolean): List<Pos> {
        return Dir.values().map { dir -> dir.from(pos) }
                .filter { newPos -> map.containsKey(newPos.flatten()) && map[newPos.flatten()] == '.' }
                .toMutableList()
                .apply {
                    portals[pos.flatten()]?.let {
                        val newFloor = if (withFloors) pos.z + portalDirection(pos) else 0
                        if (newFloor >= 0 && newFloor < portals.size / 2) {
                            // heuristic for speed improvement, don't go trough portals too many times
                            // on average max once per portal should be enough
                            add(Pos(it.x, it.y, newFloor))
                        }
                    }
                }
    }

    fun find(map: Map<Pos, Char>, from: Pos, to: Pos, portals: Map<Pos, Pos>, withFloors: Boolean = false): List<Pos> {
        val toCheck = GapList<List<Pos>>()
        availableNeighbours(map, from, portals, withFloors).forEach { toCheck.add(listOf(it)) }
        val alreadyChecked = mutableSetOf<Pos>()
        while (toCheck.isNotEmpty()) {
            val pathSoFar = toCheck.remove()
            if (pathSoFar.last() == to) {
                return pathSoFar
            }
            availableNeighbours(map, pathSoFar.last(), portals, withFloors)
                    .forEach {
                        if (it !in alreadyChecked) {
                            alreadyChecked.add(it)
                            toCheck.add(pathSoFar + it)
                        }
                    }
        }
        return emptyList()
    }
}
