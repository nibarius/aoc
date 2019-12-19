package aoc2019

class Day19(input: List<String>) {
    val parsedInput = input.map { it.toLong() }

    data class Pos(val x: Int, val y: Int)

    private fun scanArea(): MutableMap<Pos, Char> {
        val map = mutableMapOf<Pos, Char>()
        for (y in 0 until 50) {
            for (x in 0 until 50) {
                Intcode(parsedInput).run {
                    input.add(x.toLong())
                    input.add(y.toLong())
                    run()
                    map[Pos(x, y)] = if (output.removeAt(0) == 1L) '#' else '.'
                }
            }
        }
        return map
    }

    fun solvePart1(): Int {
        return scanArea().values.count { it == '#' }
    }

    private fun isInBeam(pos: Pos): Boolean {
        Intcode(parsedInput).run {
            input.add(pos.x.toLong())
            input.add(pos.y.toLong())
            run()
            return output.removeAt(0) == 1L
        }
    }

    fun solvePart2(): Int {
        var x = 0
        // Beam is somewhat narrow, so the square have to be pretty far beyond 100. Just use an arbitrary
        // large value as upper value.
        for (y in 500 until 2000) {
            // Find first x that is in the beam (my beam is below the x=y line so x is always lower than y)
            // This is the lower left corner of the target square
            for (newX in x..y) {
                if (isInBeam(Pos(newX, y))) {
                    x = newX
                    break
                }
            }

            // If lower left corner and upper right corner is in the beam the whole area is in the beam
            if (isInBeam(Pos(x + 99, y - 99))) {
                // found a 100x100 square in the beam
                return 10_000 * x + y - 99
            }
        }
        return -1
    }
}