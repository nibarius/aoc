package aoc2020

@Suppress("MapGetWithNotNullAssertionOperator")
class Day7(input: List<String>) {

    private data class Node(val parents: MutableSet<String> = mutableSetOf(), val children: MutableMap<String, Int> = mutableMapOf())

    private val myBag = "shiny gold"
    private val bags = parseInput(input)

    private fun MutableMap<String, Node>.addChildToParent(child: String, parent: String) {
        if (!contains(child)) this[child] = Node()
        this[child]!!.parents.add(parent)
    }

    // Example input:
    // light red bags contain 1 bright white bag, 2 muted yellow bags.
    // faded blue bags contain no other bags.
    private fun parseInput(input: List<String>): Map<String, Node> {
        val ret = mutableMapOf<String, Node>()
        for (line in input) {
            val (name, contents) = line.split(" bags contain ")

            // Create entry for this node if needed
            if (!ret.contains(name)) ret[name] = Node()
            if (contents == "no other bags.") continue

            // Parse and set all children for this node
            // each entry becomes a bag name to quantity pair
            contents.split(", ").associate { bag -> // each entry becomes a bag name to quantity pair
                "([0-9]+) (.*) bag.{0,2}".toRegex().matchEntire(bag)!!
                    .destructured
                    .let { (qty, name) -> Pair(name, qty.toInt()) }
            }
                // add all children into ret
                    .apply { ret[name]!!.children.putAll(this) }
                    // mark this node as parent for all its children
                    .also { it.keys.forEach { child -> ret.addChildToParent(child, name) } }

        }
        return ret
    }

    private fun MutableSet<String>.addAllAncestorsOf(name: String) {
        // the bag itself + all its parents
        add(name)
        bags[name]!!.parents
                .forEach { parent -> addAllAncestorsOf(parent) }
    }

    private fun countBagsContainedBy(name: String): Int {
        // the bag itself + all its children
        return 1 + bags[name]!!.children
                .toList()
                .sumOf { it.second * countBagsContainedBy(it.first) }

    }

    fun solvePart1(): Int {
        return mutableSetOf<String>()
                .apply { addAllAncestorsOf(myBag) }
                .size - 1 // subtract one since start bag should not be counted
    }

    fun solvePart2(): Int {
        return countBagsContainedBy(myBag) - 1 // subtract one since start bag should not be counted
    }
}