package aoc2020

class Day19(input: List<String>) {

    // options is a list of options available
    data class Rule(val options: List<List<String>>)

    private val messages: List<String> = input.last().split("\n")
    private val rules: Map<String, Rule>

    // Example input:
    // 0: 4 1 5
    // 1: 2 3 | 3 2
    // 4: "a"
    init {
        rules = input.first().split("\n").associate { line ->
            val id = line.substringBefore(":")
            val options = line.substringAfter(": ")
                .split(" | ")
                .map { it.replace("\"", "").split(" ") }
            id to Rule(options)
        }
    }

    private fun String.isLetter() = this in listOf("a", "b")

    /**
     * Recursively check if the msg is valid under the given rule number when starting from any
     * of the given start indexes. Since a rule can have more than one option there may be more than
     * one way to fulfil the rule.
     * @return an empty list if the rule is not valid or a list of indexes if it is valid. The indexes
     * specify the index of first characters after those that has been consumed by fulfilling the rule.
     */
    private fun check(msg: String, startIndexes: List<Int>, ruleNumber: String, rules: Map<String, Rule>): List<Int> {
        val ruleToUse = rules[ruleNumber]!!
        val ret = mutableListOf<Int>()

        // Check the rule for all the given start indexes
        for (startIndex in startIndexes) {
            when { // cases that ends recursion
                startIndex !in msg.indices -> {
                    // Can't fulfil this rule, we're past the end of msg already
                    continue
                }
                ruleToUse.options.first().first().isLetter() -> { // This rule is a leaf node
                    if (msg[startIndex].toString() == ruleToUse.options.first().first()) {
                        ret.add(startIndex + 1) // and msg is valid under this rule
                    }
                    continue
                }
            }

            // The current rule has one or more options that refers to other rules. All options needs
            // to be checked since we don't know which option is right. Both might look good so far
            // but they could consume different amounts of the message which becomes important when
            // checking other rules later on.
            // Example: rule 1: a | aa ==> Should rule 1 consume one or two a:s?
            optionCheck@ for (option in ruleToUse.options) {
                var indexes = listOf(startIndex)

                // For the current option, check if all it's requirements / sub rules are fulfilled
                // Example: "option" could be [42, 42, 42], meaning it must be possible to apply rule 42
                // three times in a row.
                for (i in option.indices) {
                    val nextRuleNumber = option[i]
                    // Recursively (depth first) check if the character at the current position is correct.
                    val indexesThatCouldWork = check(msg, indexes, nextRuleNumber, rules)
                    if (indexesThatCouldWork.isEmpty()) {
                        // The current sub rule is not valid at all, no point in checking any further sub rules
                        // continue with the next option instead.
                        continue@optionCheck
                    }
                    // This option looks promising so far, continue checking for all indexes that are valid
                    indexes = indexesThatCouldWork
                }
                // We have successfully checked all sub rules, meaning this option is valid.
                ret.addAll(indexes)
            }
        }
        return ret
    }

    private fun checkAllRules(msg: String, rules: Map<String, Rule>): Boolean {
        val result = check(msg, listOf(0), "0", rules)
        return result.isNotEmpty() && result.any { it == msg.length }
    }

    fun solvePart1(): Int {
        return messages.count { checkAllRules(it, rules) }
    }

    private fun changeRules(): Map<String, Rule> {
        return rules.toMutableMap().apply {
            this["8"] = Rule(listOf(listOf("42"), listOf("42", "8")))
            this["11"] = Rule(listOf(listOf("42", "31"), listOf("42", "11", "31")))
        }
    }

    fun solvePart2(): Int {
        return messages.filter { checkAllRules(it, changeRules()) }.count()
    }
}
