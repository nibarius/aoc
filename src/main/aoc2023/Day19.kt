package aoc2023

import size
import kotlin.math.max
import kotlin.math.min

class Day19(input: List<String>) {

    private fun String.toIntRange(): IntRange {
        val i = this.toInt()
        return i..i
    }

    private val partRatings = input.last().split("\n").map { line ->
        val regex = """\{x=(\d+),m=(\d+),a=(\d+),s=(\d+)}""".toRegex()
        val (x, m, a, s) = regex.matchEntire(line)!!.destructured
        "in" to mapOf('x' to x.toIntRange(), 'm' to m.toIntRange(), 'a' to a.toIntRange(), 's' to s.toIntRange())
    }

    private val workflows = input.first().split("\n").map { line ->
        val name = line.substringBefore("{")
        val rules = line.substringAfter("{").dropLast(1).split(",")
        name to rules
    }.toMap()

    /**
     * Lookup all the possible different scenarios for the given workflow. The given part will be split up into
     * several new parts, one for each matching rule with smaller ranges for each sub part.
     * @param workflow The workflow to work with
     * @param part The possible category ratings for the part
     * @returns A list of workflow to possible category ratings.
     */
    private fun lookupWorkflow(
        workflow: String,
        part: Map<Char, IntRange>
    ): MutableList<Pair<String, Map<Char, IntRange>>> {
        val ret = mutableListOf<Pair<String, Map<Char, IntRange>>>()
        val currentPart = part.toMutableMap()
        for (rule in workflows[workflow]!!) {
            if (!rule.contains(":")) {
                // Fallback rule, all remaining ranges leads to this rule.
                ret.add(rule to currentPart)
                break
            }
            val regex = """([amsx])([<>])([0-9]+):([RAa-z]+)""".toRegex()
            val (category, op, amount, nextWorkflow) = regex.matchEntire(rule)!!.destructured
            val currentRange = currentPart[category.first()]!!

            // Split the current range into two ranges, one that fits the current rule and one for all that remains
            val (fitsRuleRange, remainingRange) = when (op) {
                "<" -> {
                    currentRange.first..min(currentRange.last, amount.toInt() - 1) to
                            max(amount.toInt(), currentRange.first)..currentRange.last
                }

                ">" -> {
                    max(currentRange.first, amount.toInt() + 1)..currentRange.last to
                            currentRange.first..min(amount.toInt(), currentRange.last)
                }

                else -> error("unknown op")
            }
            if (fitsRuleRange.size() > 0) {
                ret.add(nextWorkflow to currentPart.toMutableMap().apply { this[category.first()] = fitsRuleRange })
            }
            if (remainingRange.size() > 0) {
                currentPart[category.first()] = remainingRange
            } else {
                // There is nothing left for any other rules, so no need to continue
                break
            }
        }
        return ret
    }

    /**
     * Find all parts that are accepted given the given initial list of parts.
     * @param toCheck A list of a workflow to parts pairs that makes up the parts to check.
     */
    private fun findAcceptedParts(toCheck: MutableList<Pair<String, Map<Char, IntRange>>>): MutableSet<Map<Char, IntRange>> {
        val accepted = mutableSetOf<Map<Char, IntRange>>()
        while (toCheck.isNotEmpty()) {
            val (workflow, currentPart) = toCheck.removeFirst()
            if (workflow == "A") {
                accepted.add(currentPart)
                continue
            } else if (workflow == "R") {
                continue
            }
            val next = lookupWorkflow(workflow, currentPart)
            toCheck.addAll(next)
        }
        return accepted
    }

    fun solvePart1(): Long {
        val accepted = findAcceptedParts(partRatings.toMutableList())
        return accepted.sumOf { it.values.sumOf { it.first }.toLong() }
    }

    fun solvePart2(): Long {
        val accepted = findAcceptedParts(mutableListOf("in" to listOf('x', 'm', 'a', 's').associateWith { 1..4000 }))
        return accepted.sumOf { it.values.map { range -> range.size().toLong() }.reduce(Long::times) }
    }
}