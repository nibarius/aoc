package aoc2018


class Day19(val input: List<String>) {
    private val registers = MutableList(6) { 0 }

    fun solvePart1(): Long {
        val elf = ElfCode(input)
        elf.runProgram()
        return elf.registers[0]
    }

    private fun theProgram() {
        // init part (line 1 + 17 - 35)
        registers[2] += 2 // line 17
        registers[2] *= registers[2] // line 18
        registers[2] *= 19 // line 19
        registers[2] *= 11 // line 20
        registers[1] += 6  // line 21
        registers[1] *= 22 // line 22
        registers[1] += 18 // line 23
        registers[2] += registers[1] // line 24
        if (registers[0] == 1) { // line 25 - 26
            registers[1] = 27
            registers[1] *= 28
            registers[1] += 29
            registers[1] *= 30
            registers[1] *= 14
            registers[1] *= 32
            registers[2] += registers[1]
        }

/*      // Below part calculates the sum of all factorials of registers[2] and put it in registers[0]
        registers[5] = 1 //line 1
        var a = registers[0]
        var b = registers[1]
        val c = registers[2]
        var d = registers[3]
        //var e = registers[4]
        var f = registers[5]
        do { // a = sum of all factors of of c
            d = 1 //line 2
            do {
                b = f * d // line 3
                if (b == c) { // line 4-7
                    a += f
                }
                d++ // line 8
            } while (d <= c) // line 9-11
            f++ // line 12
        } while (f <= c) // line 13-15
        registers[0] = a
        registers[1] = b
        registers[2] = c
        registers[3] = d
        //registers[4] = e
        registers[5] = f
        return //line 16*/

        // Do the calculation in a more efficient way
        registers[0] = sumOfFactors(registers[2])
    }

    private fun sumOfFactors(number: Int): Int {
        return (1..number)
                .filter { number % it == 0 }
                .sum()
    }

    fun solvePart2(): Int {
        registers[0] = 1
        theProgram()
        return registers[0]
    }
}