package aoc2019

class Day13(input: List<String>) {
    var parsedInput = input.map { it.toLong() }

    class Arcade(program: List<Long>) {
        private companion object {
            const val BLOCK = 2L
            const val PADDLE = 3L
            const val BALL = 4L
        }

        private val computer = Intcode(program)
        private val screen = mutableMapOf<Pair<Long, Long>, Long>()
        private var score = 0L
        private var ballX = 0L
        private var paddleX = 0L

        fun countBlocks() = screen.values.count { it == BLOCK }

        fun play(): Long {
            do {
                computer.run()
                updateScreen()
                computer.input.add((ballX - paddleX).coerceIn(-1, 1))
            } while (computer.computerState != Intcode.ComputerState.Terminated)
            return score
        }

        private fun updateScreen() {
            computer.output.chunked(3).forEach { (x, y, type) ->
                when {
                    x == -1L -> score = type
                    type == BALL -> ballX = x
                    type == PADDLE -> paddleX = x
                }
                screen[Pair(x, y)] = type
            }
            computer.output.clear()
        }
    }

    fun solvePart1(): Int {
        return Arcade(parsedInput).run {
            play()
            countBlocks()
        }
    }

    fun solvePart2(): Long {
        return Arcade(parsedInput.toMutableList().apply { this[0] = 2L }).play()
    }
}