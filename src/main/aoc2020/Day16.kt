package aoc2020

@OptIn(ExperimentalStdlibApi::class)
class Day16(input: List<String>) {

    private data class Rule(val name: String, val r1: IntRange, val r2: IntRange) {
        fun validate(i: Int) = i in r1 || i in r2
    }

    private data class Ticket(val fields: List<Int>, val invalidFields: List<Int>) {
        fun isValid() = invalidFields.isEmpty()
        fun scanningErrorRate() = invalidFields.sum()
        fun isColumnValidUnderRule(column: Int, rule: Rule) = rule.validate(fields[column])

        companion object {
            fun parse(fields: List<Int>, rules: List<Rule>): Ticket {
                val valid = mutableListOf<Int>()
                val invalid = mutableListOf<Int>()
                fields.forEach { if (rules.any { rule -> rule.validate(it) }) valid.add(it) else invalid.add(it) }
                return Ticket(valid, invalid)
            }
        }
    }

    private val rules: List<Rule>
    private val myTicket: List<Int>
    private val nearbyTickets: List<Ticket>

    private fun String.toIntRange() = split("-").let { it.first().toInt()..it.last().toInt() }
    private fun String.toIntList() = split(",").map { it.toInt() }

    init {
        val lineIterator = input.iterator()
        rules = buildList {
            for (line in lineIterator) {
                if (line.isEmpty()) {
                    break
                }
                "([a-z ]+): ([0-9-]+) or ([0-9-]+)".toRegex()
                        .matchEntire(line)!!
                        .destructured
                        .let { (rule, r1, r2) -> add(Rule(rule, r1.toIntRange(), r2.toIntRange())) }
            }
        }
        lineIterator.next() // your ticket:
        myTicket = lineIterator.next().toIntList()
        lineIterator.next()
        lineIterator.next() // nearby tickets:
        nearbyTickets = buildList {
            for (line in lineIterator) {
                add(Ticket.parse(line.toIntList(), rules))
            }
        }
    }

    private fun findRulesToColumnMapping(tickets: List<Ticket>): MutableMap<Rule, Int> {
        // Map with rule to set of columns that it could apply to mapping
        val possible = rules.associateWith { mutableSetOf<Int>() }.toMutableMap()
        rules.forEach { rule ->
            for (column in myTicket.indices) {
                if (tickets.all { it.isColumnValidUnderRule(column, rule) }) {
                    possible[rule]!!.add(column)
                }
            }
        }

        val fixed = mutableMapOf<Rule, Int>()
        while (possible.isNotEmpty()) {
            // Pick the rule that could only apply to one column and fix it
            val (knownRule, knownColumn) = possible.minByOrNull { it.value.size }!!
            fixed[knownRule] = knownColumn.single()
            possible.remove(knownRule)

            // then remove it's column from all other rules
            possible.forEach { it.value.remove(knownColumn.single()) }
        }
        return fixed
    }

    fun solvePart1(): Int {
        return nearbyTickets.sumBy { it.scanningErrorRate() }
    }

    fun solvePart2(): Long {
        return findRulesToColumnMapping(nearbyTickets.filter { it.isValid() })
                .filterKeys { it.name.contains("departure") }
                .values
                .map { myTicket[it] }
                .fold(1L) { acc, value -> acc * value }
    }
}