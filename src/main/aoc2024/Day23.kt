package aoc2024

// Observation from looking at the data: all nodes have the same number of neighbours,
// in the example there are 4 neighbours, in the real input there are 13 neighbours.
class Day23(input: List<String>) {

    private fun MutableMap<String, MutableSet<String>>.addNode(key: String, value: String) {
        if (key !in this) {
            this[key] = mutableSetOf()
        }
        this[key]!!.add(value)
    }

    val nodes = buildMap {
        input.forEach { line ->
            val (a, b) = line.split("-")
            addNode(a, b)
            addNode(b, a)
        }
    }

    fun solvePart1(): Int {
        return buildSet {
            nodes.forEach { (node, neighbours) ->
                neighbours.forEach { neighbour ->
                    (neighbours - neighbour).forEach { otherNeighbour ->
                        if (otherNeighbour in nodes[neighbour]!!) {
                            add(setOf(node, neighbour, otherNeighbour))
                        }
                    }
                }
            }
        }.count { groupOfThree -> groupOfThree.any { node -> node.startsWith("t") } }
    }


    /**
     * All nodes have 4 neighbours in the test input and 13 nodes in the real input. Both test input and real input
     * have the largest group of connected nodes with the same size as number of neighbours. The group consists of
     * the node itself and all except one of its neighbours. The left-over neighbour connects the node to some other
     * node to ensure that all nodes are connected.
     *
     * This was just an assumption, nothing in the problem text states that this must be true. But it was quick to
     * implement and the assumption turned out to be true.
     *
     * This means that we can find the largest group by looking for the nodes where all neighbours except one have
     * the same neighbours as the current node.
     */
    private fun findLargestGroup(): String {
        return nodes.mapNotNull { (currentNode, neighbours) ->
            val desiredSize = neighbours.size - 1
            currentNode.takeIf {
                desiredSize == neighbours.count { nextNode ->
                    val numSharedNeighbours =
                        nodes[nextNode]!!.count { other -> other in neighbours || other == currentNode }
                    numSharedNeighbours == desiredSize
                }
            }
        }.sorted().joinToString(",")
    }

    fun solvePart2(): String {
        return findLargestGroup()
    }
}