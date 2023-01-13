package aoc2022

class Day21(input: List<String>) {

    /**
     * A monkey can yell when all their numbers are known, until then it's just listening for others to yell.
     */
    data class Monkey(val name: String, val operationParts: List<String>) {
        private val listeningTo = if (operationParts.size == 1) {
            mutableSetOf()
        } else {
            mutableSetOf(operationParts.first(), operationParts.last())
        }
        val hasHeard = mutableMapOf<String, Long>()

        /**
         * The monkey can yell if there is no one left to listen to.
         */
        fun canYell() = listeningTo.isEmpty()

        /**
         * Yell the number. Can only be called when the monkey has finished listening.
         */
        fun yell(): Long {
            return if (operationParts.size == 1) {
                operationParts.first().toLong()
            } else {
                val op: (Long, Long) -> Long = when (operationParts[1]) {
                    "+" -> Long::plus
                    "-" -> Long::minus
                    "*" -> Long::times
                    "/" -> Long::div
                    else -> error("unknown operation")
                }
                op(hasHeard[operationParts.first()]!!, hasHeard[operationParts.last()]!!)
            }
        }

        /**
         * Hear what another monkey yells and remember the value if it's an important monkey yelling.
         */
        fun hear(yellingMonkey: String, value: Long) {
            if (yellingMonkey in listeningTo) {
                listeningTo.remove(yellingMonkey)
                hasHeard[yellingMonkey] = value
            }
        }

        /**
         * Given what this monkey should yell (desiredResult), return the monkey that needs to yell and
         * the value that monkey need to yell so that this monkey can provide the desired result.
         */
        fun isWaitingForXtoYellY(desiredResult: Long): Pair<String, Long> {
            val waitingFor = listeningTo.single()
            val known = hasHeard.values.single()
            val needToHear = when {
                // desired = known + unknown ==> unknown = desired - known
                operationParts[1] == "+" -> desiredResult - known
                // desired = known * unknown ==> unknown = desired / known
                operationParts[1] == "*" -> desiredResult / known

                // desired = unknown - known ==> unknown = desired + known
                operationParts[1] == "-" && operationParts[0] == waitingFor -> desiredResult + known
                // desired = known - unknown ==> unknown = known - desired
                operationParts[1] == "-" -> known - desiredResult

                // desired = unknown / known ==> unknown = desired * known
                operationParts[1] == "/" && operationParts[0] == waitingFor -> desiredResult * known
                // desired = known / unknown ==> unknown = known / desired
                operationParts[1] == "/" -> known / desiredResult
                else -> error("unknown operation")
            }
            return waitingFor to needToHear
        }
    }

    private val monkeys = input.map { line ->
        val (name, op) = line.split(": ")
        Monkey(name, op.split(" "))
    }.associateBy { it.name }

    private fun yellParty(humanWaitsWithYelling: Boolean = false) {
        val canYell = mutableListOf<Monkey>()
        val waitingToYell = mutableListOf<Monkey>()
        monkeys.values.forEach {
            if (it.canYell()) {
                canYell.add(it)
            } else {
                waitingToYell.add(it)
            }
        }

        // Have all monkeys that can yell, yell. Then, when a monkey hears all numbers its waiting on,
        // put it in the canYell queue so that it also can yell its number. Keep going until everyone has yelled.
        while (canYell.isNotEmpty()) {
            val toYell = canYell.removeAt(0)
            if (humanWaitsWithYelling && toYell.name == "humn") {
                continue
            }
            val result = toYell.yell()
            for (i in waitingToYell.indices.reversed()) {
                val hearingMonkey = waitingToYell[i]
                hearingMonkey.hear(toYell.name, result)
                if (hearingMonkey.canYell()) {
                    waitingToYell.removeAt(i)
                    canYell.add(hearingMonkey)
                }
            }
        }
    }

    fun solvePart1(): Long {
        yellParty()
        return monkeys["root"]!!.yell()
    }

    fun solvePart2(): Long {
        // First let everyone that can yell, except the human
        yellParty(true)

        // After that work backwards from the root checking what everyone needs to yell to fulfil the desire of the
        // monkey before it.
        var currMonkey = monkeys["root"]!!
        // The root is always addition, so for the two terms to be equal the root needs to yell double the amount
        // of the known term.
        var needToYell = currMonkey.hasHeard.values.single() * 2
        while (currMonkey.name != "humn") {
            val (from, needed) = currMonkey.isWaitingForXtoYellY(needToYell)
            currMonkey = monkeys[from]!!
            needToYell = needed
        }
        return needToYell
    }
}