package aoc2022

class Day11(input: List<String>) {
    data class Monkey(
        val holding: MutableList<Long>,
        val test: Int,
        val ifTrue: Int,
        val ifFalse: Int,
        val op: (Long) -> Long
    ) {
        var numInspections = 0L
        fun inspectAndThrow(copingStrategy: (Long) -> Long): Pair<Long, Int> {
            numInspections++
            var toThrow = holding.removeAt(0)
            toThrow = op(toThrow)
            toThrow = copingStrategy(toThrow)
            return toThrow to if (toThrow % test == 0L) ifTrue else ifFalse
        }
    }

    private val monkeys = parseInput(input)


    fun parseInput(input: List<String>): List<Monkey> {
        return input.map { monkey ->
            val lines = monkey.split("\n").drop(1)
            val items = lines[0].substringAfter(": ").split(", ").map { it.toLong() }
            val test = lines[2].substringAfterLast(" ").toInt()
            val ifTrue = lines[3].substringAfterLast(" ").toInt()
            val ifFalse = lines[4].substringAfterLast(" ").toInt()

            val (operation, amount) = lines[1].substringAfter("new = old ").split(" ")
            val op = when {
                amount == "old" -> { a: Long -> a * a }
                operation == "*" -> { a: Long -> a * amount.toLong() }
                operation == "+" -> { a: Long -> a + amount.toLong() }
                else -> error("Unknown operation: ${lines[1]}")
            }

            Monkey(items.toMutableList(), test, ifTrue, ifFalse, op)
        }
    }

    private fun play(rounds: Int, copingStrategy: (Long) -> Long) {
        repeat(rounds) {
            monkeys.forEach { monkey ->
                while (monkey.holding.isNotEmpty()) {
                    val (item, toMonkey) = monkey.inspectAndThrow(copingStrategy)
                    monkeys[toMonkey].holding.add(item)
                }
            }
        }
    }

    private fun calculateMonkeyBusiness() = monkeys
        .map { it.numInspections }
        .sortedDescending()
        .take(2)
        .let { (a, b) -> a * b }

    fun solvePart1(): Long {
        play(20) { a -> a / 3 }
        return calculateMonkeyBusiness()
    }

    fun solvePart2(): Long {
        // The divisors used for the tests are all primes. Multiply them together to find the
        // least common denominator shared between all the monkeys
        val worryModulo = monkeys.map { it.test }.reduce(Int::times)
        play(10000) { a -> a % worryModulo }
        return calculateMonkeyBusiness()
    }
}