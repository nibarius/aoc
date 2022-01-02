package aoc2020

class Day14(input: List<String>) {
    sealed class Instruction {
        data class Mask(val mask: String) : Instruction()
        data class Write(val address: String, val value: String) : Instruction()
    }

    val instructions = input.map { line ->
        when {
            line.startsWith("mask") -> Instruction.Mask(line.substringAfter("mask = "))
            line.startsWith("mem") -> {
                val (address, value) = "mem\\[(\\d+)] = (\\d+)".toRegex().find(line)!!.destructured
                Instruction.Write(
                        address.toInt().toString(2).padStart(36, '0'),
                        value.toInt().toString(2).padStart(36, '0')
                )
            }
            else -> error("unknown input: $line")
        }
    }

    private fun String.applyMask(mask: String, maskIgnores: Char): String {
        return CharArray(36) { if (mask[it] == maskIgnores) this[it] else mask[it] }.joinToString("")
    }

    // Write one masked value to one address
    private fun writeOne(memory: MutableMap<String, String>, instruction: Instruction.Write, mask: String) {
        memory[instruction.address] = instruction.value.applyMask(mask, 'X')
    }

    // Write one value to all addresses matching the masked address
    private fun writeAll(memory: MutableMap<String, String>, instruction: Instruction.Write, mask: String) {
        val address = instruction.address.applyMask(mask, '0')
        val ret = mutableListOf(CharArray(36))
        for (i in 0 until 36) {
            if (address[i] == 'X') {
                val copy = ret.deepCopy()
                ret.forEach { it[i] = '0' }
                copy.forEach { it[i] = '1' }
                ret.addAll(copy)
            } else {
                ret.forEach { it[i] = address[i] }
            }
        }
        ret.forEach { memory[it.joinToString("")] = instruction.value }
    }

    private fun List<CharArray>.deepCopy(): MutableList<CharArray> =
            mutableListOf<CharArray>().apply { this@deepCopy.forEach { add(it.clone()) } }

    private fun run(writeFn: (MutableMap<String, String>, Instruction.Write, String) -> Unit): Long {
        val memory = mutableMapOf<String, String>()
        var mask = ""
        instructions.forEach { instruction ->
            when (instruction) {
                is Instruction.Mask -> mask = instruction.mask
                is Instruction.Write -> writeFn(memory, instruction, mask)
            }
        }
        return memory.values.sumOf { it.toLong(2) }
    }

    fun solvePart1(): Long {
        return run(this::writeOne)
    }

    fun solvePart2(): Long {
        return run(this::writeAll)
    }
}
