package aoc2022

import java.util.*

class Day13(input: List<String>) {

    sealed class Entry {
        data class Value(val value: Int) : Entry()
        data class AList(val list: MutableList<Entry> = mutableListOf()) : Entry()
    }

    private val packetPairs = input
        .map { rawPairs ->
            rawPairs.split("\n")
                .let { parse(it.first()) to parse(it.last()) }
        }

    private val allPackets = input.let { it + "[[2]]\n[[6]]" }
        .flatMap { group -> group.split("\n").map { line -> parse(line) } }

    private fun parse(raw: String): Entry {
        val stack = Stack<Entry.AList>()
        val root = Entry.AList()
        var curr: Entry.AList = root
        var number = ""

        fun finishNumberIfNeeded() {
            if (number.isNotEmpty()) {
                curr.list.add(Entry.Value(number.toInt()))
                number = ""
            }
        }

        // Drop the outermost [ and ] since the root entry is an Entry from the start
        for (token in raw.drop(1).dropLast(1)) {
            when (token) {
                '[' -> {
                    // Start of a new list, save the current item on the stack and continue with the child
                    val child = Entry.AList()
                    curr.list.add(child)
                    stack.push(curr)
                    curr = child
                }

                ']' -> {
                    // List ends, continue processing the parent
                    finishNumberIfNeeded()
                    curr = stack.pop()
                }

                ',' -> finishNumberIfNeeded()
                else -> number += token
            }
        }
        finishNumberIfNeeded()
        return root
    }

    private fun compareValues(left: Int, right: Int): Boolean? {
        return when {
            left < right -> true
            left > right -> false
            else -> null
        }
    }

    private fun isInOrder(left: Entry, right: Entry): Boolean? {
        return when {
            left is Entry.Value && right is Entry.Value -> compareValues(left.value, right.value)
            left is Entry.Value -> isInOrder(Entry.AList(mutableListOf(left)), right)
            right is Entry.Value -> isInOrder(left, Entry.AList(mutableListOf(right)))
            left is Entry.AList && right is Entry.AList -> {
                for ((l, r) in left.list zip right.list) {
                    val res = isInOrder(l, r)
                    if (res != null) {
                        return res
                    }
                }
                when {
                    left.list.size < right.list.size -> true
                    left.list.size > right.list.size -> false
                    else -> null
                }
            }

            else -> error("not reachable")
        }
    }

    fun solvePart1(): Int {
        return packetPairs.map { isInOrder(it.first, it.second) }
            .mapIndexedNotNull { index, b -> if (b!!) index + 1 else null }
            .sum()
    }

    fun solvePart2(): Int {
        val ordered = allPackets.sortedWith { a, b ->
            when (isInOrder(a, b)) {
                true -> -1
                false -> 1
                else -> 0
            }
        }
        val two = parse("[[2]]")
        val six = parse("[[6]]")
        val filtered = ordered.withIndex().mapNotNull { (index, value) ->
            if (value == two || value == six) index + 1 else null
        }
        return filtered.reduce(Int::times)
    }
}