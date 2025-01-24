package aoc2024

class Day24(input: List<String>) {
    private val wires = input.first().split("\n").map { line ->
        val (key, value) = line.split(": ")
        key to value.toInt()
    }

    private val rawGates = input.last().split("\n").associate { line ->
        val (a, op, b, _, out) = line.split(" ")
        Gate2(setOf(a, b), op) to out
    }

    private data class Gate2(val inputs: Set<String>, val op: String)


    private val gates = input.last().split("\n").map { line ->
        val (a, op, b, _, out) = line.split(" ")
        Gate(
            mutableMapOf(a to null, b to null),
            when (op) {
                "AND" -> Int::and
                "OR" -> Int::or
                "XOR" -> Int::xor
                else -> error("Unknown operator")
            },
            out
        )
    }

    private class Gate(val inputs: MutableMap<String, Int?>, val op: (Int, Int) -> Int, val out: String) {
        fun provideInput(input: String, value: Int): Int? {
            inputs[input] = value
            val (a, b) = inputs.values.toList()
            return if (a != null && b != null) op(a, b) else null
        }
    }

    private val listeners = buildMap<String, MutableList<Gate>> {
        gates.forEach { gate ->
            gate.inputs.keys.forEach { input ->
                if (this[input] == null) {
                    this[input] = mutableListOf()
                }
                this[input]!!.add(gate)
            }
        }
    }

    private fun runSystem(): MutableList<Pair<String, Int>> {
        val wiresWithSignals = wires.toMutableList()
        val zWires = mutableListOf<Pair<String, Int>>()
        while (wiresWithSignals.isNotEmpty()) {
            val (wire, value) = wiresWithSignals.removeFirst()
            listeners[wire]?.forEach { gate ->
                val output = gate.provideInput(wire, value)
                if (output != null) {
                    if (gate.out.startsWith("z")) {
                        zWires.add(gate.out to output)
                    } else {
                        wiresWithSignals.add(gate.out to output)
                    }
                }
            }
        }
        return zWires
    }

    fun solvePart1(): Long {
        val z = runSystem()
        return z.sortedByDescending { it.first }.joinToString("") { it.second.toString() }.toLong(2)
    }

    /**
     * A half-adder takes two inputs and returns one output and one carry
     * x XOR y -> z
     * x AND y -> carry
     */
    private fun verifyHalfAdder(incorrectWires: MutableList<String>): String {
        val out = rawGates[Gate2(setOf("x00", "y00"), "XOR")]!!
        val carry = rawGates[Gate2(setOf("x00", "y00"), "AND")]!!
        if (!out.startsWith("z")) {
            incorrectWires.addAll(setOf(carry, out))
        }
        return carry.fixSwappedOutputIfNeeded(incorrectWires)
    }

    /**
     * A full-adder takes two inputs, an incoming carry and returns one output and one carry and
     * is built up of the following gates (int is an abbreviation for internal gate)
     * x XOR y -> int1
     * carryIn XOR int1 -> z
     * carryIn AND int1 -> int2
     * x AND y -> int3
     * int2 OR int3 -> carryOut
     *
     * Assuming that the puzzle is set up so that all errors happens in parallel (which it is)
     * means that there are at most 4 different kind of errors possible:
     * 1. z output is swapped with int2
     * 2. z output is swapped with int3
     * 3. z output is swapped with carryOut
     * 4. int1 is swapped with int3
     *
     * int1 can't be swapped with anything else, because everything except int3 depends on int1
     * int3 can't be swapped with int2 since both are used as input for the carryOut gate and
     * "int2 OR int3" is the same as "int3 OR int2".
     *
     * @param x the x-input to the full adder
     * @param y the y-input to the full adder
     * @param incorrectWires a list of all incorrect wires so far, when incorrect wires are detected
     *                       this list is updated
     * @return The wire that's used for the carry of this full adder.
     */
    private fun verifyFullAdder(x: String, y: String, c: String, incorrectWires: MutableList<String>): String {
        val int1 = rawGates[Gate2(setOf(x, y), "XOR")]!!
        val int3 = rawGates[Gate2(setOf(x, y), "AND")]!!

        val outputGate = rawGates[Gate2(setOf(c, int1), "XOR")]
        var wireToCarryGate = int3 // The wire to the carry gate is generally int3

        if (outputGate == null) {
            // can't find the output gate, it means int1 and int3 has been swapped.
            incorrectWires.addAll(setOf(int1, int3))
            wireToCarryGate = int1 // fix which gate leads to the carry gate.
        } else if (!outputGate.startsWith("z")) {
            // Output gate has been swapped with some other gate.
            // Put the non-output wire last in the incorrectWires list
            // as it will be needed when identifying the carry gate
            incorrectWires.addAll(setOf("z" + x.drop(1), outputGate))
        }
        // The wireToCarryGate leads only to the carry gate and nowhere else,
        // so the carry gate can be found by looking for this wire.
        return rawGates
            .filter { wireToCarryGate.fixSwappedOutputIfNeeded(incorrectWires) in it.key.inputs }
            .values.single()
            .fixSwappedOutputIfNeeded(incorrectWires)
    }

    /**
     * If the output wire has been swapped with some other wire, that wire will start
     * with z. When this is the case the correct name for that wire is stored in the
     * last entry of the incorrectWires list.
     */
    private fun String.fixSwappedOutputIfNeeded(incorrectWires: List<String>): String {
        return if (this.startsWith("z")) incorrectWires.last() else this
    }

    /**
     * Adding two binary numbers with logic gates is done by using a half-adder for the least
     * significant bit and then one full-adder for all the remaining bits.
     * See: https://www.101computing.net/binary-additions-using-logic-gates/
     *
     * A full adder looks like this (with assigned names I use in the code):
     *                        +-----+
     * Cin --------------+----| XOR |------------- z
     *                   | /--|outpt|
     *                   | |  +-----+
     *                   | |
     *           +-----+ | |  +-----+
     * x  -+-----| XOR |-|-+--| AND |-\
     *     | /---| int1| \----| int2| |
     *     | |   +-----+      +-----+ |
     *     | |                        |
     *     | |   +-----+              |   +-----+
     *     \-|---| AND |              \---| OR  |-- Cout
     * y ----+---| int3|------------------|carry|
     *           +-----+                  +-----+
     *
     * Assumptions made (which are true for the given input):
     *  - Two swapped wires happens within the same full-adder
     *  - Two swapped wires are "parallel" to each other, that is
     *    the output wire can be swapped with int2, int3 or carry
     *    but not with int1 since output depends on int1.
     *
     * Testing showed that they hold and the implementation becomes much easier.
     */
    fun solvePart2(): String {
        val incorrectWires = mutableListOf<String>()
        var carry = verifyHalfAdder(incorrectWires)
        repeat(44) {
            val num = (it + 1).toString().padStart(2, '0')
            carry = verifyFullAdder("x$num", "y$num", carry, incorrectWires)
        }
        return incorrectWires.sorted().joinToString(",")
    }
}