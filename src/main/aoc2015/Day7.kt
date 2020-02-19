package aoc2015

@ExperimentalUnsignedTypes
class Day7(val instructions: List<String>) {

    // Get the value if it's been set before, otherwise return null
    fun getValue(wire: String, wires: Map<String, Int>): Int? {
        return wire.toIntOrNull() ?: wires.getOrElse(wire) { null }
    }

    // Get the two values if both have been set before, otherwise return null
    private fun getValues(wire1: String, wire2: String, wires: Map<String, Int>): Pair<Int, Int>? {
        val a = wire1.toIntOrNull() ?: wires.getOrElse(wire1) { null }
        val b = wire2.toIntOrNull() ?: wires.getOrElse(wire2) { null }
        if (a == null || b == null) return null
        return a to b
    }

    // Set the value unless it's already been set since before (important for part 2)
    fun setValue(wire: String, value: Int, wires: MutableMap<String, Int>) {
        if (!wires.containsKey(wire)) {
            // All wires are 16 bit unsigned numbers so discard anything above the lowest 16 bits.
            wires[wire] = value.toUShort().toInt()
        }
    }

    // Tries to do an instruction, or returns null if the input is not available yet
    private fun doInstruction(inst: String, wires: MutableMap<String, Int>): Unit? {
        val parts = inst.split(" ")
        return when {
            parts[1] == "->" -> getValue(parts[0], wires)?.let { setValue(parts[2], it, wires) }
            parts[0] == "NOT" -> getValue(parts[1], wires)?.let { setValue(parts[3], it.inv(), wires) }
            parts[1] == "AND" -> getValues(parts[0], parts[2], wires)
                    ?.let { (a, b) -> setValue(parts[4], a and b, wires) }
            parts[1] == "OR" -> getValues(parts[0], parts[2], wires)
                    ?.let { (a, b) -> setValue(parts[4], a or b, wires) }
            parts[1] == "LSHIFT" -> getValues(parts[0], parts[2], wires)
                    ?.let { (a, b) -> setValue(parts[4], a shl b, wires) }
            parts[1] == "RSHIFT" -> getValues(parts[0], parts[2], wires)
                    ?.let { (a, b) -> setValue(parts[4], a ushr b, wires) }
            else -> throw RuntimeException("Unknown instruction: $inst")
        }
    }

    private fun doAllInstructions(bOverride: Int = 0): MutableMap<String, Int> {
        val wires = mutableMapOf<String, Int>()
        val remaining = instructions.toMutableList()
        var offset = 0
        if (bOverride != 0) {
            wires["b"] = bOverride
        }
        while (remaining.isNotEmpty()) {
            offset %= remaining.size
            if (doInstruction(remaining[offset], wires) == null) {
                // We don't have all input values for this gate yet, skip it for now and try next instead
                offset++
            } else {
                // Gate could be handled and have provided output
                remaining.removeAt(offset)
            }
        }
        return wires
    }

    fun solvePart1(): Int {
        return doAllInstructions()["a"]!!
    }

    fun solvePart2(): Int {
        val a = solvePart1()
        return doAllInstructions(a)["a"]!!
    }
}