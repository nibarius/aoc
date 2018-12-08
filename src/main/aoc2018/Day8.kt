package aoc2018

import org.magicwerk.brownies.collections.GapList

class Day8(input: String) {

    private data class Node(val children: List<Node>, val metadata: List<Int>) {
        fun valueOf(): Int {
            return if (children.isEmpty()) {
                metadata.sum()
            } else {
                var ret = 0
                metadata.forEach {
                    if (children.size >= it && it > 0) {
                        ret += children[it - 1].valueOf()
                    }
                }
                ret
            }
        }
    }

    private val allNodes = mutableListOf<Node>()
    private val tree = parseInput(input)

    private fun readNode(input: GapList<String>): Node {
        val numChildren = input.removeFirst().toInt()
        val numMetadata = input.removeFirst().toInt()
        val children = mutableListOf<Node>()
        val metadata = mutableListOf<Int>()
        for (child in 0 until numChildren) {
            children.add(readNode(input))
        }
        for (meta in 0 until numMetadata) {
            metadata.add(input.removeFirst().toInt())
        }
        val node = Node(children, metadata)
        allNodes.add(node)
        return node
    }

    private fun parseInput(input: String): Node {
        val list = GapList<String>()
        list.addAll(input.split(" "))
        return readNode(list)
    }



    fun solvePart1(): Int {
        return allNodes.sumBy { it.metadata.sum() }
    }

    fun solvePart2(): Int {
        return tree.valueOf()
    }
}