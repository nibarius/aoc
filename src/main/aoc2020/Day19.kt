package aoc2020

class Day19(input: List<String>) {

    // For rules where there is no second option, option2 is an empty list
    data class Rule(val option1: List<String>, val option2: List<String>)

    private val messages: List<String> = input.last().split("\n")
    private val rules: Map<String, Rule>

    // Example input:
    // 0: 4 1 5
    // 1: 2 3 | 3 2
    // 4: "a"
    init {
        rules = input.first().split("\n").map { line ->
            val id = line.substringBefore(":")
            val options = line.substringAfter(": ").split(" | ")
            id to Rule(options.first().replace("\"", "").split(" "),
                    options.getOrNull(1)?.split(" ") ?: listOf())
        }.toMap()
    }

    // There are only two letters: a and b
    private fun String.isLetter() = this in listOf("a", "b")

    // returns if matches so far and index of next to check (-1 if there is no match)
    // Recursively check if the msg is valid starting from startIndex under the given ruleNumber
    // If the rule indicated by ruleNumber can be applied on the message starting at startIndex the
    // return value is (true, <index of the first character not covered by ruleNumber>)
    // If the rule is not fulfilled false and an unspecified index is returned.
    private fun check(msg: String, startIndex: Int, ruleNumber: String, rules: Map<String, Rule>): Pair<Boolean, Int> {
        val currentRule = rules[ruleNumber]!!
        when {
            startIndex !in msg.indices -> {
                // There are still required rules left, but we've run trough the whole string
                return Pair(false, -1)
            }
            currentRule.option1.first().isLetter() -> {
                // This rule is a leaf node, check if it matches the letter at the current position
                val letterMatches = msg[startIndex].toString() == currentRule.option1.first()
                return Pair(letterMatches && letterMatches, startIndex + 1)
            }
        }

        val options = listOf(currentRule.option1, currentRule.option2)
        for (option in options) {
            var index = startIndex

            // for the current option, check if all it's requirements are fulfilled
            for (i in option.indices) {
                val nextRuleNumber = option[i]
                // Recursively (depth first) check if the character at the current position is correct.
                val (result, newIndex) = check(msg, index, nextRuleNumber, rules)
                if (!result) {
                    // The next character isn't valid according to the current option. Try with the next
                    // one if there is any.
                    break
                }
                // This option looks promising so far, skip ahead as many character as was required to fulfil
                // the recently consumed rule.
                index = newIndex

                if (i == currentRule.option1.size - 1) {
                    // We've checked all rules available for this option and they are all valid since we
                    // haven't bailed out yet. This option is valid and the next character to check
                    // is at newIndex.
                    return Pair(true, newIndex)
                }
            }
        }

        // There are no options that works
        return Pair(false, -1)
    }

    private fun initialCheck(msg: String, rules: Map<String, Rule>): Boolean {
        val (result, index) = check(msg, 0, "0", rules)
        return result && index == msg.length
    }

    fun solvePart1(): Int {
        return messages.filter { initialCheck(it, rules) }.count()
    }

    private fun changeRules(): Map<String, Rule> {
        return rules.toMutableMap().apply {
            this["8"] = Rule(listOf("42"), listOf("42", "8"))
            this["11"] = Rule(listOf("42", "31"), listOf("42", "11", "31"))
        }
    }

    // 8: 42 | 42 8
    // 11: 42 31 | 42 11 31
    fun solvePart2(): Int {
        return messages.filter { initialCheck(it, changeRules()) }.also { println(it) }.count()
    }
}

/**
From the part 2 example the following should match:
bbabbbbaabaabba
babbbbaabbbbbabbbbbbaabaaabaaa **
aaabbbbbbaaaabaababaabababbabaaabbababababaaa
bbbbbbbaaaabbbbaaabbabaaa **
bbbababbbbaaaaaaaabbababaaababaabab
ababaaaaaabaaab
ababaaaaabbbaba
baabbaaaabbaaaababbaababb
abbbbabbbbaaaababbbbbbaaaababb **
aaaaabbaabaaaaababaa **
aaaabbaabbaaaaaaabbbabbbaaabbaabaaa
aabbbbbaabbbaaaaaabbbbbababaaaaabbaaabba **

entries marked with ** does not match.
 */