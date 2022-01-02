package aoc2019

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

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

        private fun Map<Pair<Long, Long>, Long>.xRange() = keys.minByOrNull { it.first }!!.first..keys.maxByOrNull { it.first }!!.first
        private fun Map<Pair<Long, Long>, Long>.yRange() = keys.minByOrNull { it.second }!!.second..keys.maxByOrNull { it.second }!!.second

        @Suppress("unused")
        fun drawStuff(toDraw: Map<Pair<Long, Long>, Long>, frame: Int) {
            val yrange = toDraw.yRange()
            val xrange = toDraw.xRange()
            val img = BufferedImage(xrange.count(), yrange.count(), BufferedImage.TYPE_INT_RGB)
            (yrange).forEach { y ->
                (xrange).forEach { x ->
                    if (x >= 0) {
                        toDraw.getOrDefault(Pair(x, y), -1).let { color ->
                            val realColor = when (color) {
                                0L -> Color.BLACK.rgb
                                1L -> Color.BLUE.rgb
                                2L -> Color.GREEN.rgb
                                3L -> Color.RED.rgb
                                4L -> Color.WHITE.rgb
                                else -> Color.BLACK.rgb
                            }
                            img.setRGB(x.toInt(), y.toInt(), realColor)
                        }
                    }
                }
            }
            ImageIO.write(img, "png", File("img/frame${frame.toString().padStart(4, '0')}.png"))
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