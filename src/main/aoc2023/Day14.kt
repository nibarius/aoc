package aoc2023

import Grid

class Day14(input: List<String>) {

    // Do double buffering and copy data between two grids to minimize allocations
    private val grid = Grid.parse(input)
    private val grid2 = grid.copy()

    private fun Grid.tiltNorth(dest: Grid): Grid {
        for (x in 0..<size) {
            var offset = 0
            while (offset < size) {
                var round = 0
                var emptySpace = 0
                var y = offset
                while (y < size) {
                    when (this[y++][x]) {
                        '.' -> emptySpace++
                        'O' -> round++
                        '#' -> break
                    }
                    dest[y - 1][x] = '.' // Clear everything except square rocks
                }
                // Move all round rocks to the north
                for (i in offset..<offset + round) {
                    dest[i][x] = 'O'
                }
                // Skip past the square rock for the next iteration
                offset = y
            }
        }
        return dest
    }

    private fun Grid.tiltWest(dest: Grid): Grid {
        for (y in 0..<size) {
            var offset = 0
            while (offset < size) {
                var round = 0
                var emptySpace = 0
                var x = offset
                while (x < size) {
                    when (this[y][x++]) {
                        '.' -> emptySpace++
                        'O' -> round++
                        '#' -> break
                    }
                    dest[y][x - 1] = '.' // Clear everything except square rocks
                }
                // Move all round rocks to the west
                for (i in offset..<offset + round) {
                    dest[y][i] = 'O'
                }
                // Skip past the square rock for the next iteration
                offset = x
            }
        }
        return dest
    }

    private fun Grid.tiltSouth(dest: Grid): Grid {
        for (x in 0..<size) {
            var offset = size - 1
            while (offset >= 0) {
                var round = 0
                var emptySpace = 0
                var y = offset
                while (y >= 0) {
                    when (this[y--][x]) {
                        '.' -> emptySpace++
                        'O' -> round++
                        '#' -> break
                    }
                    dest[y + 1][x] = '.' // Clear everything except square rocks
                }
                // Move all round rocks to the south
                for (i in (offset - round + 1..offset).reversed()) {
                    dest[i][x] = 'O'
                }
                // Skip past the square rock for the next iteration
                offset = y
            }
        }
        return dest
    }

    private fun Grid.tiltEast(dest: Grid): Grid {
        for (y in 0..<size) {
            var offset = size - 1
            while (offset >= 0) {
                var round = 0
                var emptySpace = 0
                var x = offset
                while (x >= 0) {
                    when (this[y][x--]) {
                        '.' -> emptySpace++
                        'O' -> round++
                        '#' -> break
                    }
                    dest[y][x + 1] = '.' // Clear everything except square rocks
                }
                // Move all round rocks to the east
                for (i in offset - round + 1..offset) {
                    dest[y][i] = 'O'
                }
                // Skip past the square rock for the next iteration
                offset = x
            }
        }
        return dest
    }

    private fun doOneCycle(): Grid {
        // Not nice to have 4 almost identical methods, but doing a generic method
        // that can handle both north and south increases runtime from 50ms to over 200ms
        return grid.tiltNorth(grid2).tiltWest(grid).tiltSouth(grid2).tiltEast(grid)
    }

    private fun doManyCycles(): Int {
        var hash = grid.hashCode()
        val memory = mutableMapOf(hash to 0)
        val loads = mutableListOf(grid.load())
        var i = 1
        while (true) {
            val next = doOneCycle()
            hash = next.hashCode()
            if (memory.containsKey(hash)) {
                // found a loop
                break
            }
            memory[hash] = i++
            loads.add(next.load())
        }
        val loopStart = memory[hash]!!
        val len = i - loopStart
        val targetOffset = (1_000_000_000 - loopStart) % len

        return loads[loopStart + targetOffset]
    }

    private fun Grid.load(): Int {
        return keys.filter { get(it) == 'O' }.sumOf { size - it.y }
    }

    fun solvePart1(): Int {
        return grid.tiltNorth(grid2).load()
    }

    fun solvePart2(): Int {
        return doManyCycles()
    }
}