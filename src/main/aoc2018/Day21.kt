package aoc2018


class Day21(val input: List<String>) {

    fun solvePart1(): Int {
        val elf = ElfCode(input)
        elf.runProgram(28)
        return elf.registers[1].toInt()
    }

    fun solvePart2(): Int {
        return theProgram()
    }

    // Faster version of the program
    private fun theProgram(): Int {
        val seen = mutableSetOf<Int>()
        var prev = -1
        var b = 0
        var e: Int

        while (true) {
            e = b or 0x10000
            b = 16298264
            while (true) {
                b += e and 0xff
                b = b and 0xffffff
                b *= 65899
                b = b and 0xffffff

                if (e < 0x100) {
                    break
                }
                e /= 0x100
            }

            val value = b
            if (seen.contains(value)) {
                return prev
            }
            seen.add(value)
            prev = value
        }
    }
}