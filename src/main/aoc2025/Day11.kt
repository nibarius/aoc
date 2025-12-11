package aoc2025

class Day11(input: List<String>) {
    val neighbours = input.associate { line ->
        line.take(3) to line.drop(5).split(" ")
    }

    private fun waysToOut(from: String, to: String, memory: MutableMap<String, Long>): Long {
        return when (from) {
            in memory -> memory.getValue(from)
            to -> 1
            else -> neighbours.getOrDefault(from, listOf())
                .sumOf {
                    waysToOut(it, to, memory)
                }
        }.also { memory[from] = it }
    }

    fun solvePart1(): Long {
        return waysToOut("you", "out", mutableMapOf())
    }

    fun solvePart2(): Long {
        //makeGraphvizGraph()
        // There is no going back and no cycles in the graph, fft comes before dac
        val x = waysToOut("svr", "fft", mutableMapOf())
        val y = waysToOut("fft", "dac", mutableMapOf())
        val z = waysToOut("dac", "out", mutableMapOf())
        return x * y * z
    }

    @Suppress("unused")
    private fun makeGraphvizGraph() {
        val output = buildList {
            neighbours.forEach { (key, value) ->
                value.forEach {
                    add("  $key -> $it;")
                }
            }
        }.joinToString("\n")
        println(
            """digraph G {
$output
  you[fillcolor=yellow,style=filled]
  fft[fillcolor=green,style=filled]
  dac[fillcolor=green,style=filled]
  svr[fillcolor=yellow,style=filled]
  out[fillcolor=red,style=filled,shape=Msquare]
}
"""
        )
    }
}