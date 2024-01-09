package aoc2023

import MyMath

class Day20(input: List<String>) {
    private val connections = input.associate { line ->
        val name = line.substringBefore(" ").substringAfter("%").substringAfter("&")
        val outputs = line.substringAfter("-> ").split(", ")
        name to outputs
    }
    private val modules = input.map { line ->
        val name = line.substringBefore(" ->").drop(1)
        when (line.first()) {
            'b' -> Broadcast()
            '%' -> FlipFlop(name, false)
            '&' -> {
                val memory = connections.filterValues { it.contains(name) }.keys.associateWith { false }
                Conjunction(name, memory.toMutableMap())
            }

            else -> error("Invalid module")

        }
    }.associateBy { it.name }

    sealed class Module {
        abstract val name: String
    }

    data class Broadcast(override val name: String = "broadcaster") : Module()
    data class FlipFlop(override val name: String, var on: Boolean) : Module()
    data class Conjunction(override val name: String, var memory: MutableMap<String, Boolean>) : Module()


    private fun pushButton(onVisitLow: (String) -> Unit = {}): MutableMap<Boolean, Long> {
        val pulses = mutableListOf(Triple("button", "broadcaster", false))
        val pulseCount = mutableMapOf(false to 1L, true to 0L)
        while (pulses.isNotEmpty()) {
            val (sender, receiver, pulse) = pulses.removeFirst()
            if (!pulse) {
                onVisitLow(receiver)
            }
            if (receiver !in modules) {
                // this is sent to an output that does not do anything with the data
                continue
            }
            when (val currentModule = modules[receiver]!!) {
                is Broadcast -> connections[receiver]!!
                    .forEach {
                        pulses.add(Triple(receiver, it, pulse))
                        pulseCount[pulse] = pulseCount[pulse]!! + 1
                    }

                is Conjunction -> {
                    // update memory, I need to know who is sending this to me
                    currentModule.memory[sender] = pulse
                    // send
                    connections[receiver]!!
                        .forEach { dest ->
                            val sending = !currentModule.memory.values.all { it }
                            pulses.add(Triple(receiver, dest, sending))
                            pulseCount[sending] = pulseCount[sending]!! + 1
                        }
                }

                is FlipFlop -> {
                    if (!pulse) {
                        currentModule.on = !currentModule.on
                        connections[receiver]!!
                            .forEach { dest ->
                                pulses.add(Triple(receiver, dest, currentModule.on))
                                pulseCount[currentModule.on] = pulseCount[currentModule.on]!! + 1
                            }
                    }
                }
            }
        }
        return pulseCount
    }


    fun solvePart1(): Long {
        val pushCount = mutableMapOf(false to 0L, true to 0L)
        repeat(1000) {
            val addedCount = pushButton()
            addedCount.forEach { (k, v) ->
                pushCount[k] = pushCount[k]!! + v
            }
        }
        return pushCount.values.reduce(Long::times)
    }

    // there are four separate cycles of different length. rx will only get a low
    // signal when all four cycles match. Do LCM of each cycle length to find the
    // correct answer.
    // That there are four different cycles was realized by drawing the whole thing
    // on a paper and seeing what it looked like.
    fun solvePart2(): Long {
        var pushCount = 1
        val cycles = mutableListOf<Int>()
        while (cycles.size < 4) {
            pushButton {
                // These are the four nodes need to receive a low pulse at the same time for rx to get alow pulse
                if (it in setOf("kl", "vb", "vm", "kv")) {
                    cycles.add(pushCount)
                }
            }
            pushCount++
        }
        return MyMath.lcm(cycles)
    }
}