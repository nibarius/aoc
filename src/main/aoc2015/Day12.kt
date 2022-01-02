package aoc2015

import java.util.*

class Day12(val input: String) {

    fun solvePart1(): Int {
        return input.split(',', ':', '[', ']', '{', '}').mapNotNull { it.toIntOrNull() }.sum()
    }

    private data class Node(var sum: Int = 0, var red: Boolean = false, val children: MutableList<Node> = mutableListOf())

    private fun ignoreRed(): Int {
        // Split the string with same delimiters in part 1, except colon and keep the delimiters:
        // https://stackoverflow.com/questions/37131283/how-to-split-a-string-and-plant-the-delimiters-between-the-splitted-parts-in-kot
        val reg = Regex("(?<=[,{}\\[\\]])|(?=[,{}\\[\\]])")
        val list = input.split(reg).filterNot { listOf("", ",", "[", "]").contains(it) }

        // Create a tree where each new object is a child to the current node
        val root = Node()
        var curr = root
        val stack = Stack<Node>()

        for (token in list) {
            when {
                token == "{" -> {
                    // Start of a new object create a child node and continue processing it
                    val child = Node()
                    curr.children.add(child)

                    // push the current node to the stack and continue with this when the
                    // new object has been processed.
                    stack.push(curr)
                    curr = child
                }
                token == "}" -> {
                    // Object ends, continue processing the parent
                    curr = stack.pop()
                }
                token.contains(":\"red\"") -> {
                    // :"red" are red in objects that should cause ignores. Mark this node as red
                    // ("red" without leading colon is in arrays and does not affect the sum)
                    curr.red = true
                }
                else -> {
                    // Either an object (has : followed by the value), or an array where
                    // : does not exist and substringAfter will return the original string.
                    // If it's an integer add it to the sum, for the rest (colors) add 0
                    curr.sum += token.substringAfter(":").toIntOrNull() ?: 0
                }
            }
        }

        return sum(root)
    }

    // Sum the value of all non-red nodes in the tree
    private fun sum(node: Node): Int {
        return if (node.red) {
            0
        } else {
            node.sum + node.children.sumOf { sum(it) }
        }
    }

    fun solvePart2(): Int {
        return ignoreRed()
    }
}