package aoc2020

import Direction
import Grid
import Pos
import xRange
import yRange

class Day20(input: List<String>) {

    private data class Tile(val id: Long, val data: Grid) {
        var rotation: Grid.Transformation.Rotation = Grid.Transformation.NoRotation
        var flip: Grid.Transformation.Flip = Grid.Transformation.NoFlip

        /**
         * Get this tile as a grid without any borders and it's transformations applied
         */
        fun withoutBorders() = data.subGrid(listOf(rotation, flip), Pos(1, 1), data.size - 2)

        fun getSide(dir: Direction) = data.getSide(dir, listOf(rotation, flip))

        /**
         * Test all rotation/flip combinations and see if any match.
         * If it does update the rotation/flip info for this tile and return true
         */
        fun anyTransformationMatchingSide(side: String, direction: Direction): Boolean {
            Grid.possibleTransformations.forEach { transformations ->
                if (side == data.getSide(direction, transformations)) {
                    rotation = transformations.filterIsInstance<Grid.Transformation.Rotation>().single()
                    flip = transformations.filterIsInstance<Grid.Transformation.Flip>().single()
                    return true
                }
            }
            return false
        }
    }

    private val tiles = input.map { rawTile ->
        val lines = rawTile.split("\n")
        val id = lines.first().substringAfter("Tile ").dropLast(1).toLong()
        val tile = Grid.parse(lines.drop(1).dropLastWhile { it.isEmpty() }) // real input has a trailing blank line
        Tile(id, tile)
    }

    /**
     * Returns a Pos to tile id map that orders the tiles in the correct order.
     * Coordinates of the tiles forms a square and Pos(0,0) is located somewhere inside the square.
     */
    private fun sortTiles(): Map<Pos, Long> {
        val tilesLeft = tiles.associateBy { it.id }.toMutableMap() // id to tile map
        // Start with the first tile without rotating it, and fix it's location to (0, 0)
        val found = mutableMapOf(Pos(0, 0) to tiles.first().id)
        val positionsToFindNeighboursOf = mutableListOf(Pos(0, 0))
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
        val tileDataSize = tiles.first().data.size - 2
        val numTilesInARow = kotlin.math.sqrt(tiles.size.toDouble()).toInt()
        val mergedGrid = Grid(tileDataSize * numTilesInARow)

        // tileY, tileX are the coordinates within the the sorted tiles map
        // indexY, indexX is rebased so that (0, 0) is in the top left corner
        for ((indexY, tileY) in (sorted.yRange()).withIndex()) {
            for ((indexX, tileX) in (sorted.xRange()).withIndex()) {
                val tileId = sorted[Pos(tileX, tileY)]!!
                val currentTile = tiles.find { it.id == tileId }!!
                val data = currentTile.withoutBorders()
                val offset = Pos(indexX * tileDataSize, indexY * tileDataSize)
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

    private fun getSeaMonsterPattern(): Set<Pos> {
        val pattern = mutableSetOf<Pos>()
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

    private fun countMonsters(grid: Grid): Int {
        var count = 0
        val pattern = getSeaMonsterPattern()
        // pattern is 3 times 20 in size
        for (y in 0 until grid.size - 3) {
            for (x in 0 until grid.size - 20) {
                if (pattern.all { grid[y + it.y][x + it.x] == '#' }) {
                    count++
                }
            }
        }
        return count
    }

    fun solvePart1(): Long {
        val sorted = sortTiles()
        val minx = sorted.xRange().first
        val maxx = sorted.xRange().last
        val miny = sorted.yRange().first
        val maxy = sorted.yRange().last
        return sorted[Pos(minx, miny)]!! * sorted[Pos(minx, maxy)]!! * sorted[Pos(maxx, miny)]!! * sorted[Pos(maxx, maxy)]!!
    }

    fun solvePart2(): Int {
        val mergedImage = getMergedImage()
        val numberOfMonsters = Grid.possibleTransformations
            .map { mergedImage.transformed(it) }.maxOf { countMonsters(it) }
        return mergedImage.count('#') - monsterString.count { it == '#' } * numberOfMonsters
    }
}