package aoc2019

import java.util.*
import kotlin.math.pow

class Day25(input: List<String>) {

    @Suppress("unused")
    private class Droid(val computer: Intcode, val verboseMode: Boolean = false) {
        private fun String.toAscii(): List<Long> {
            return map { it.toLong() }
                    .toMutableList()
                    .apply { add(10L) }
        }

        private fun List<Long>.fromAscii(): String {
            return map { it.toChar() }.joinToString("")
        }

        private fun String.opposite(): String {
            return when (this) {
                "north" -> "south"
                "south" -> "north"
                "east" -> "west"
                "west" -> "east"
                else -> ""
            }
        }

        private val items = mutableListOf<String>()

        fun move(dir: String) = input(dir)
        fun move(steps: List<String>) = steps.forEach { move(it) }
        fun take(item: String) = input("take $item")
        fun drop(item: String) = input("drop $item")
        fun inv() = input("inv")
        fun run() = input(null)

        fun input(input: String?): Location {
            if (input != null) {
                if (verboseMode) println(input)
                computer.input.addAll(input.toAscii())
            }
            computer.run()
            return parseDescription(computer.output.fromAscii())
                    .also { if (verboseMode) println(computer.output.fromAscii()) }
                    .also { computer.output.clear() }
        }

        private fun parseDescription(rawDescription: String): Location {
            val lines = rawDescription.split("\n")
            val location = lines[3].substringAfter("== ").substringBefore(" ==")
            val doors = mutableListOf<String>()
            val items = mutableListOf<String>()
            var lookingForDoors = true
            for (i in 3 until lines.size) {
                if (lines[i].startsWith("Items here")) {
                    lookingForDoors = false
                } else if (lines[i].startsWith("- ")) {
                    val list = if (lookingForDoors) doors else items
                    list.add(lines[i].substringAfter("- "))
                }
            }
            return Location(location, doors).apply {
                this.items.addAll(items)
                if (location == "Pressure-Sensitive Floor" && lines[11].contains("Oh, hello")) {
                    this.airlockCode = lines[11].substringAfter("by typing ").substringBefore(" on the")
                }
            }
        }

        // explores the whole ship and picks up all items that can be picked up
        // returns the shortest way back to the security checkpoint from the current position
        fun exploreShip(): List<String> {
            val directionsToCheck = mutableMapOf<Location, MutableList<String>>()
            var securityFound = false
            val wayBackToSecurity = mutableListOf<String>()
            var loc = run()
            var next = ""
            while (true) {
                //Take all items in the current position
                loc.items.filterNot { itemBlacklist.contains(it) }.forEach {
                    take(it)
                    items.add(it)
                }

                // Track the way back to security as the opposite of all moves made from security
                if (securityFound) {
                    wayBackToSecurity.add(0, next.opposite())
                }

                // don't pass trough here while exploring, just turn back
                if (loc.name == "Security Checkpoint") {
                    val comingFrom = next.opposite()
                    directionsToCheck[loc] = mutableListOf(comingFrom)

                    // Start tracking the way to walk to get back to the security checkpoint again
                    securityFound = true
                    wayBackToSecurity.add(loc.doors.filterNot { it == comingFrom }.first())
                }
                if (!directionsToCheck.containsKey(loc)) {
                    val comingFrom = next.opposite()
                    // Add the direction we came from last in the list.
                    // That is, check all other options before going back
                    val toCheck = loc.doors
                            .filterNot { it == comingFrom }
                            .toMutableList()
                            .apply { if (comingFrom.isNotBlank()) add(comingFrom) }
                    directionsToCheck[loc] = toCheck.toMutableList()
                }
                if (directionsToCheck[loc].isNullOrEmpty()) {
                    // There is nothing more to check
                    break
                }
                next = directionsToCheck[loc]!!.removeAt(0)
                loc = move(next)

            }
            return optimizeMoves(wayBackToSecurity)
        }

        // Bypass the security checkpoint by just trying all combinations of items
        // until the right one is found. There are only 8 items in total so the worst
        // case scenario is maximum 2^8 checks which is low enough to handle.
        fun bypassSecurityAndGetAirlockCode(dir: String): Int {
            // Start out with holding no items and try to carry more and more items
            items.forEach { drop(it) }
            var currentState = BitSet(items.size)
            for (i in 0 until 2.0.pow(items.size).toInt()) {

                // Use Gray code to just have to drop/take one item each time
                // https://en.wikipedia.org/wiki/Gray_code
                val gray = i xor (i shr 1)
                val desired = BitSet.valueOf(longArrayOf(gray.toLong()))

                // Check which items must be taken/dropped to get to the desired state.
                // Each set bit in diff denotes an item that must either be dropped or picked up
                val diff = currentState.clone() as BitSet
                diff.xor(desired)

                // Take/drop items as needed to get into the desired state
                var index = diff.nextSetBit(0)
                while (index >= 0) {
                    when (currentState.get(index)) {
                        true -> drop(items[index])
                        false -> take(items[index])
                    }
                    index = diff.nextSetBit(index + 1)
                }
                currentState = desired

                // Try to move past security
                val result = move(dir)

                result.airlockCode?.let { return it.toInt() }
            }
            throw RuntimeException("Failed to get trough the security checkpoint")
        }

        // Items that makes it impossible to win the game if picked up
        private val itemBlacklist = setOf(
                "giant electromagnet", // The giant electromagnet is stuck to you.  You can't move!!
                "molten lava",         // The molten lava is way too hot! You melt!
                "photons",             // It is suddenly completely dark! You are eaten by a Grue!
                "infinite loop",       // The program goes into an infinite loop and never returns
                "escape pod"           // You're launched into space! Bye!
        )

        data class Location(val name: String, val doors: List<String>) {
            val items = mutableListOf<String>()
            // Contains the code to the airlock if found, otherwise null
            var airlockCode: String? = null
        }

        // Take a list of directions and remove all detours
        // Example: north, north, south, south, west ==> west
        private fun optimizeMoves(moves: List<String>): List<String> {
            val stack = Stack<String>()
            moves.forEach { move ->
                if (stack.isNotEmpty() && stack.peek() == move.opposite()) {
                    stack.pop()
                } else {
                    stack.push(move)
                }
            }
            return stack
        }
    }

    private val computer = Intcode(input.map { it.toLong() })

    fun solvePart1(): Int {
        with(Droid(computer, false)) {
            val wayToSecurity = exploreShip()
            move(wayToSecurity.dropLast(1))
            val securityExitDirection = wayToSecurity.last()
            return bypassSecurityAndGetAirlockCode(securityExitDirection)
        }
    }
}