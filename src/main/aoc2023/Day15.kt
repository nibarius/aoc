package aoc2023

class Day15(input: List<String>) {
    val steps = input.first().split(",")

    private fun String.hash(): Int {
        var current = 0
        forEach { ch ->
            current += ch.code
            current *= 17
            current %= 256
        }
        return current
    }

    private fun parseInstruction(raw: String): Instruction {
        val regex = "([a-z]+)([=-])([1-9]?)".toRegex()
        val (label, op, focalLength) = regex.matchEntire(raw)!!.destructured
        return Instruction(label, label.hash(), op.first(), focalLength.toIntOrNull() ?: 0)
    }

    private data class Instruction(val label: String, val box: Int, val op: Char, val focalLength: Int)

    // box number to list of lenses (label, focal length pairs)
    private val boxes = (0..255).associateWith { mutableListOf<Pair<String, Int>>() }

    private fun doInstruction(instruction: Instruction) {
        when (instruction.op) {
            '=' -> {
                val content = boxes[instruction.box]!!
                val index = content.indexOfFirst { it.first == instruction.label }
                if (index >= 0) {
                    content[index] = Pair(instruction.label, instruction.focalLength)
                } else {
                    content.add(Pair(instruction.label, instruction.focalLength))
                }
            }
            '-' -> {
                boxes[instruction.box]!!.removeIf { it.first == instruction.label }
            }
        }
    }

    private fun focusingPower(): Int {
        return boxes.map { (boxNumber, lenses) ->
            lenses.withIndex().sumOf { (slot, pair) ->
                (1 + boxNumber) * (1 + slot) * pair.second
            }
        }.sum()

    }

    fun solvePart1(): Int {
        return steps.sumOf { it.hash() }
    }

    fun solvePart2(): Int {
        steps.forEach { doInstruction(parseInstruction(it)) }
        return focusingPower()
    }
}