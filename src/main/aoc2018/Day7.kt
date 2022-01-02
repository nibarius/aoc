package aoc2018

import java.util.*

class Day7(input: List<String>) {
    // Letter is key, list of requirements is value
    private val steps = parseInput(input)

    private fun parseInput(input: List<String>): Map<String, MutableList<String>> {
        val ret = mutableMapOf<String, MutableList<String>>()
        // To parse: Step C must be finished before step A can begin.
        input.forEach { instruction ->
            val req = instruction.substringAfter("Step ").substringBefore(" ")
            val step = instruction.substringBefore(" can begin.").substringAfterLast(" ")
            ret.putIfAbsent(req, mutableListOf())
            ret.putIfAbsent(step, mutableListOf())
            ret[step]!!.add(req)
        }
        return ret
    }

    private fun getOrder(steps: Map<String, MutableList<String>>): String {
        val remaining = steps.toMutableMap()
        val order = Stack<String>()
        while (remaining.isNotEmpty()) {
            val available = remaining.filterValues { it.isEmpty() }.keys.toList().sorted()
            val next = available.first()
            order.push(next)
            remaining.remove(next)
            remaining.values.forEach { it.remove(next) }
        }
        return order.joinToString("")
    }

    private data class Worker(val id: Int, var workingOn: String?, var done: Int?, val delay: Int) {
        val durations = ('A'..'Z').associate { Pair("$it", delay + 1 + (it - 'A')) }
        fun isWorking() = workingOn != null
        fun isIdle() = workingOn == null
        fun startWork(which: String, currentTime: Int): Int {
            workingOn = which
            done = currentTime + durations.getValue(which)
            return done!!
        }

        fun stopWorking() {
            workingOn = null
            done = null
        }
    }

    private fun getAssemblyDuration(steps: Map<String, MutableList<String>>, numWorkers: Int, delay: Int): Int {
        val remaining = steps.toMutableMap()
        val order = Stack<String>()

        val workers = Array(numWorkers) { Worker(it, null, null, delay) }
        var currentTime = 0
        val workLog = mutableListOf<Pair<String, Int>>() // part, time when done
        while (remaining.isNotEmpty() || workers.any { it.isWorking() }) {
            val available = remaining.filterValues { it.isEmpty() }.keys.toList().sorted()
            if (available.isNotEmpty() && workers.any { it.isIdle() }) {
                // start working
                val next = available.first()
                val done = workers.first { it.isIdle() }.startWork(next, currentTime)
                workLog.add(Pair(next, done))
                remaining.remove(next)
            }


            val finishedWork = workLog.filter { it.second == currentTime }
            finishedWork.forEach { finished ->
                // Some work finishes this second
                workers.find { it.workingOn == finished.first }!!.stopWorking()
                order.push(finished.first)
                remaining.values.forEach { it.remove(finished.first) }
                workLog.remove(finished)
            }

            if ((available.isEmpty() || workers.all { it.isWorking() }) && finishedWork.isEmpty()) {
                // No work to start or finish this second, advance time
                currentTime = workLog.minByOrNull { it.second }!!.second
            }
        }
        return currentTime
    }

    fun solvePart1(): String {
        return getOrder(steps)
    }

    fun solvePart2(numWorkers: Int, delay: Int): Int {
        return getAssemblyDuration(steps, numWorkers, delay)
    }
}