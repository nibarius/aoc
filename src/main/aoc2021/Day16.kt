package aoc2021

class Day16(input: List<String>) {
    val data = input.single()
        .map { it.digitToInt(16).toString(2).padStart(4, '0') }
        .joinToString("")
        .map { it.digitToInt() }


    // Read the given number of bits, consuming them from the raw input. Return them as an Int
    private fun readBits(howMany: Int, raw: MutableList<Int>) =
        readRawBits(howMany, raw).joinToString("").toInt(2)

    // Read the given number of bits, consuming them from the raw input. Return them as a list of binary numbers
    private fun readRawBits(howMany: Int, raw: MutableList<Int>): List<Int> {
        val ret = raw.take(howMany)
        repeat(howMany) {
            raw.removeAt(0)
        }
        return ret
    }

    // Returns the value of the literal
    private fun parseLiteral(raw: MutableList<Int>): Long {
        val ret = mutableListOf<Int>()
        do {
            val cont = readBits(1, raw)
            ret.addAll(readRawBits(4, raw))
        } while (cont == 1)
        return ret.joinToString("").toLong(2)
    }

    // Returns the result of the operator
    private fun parseOperator(operator: Int, raw: MutableList<Int>): Long {
        val lengthTypeId = readBits(1, raw)
        val subPackets = mutableListOf<Long>()
        if (lengthTypeId == 0) {
            val lengthSubPackets = readBits(15, raw)
            val lengthBefore = raw.size
            while (lengthBefore - raw.size < lengthSubPackets) {
                subPackets.add(parsePacket(raw))
            }
        } else {
            val numSubPackets = readBits(11, raw)
            repeat(numSubPackets) {
                subPackets.add(parsePacket(raw))
            }
        }
        return calculateValue(operator, subPackets)
    }

    private val versions = mutableListOf<Int>()

    // Returns the value of this packet (either literal value, or the result of the operator)
    private fun parsePacket(raw: MutableList<Int>): Long {
        val packetVersion = readBits(3, raw)
        versions.add(packetVersion)
        return when (val packetIdType = readBits(3, raw)) {
            4 -> parseLiteral(raw)
            else -> parseOperator(packetIdType, raw)
        }
    }

    private fun calculateValue(operator: Int, operands: List<Long>) = when (operator) {
        0 -> operands.sum()
        1 -> operands.reduce { acc, literal -> acc * literal }
        2 -> operands.minOf { it }
        3 -> operands.maxOf { it }
        5 -> if (operands.last() < operands.first()) 1 else 0
        6 -> if (operands.last() > operands.first()) 1 else 0
        7 -> if (operands.first() == operands.last()) 1 else 0
        else -> error("unknown operator")
    }

    fun solvePart1(): Int {
        parsePacket(data.toMutableList())
        return versions.sum()
    }

    fun solvePart2(): Long {
        return parsePacket(data.toMutableList())
    }
}