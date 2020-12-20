package aoc2020

import Direction
import Grid
import Pos

class Day20(input: List<String>) {

    data class Tile(val id: Long, val data: Grid) {
        var rotation: Grid.Transformation.Rotation = Grid.Transformation.NoRotation
        var flip: Grid.Transformation.Flip = Grid.Transformation.NoFlip

        fun print() {
            println("Tile $id:")
            data.print()
        }

        /**
         * Get this tile as a grid without any borders and it's transformations applied
         */
        fun withoutBorders(): Grid {
            return data
                    .toString(listOf(rotation, flip)) // Apply transformations
                    .split("\n")
                    .drop(1) // remove top and bottom border
                    .dropLast(1)
                    .map { line -> // remove left and right border
                        line.drop(1).dropLast(1)
                    }
                    .let { Grid.parse(it) }
        }

        fun getSide(dir: Direction): String {
            return data.getSide(dir, listOf(rotation, flip))
        }

        // test all rotation/flip combinations and see if any match. If it does update the rotation/flip info and return true
        fun anyTransformationMatchingSide(side: String, direction: Direction): Boolean {
            data.possibleTransformations.forEach { transformations ->
                if (side == data.getSide(direction, transformations)) {
                    rotation = transformations.filterIsInstance<Grid.Transformation.Rotation>().single()
                    flip = transformations.filterIsInstance<Grid.Transformation.Flip>().single()
                    return true
                }
            }
            return false
        }
    }

    private fun sortTiles(): Map<Pos, Long> {
        val startTile = tiles.first()
        val tilesLeft = tiles.map { it.id to it }.toMap().toMutableMap() // id to tile map
        val positionsToFindNeighboursOf = mutableListOf(Pos(0, 0))
        val found = mutableMapOf(Pos(0, 0) to startTile.id) // position to tile id. position of the first tile is 0,0
        while (tilesLeft.isNotEmpty()) {
            val currentPos = positionsToFindNeighboursOf.removeFirst()
            val matchAgainst = tilesLeft.remove(found[currentPos])!!

            // check in all directions from the current tile
            for (direction in Direction.values()) {
                val checkPos = currentPos.move(direction)
                if (found.containsKey(checkPos)) {
                    // skip this direction if it's already been identified
                    continue
                }
                // Check among all remaining tiles for the one that matches in this direction
                for ((id, otherTile) in tilesLeft) {
                    val sideToMatch = matchAgainst.getSide(direction)
                    if (otherTile.anyTransformationMatchingSide(sideToMatch, direction.opposite())) {
                        found[checkPos] = id
                        //tilesLeft.remove(id) //todo: not doing this means it's included in checks where it's not needed (all other directions)
                        positionsToFindNeighboursOf.add(checkPos)
                        break
                    }
                }
            }
        }
        return found
    }

    private fun getMergedImage(): Grid {
        val sorted = sortTiles()
        val minx = sorted.keys.minByOrNull { it.x }!!.x
        val maxx = sorted.keys.maxByOrNull { it.x }!!.x
        val miny = sorted.keys.minByOrNull { it.y }!!.y
        val maxy = sorted.keys.maxByOrNull { it.y }!!.y
        val tileDataSize = tiles.first().data.size - 2
        val numTilesInARow = kotlin.math.sqrt(tiles.size.toDouble()).toInt()
        val mergedGrid = Grid(tileDataSize * numTilesInARow)
        for ((yy, y) in (miny..maxy).withIndex()) {
            for ((xx, x) in (minx..maxx).withIndex()) {
                val tileId = sorted[Pos(x, y)]!!
                val currentTile = tiles.find { it.id == tileId }!!
                val data = currentTile.withoutBorders()
                val offset = Pos(xx * tileDataSize, yy * tileDataSize)
                mergedGrid.writeAllWithOffset(data, offset)
            }
        }
        return mergedGrid
    }

    private val monsterString = """
            ..................#.
            #....##....##....###
            .#..#..#..#..#..#...
        """.trimIndent()

    private fun getSeaMonsterPattern(): List<Pos> {
        val pattern = mutableListOf<Pos>()
        val monster = monsterString.split("\n")
        for (y in monster.indices) {
            for (x in monster[0].indices) {
                if (monster[y][x] == '#') {
                    pattern.add(Pos(x, y))
                }
            }
        }
        return pattern
    }

    private fun countMonsters(pattern: List<Pos>, grid: Grid): Int {
        var count = 0
        // pattern is 3 times 20 in size
        for (y in 0 until grid.size - 3) {
            for (x in 0 until grid.size - 20) {
                if (pattern.all { grid.data[y + it.y][x + it.x] == '#' }) {
                    count++
                }
            }
        }
        return count
    }

    val tiles = input.map { rawTile ->
        val lines = rawTile.split("\n")
        val id = lines.first().substringAfter("Tile ").dropLast(1).toLong()
        val tile = Grid.parse(lines.drop(1).dropLastWhile { it.isEmpty() }) // real input has a trailing blank line
        Tile(id, tile)
    }

    fun solvePart1(): Long {
        val sorted = sortTiles()
        val minx = sorted.keys.minByOrNull { it.x }!!.x
        val maxx = sorted.keys.maxByOrNull { it.x }!!.x
        val miny = sorted.keys.minByOrNull { it.y }!!.y
        val maxy = sorted.keys.maxByOrNull { it.y }!!.y
        return sorted[Pos(minx, miny)]!! * sorted[Pos(minx, maxy)]!! * sorted[Pos(maxx, miny)]!! * sorted[Pos(maxx, maxy)]!!
    }

    fun solvePart2(): Int {
        val mergedImage = getMergedImage()
        val pattern = getSeaMonsterPattern()
        val numberOfMonsters = mergedImage.possibleTransformations
                .map { mergedImage.transformed(it) }
                .map { countMonsters(pattern, it) }
                .maxOrNull()!!
        val patternCount = monsterString.count { it == '#' }
        val totalCount = mergedImage.count('#')
        return totalCount - patternCount * numberOfMonsters
    }
}