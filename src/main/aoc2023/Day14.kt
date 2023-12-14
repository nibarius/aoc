package aoc2023

import AMap
import Pos
import size

class Day14(input: List<String>) {
    private val platform = AMap.parse(input, listOf('.'))
    private val fixedRocksOnly = AMap(platform.toMap().filterValues { it == '#' }.toMutableMap())

    private fun AMap.tiltNorth(): AMap {
        val state = this
        val next = fixedRocksOnly.copy()
        for (x in next.xRange()) {
            var offset = 0
            while (offset in state.yRange()) {
                var round = 0
                var emptySpace = 0
                var y = offset
                // Count number of empty spaces and round rocks until the next square rock
                while (y in state.yRange()) {
                    when (state[Pos(x, y++)]) {
                        null -> emptySpace++
                        'O' -> round++
                        '#' -> break
                    }
                }
                // Move up all round rocks
                for (i in offset..<offset + round) {
                    next[Pos(x, i)] = 'O'
                }
                // Skip part the square rock for the next iteration
                offset = y
            }
        }
        return next
    }

    private fun AMap.tiltWest(): AMap {
        val state = this
        val next = fixedRocksOnly.copy()
        for (y in next.yRange()) {
            var offset = 0
            while (offset in state.yRange()) {
                var round = 0
                var emptySpace = 0
                var x = offset
                // Count number of empty spaces and round rocks until the next square rock
                while (x in state.xRange()) {
                    when (state[Pos(x++, y)]) {
                        null -> emptySpace++
                        'O' -> round++
                        '#' -> break
                    }
                }
                // Move west all round rocks
                for (i in offset..<offset + round) {
                    next[Pos(i, y)] = 'O'
                }
                // Skip part the square rock for the next iteration
                offset = x
            }
        }
        return next
    }

    private fun AMap.tiltSouth(): AMap {
        val state = this
        val next = fixedRocksOnly.copy()
        for (x in next.xRange()) {
            var offset = state.yRange().last
            while (offset in state.yRange()) {
                var round = 0
                var emptySpace = 0
                var y = offset
                // Count number of empty spaces and round rocks until the next square rock
                while (y in state.yRange()) {
                    when (state[Pos(x, y--)]) {
                        null -> emptySpace++
                        'O' -> round++
                        '#' -> break
                    }
                }
                // Move down all round rocks
                for (i in (offset - round + 1..offset).reversed()) {
                    next[Pos(x, i)] = 'O'
                }
                // Skip part the square rock for the next iteration
                offset = y
            }
        }
        return next
    }

    private fun AMap.tiltEast(): AMap {
        val state = this
        val next = fixedRocksOnly.copy()
        for (y in next.yRange()) {
            var offset = state.xRange().last
            while (offset in state.xRange()) {
                var round = 0
                var emptySpace = 0
                var x = offset
                // Count number of empty spaces and round rocks until the next square rock
                while (x in state.xRange()) {
                    when (state[Pos(x--, y)]) {
                        null -> emptySpace++
                        'O' -> round++
                        '#' -> break
                    }
                }
                // Move east all round rocks
                for (i in (offset - round + 1..offset).reversed()) {
                    next[Pos(i, y)] = 'O'
                }
                // Skip part the square rock for the next iteration
                offset = x
            }
        }
        return next
    }

    private fun doOneCycle(state: AMap): AMap {
        return state.tiltNorth().tiltWest().tiltSouth().tiltEast()
    }

    private fun doManyCycles(state: AMap): Int {
        var hash = state.toString('.').hashCode()
        val memory = mutableMapOf(hash to 0)
        val loads = mutableListOf(state.load())
        var i = 1
        var current = state
        while (true) {
            val next = doOneCycle(current)
            hash = next.toString('.').hashCode()
            if (memory.containsKey(hash)) {
                // found a loop
                break
            }
            memory[hash] = i++
            loads.add(next.load())
            current = next
        }
        val loopStart = memory[hash]!!
        val len = i - loopStart
        val targetOffset = (1_000_000_000 - loopStart) % len

        return loads[loopStart + targetOffset]
    }

    private fun AMap.load(): Int {
        val size = xRange().size()
        return toMap().filterValues { it == 'O' }.keys.sumOf { size - it.y }
    }

    fun solvePart1(): Int {
        return platform.tiltNorth().load()
    }

    fun solvePart2(): Int {
        // Found loop after 151 cycles. Loops back to cycle 81, takes 1min 18 sec
        return doManyCycles(platform)
    }
}