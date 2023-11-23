package aoc2021

import java.util.*

/**
 * Uses a linked list of Tokens as a representation of snailfish numbers. Operations such as
 * add, explode and split modifies the linked list given as (first) argument.
 */
class Day18(input: List<String>) {

    sealed interface Token {
        data object Open : Token
        data object Close : Token
        data object Separator : Token // Separators are not really needed, but they make testing and debugging easier
        data class Value(val value: Int) : Token

        companion object {
            fun tokenFor(char: Char) = when (char) {
                '[' -> Open
                ']' -> Close
                ',' -> Separator
                else -> Value(char - '0') // Allows for chars with value higher than 10 (used in a few tests)
            }

            fun stringify(token: Token): String {
                return when (token) {
                    Close -> "]"
                    Open -> "["
                    Separator -> ","
                    is Value -> "${'0' + token.value}"
                }
            }
        }
    }

    private val homework = input.map { parse(it) }

    fun parse(str: String) = LinkedList(str.map { Token.tokenFor(it) })

    // Used by tests to compare results against what's given in the problem description
    fun stringify(input: LinkedList<Token>) = input.joinToString("") { Token.stringify(it) }

    operator fun LinkedList<Token>.plus(other: LinkedList<Token>) = apply {
        add(0, Token.Open)
        add(Token.Separator)
        addAll(other)
        add(Token.Close)

        // Adding two numbers always triggers a reduction
        while (explode(this) || split(this)) Unit // Keep exploding and splitting until fully reduced
    }

    // Adds 'toAdd' to the first regular number in the given range
    private fun addToFirstRegularNumber(number: LinkedList<Token>, range: IntProgression, toAdd: Int) {
        for (j in range) {
            val curr = number[j]
            if (curr is Token.Value) {
                number[j] = Token.Value(curr.value + toAdd)
                return
            }
        }
    }

    // returns true if an explosion was triggered
    fun explode(number: LinkedList<Token>): Boolean {
        var nesting = 0
        for (i in number.indices) {
            when (number[i]) {
                Token.Open -> nesting++
                Token.Close -> nesting--
                else -> Unit
            }
            if (nesting == 5) {
                // Need to explode. At this nesting level there are only two real numbers and no pairs
                // Left value is added to first regular number to the left
                val left = (number[i + 1] as Token.Value).value
                addToFirstRegularNumber(number, i - 1 downTo 0, left)

                // Right value is added to first regular number to the right
                val right = (number[i + 3] as Token.Value).value
                addToFirstRegularNumber(number, i + 5 until number.size, right)

                repeat(5) { number.removeAt(i) } // Remove the current pair
                number.add(i, Token.Value(0))    // and insert 0 in its place
                return true
            }
        }
        return false
    }

    // returns true if the number was split
    fun split(number: LinkedList<Token>): Boolean {
        for (i in number.indices) {
            val token = number[i]
            if (token is Token.Value && token.value >= 10) {
                val l = token.value / 2
                val r = token.value - l
                number.removeAt(i)
                number.addAll(i, listOf(Token.Open, Token.Value(l), Token.Separator, Token.Value(r), Token.Close))
                return true
            }
        }
        return false
    }

    private fun LinkedList<Token>.calculateMagnitude(): Int {
        val stack = mutableListOf<MutableList<Int>>()
        for (i in indices) {
            when (val ch = get(i)) {
                Token.Open -> stack.add(mutableListOf())
                Token.Close -> {
                    val (left, right) = stack.removeLast()
                    val magnitude = 3 * left + 2 * right
                    if (stack.isEmpty()) {
                        return magnitude
                    }
                    stack.last().add(magnitude)
                }
                is Token.Value -> stack.last().add(ch.value)
                Token.Separator -> Unit
            }
        }
        return -1
    }

    fun doHomework() = homework.reduce { acc, next -> acc + next }

    fun solvePart1(): Int {
        return doHomework().calculateMagnitude()
    }

    fun solvePart2(): Int {
        return sequence {
            for (i in homework.indices) {
                for (j in homework.indices) {
                    if (i != j) {
                        // create a copy to avoid modifying the homework
                        val copy = LinkedList(homework[i].toMutableList())
                        yield((copy + homework[j]).calculateMagnitude())
                    }
                }
            }
        }.maxOf { it }
    }
}