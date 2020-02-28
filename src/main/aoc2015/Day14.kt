package aoc2015

import kotlin.math.min

class Day14(input: List<String>) {

    data class Reindeer(val Name: String, val speed: Int, val duration: Int, val rest: Int) {
        var score = 0
        var currentDistance = 0
            private set

        private var currentTime = 0

        private fun isMoving(): Boolean {
            val timeIntoCurrentCycle = currentTime % (duration + rest)
            // 0 means last second in the cycle while the reindeer is still resting
            return timeIntoCurrentCycle in 1..duration
        }

        fun tick() {
            currentTime++
            if (isMoving()) currentDistance += speed
        }

        fun distanceAfter(seconds: Int): Int {
            val fullCycles = seconds / (duration + rest)
            val remainingTime = seconds % (duration + rest)
            val lastFlyTime = min(duration, remainingTime)
            return fullCycles * duration * speed + lastFlyTime * speed
        }
    }

    val reindeers = parseInput(input)

    private fun parseInput(input: List<String>): List<Reindeer> {
        return input.map {
            val parts = it.split(" ")
            Reindeer(parts.first(), parts[3].toInt(), parts[6].toInt(), parts[13].toInt())
        }
    }

    fun solvePart1(seconds: Int = 2503): Int {
        return reindeers.map { it.distanceAfter(seconds) }.max()!!
    }

    private fun race(time: Int): Int {
        repeat(time) {
            reindeers.forEach { it.tick() }
            val leadingDistance = reindeers.maxBy { it.currentDistance }!!.currentDistance
            reindeers.filter { it.currentDistance == leadingDistance }
                    .forEach { it.score++ }
        }
        return reindeers.maxBy { it.score }!!.score
    }

    fun solvePart2(seconds: Int = 2503): Int {
        return race(seconds)
    }
}