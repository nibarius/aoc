package aoc2021

import kotlin.system.measureNanoTime

class Day24(input: List<String>) {


    /*
chunk 0, inst 5: div z 1, inst 6: add x 15, inst 16: add y 9
chunk 1, inst 5: div z 1, inst 6: add x 11, inst 16: add y 1
chunk 2, inst 5: div z 1, inst 6: add x 10, inst 16: add y 11
chunk 3, inst 5: div z 1, inst 6: add x 12, inst 16: add y 3
chunk 4, inst 5: div z 26, inst 6: add x -11, inst 16: add y 10
chunk 5, inst 5: div z 1, inst 6: add x 11, inst 16: add y 5
chunk 6, inst 5: div z 1, inst 6: add x 14, inst 16: add y 0
chunk 7, inst 5: div z 26, inst 6: add x -6, inst 16: add y 7
chunk 8, inst 5: div z 1, inst 6: add x 10, inst 16: add y 9
chunk 9, inst 5: div z 26, inst 6: add x -6, inst 16: add y 15
chunk 10, inst 5: div z 26, inst 6: add x -6, inst 16: add y 4
chunk 11, inst 5: div z 26, inst 6: add x -16, inst 16: add y 10
chunk 12, inst 5: div z 26, inst 6: add x -4, inst 16: add y 4
chunk 13, inst 5: div z 26, inst 6: add x -2, inst 16: add y 9
     */
    /*init {
        input.chunked(18).forEachIndexed { index, list ->
            println("chunk $index, inst 5: ${list[4]}, inst 6: ${list[5]}, inst 16: ${list[15]}")
        }
    }*/


    fun theProgram2(input: List<Int>) {
        var (w, x, y, z) = listOf(0L, 0, 0, 0)
        val variantOne = listOf(15, 11, 10, 12, -11, 11, 14, -6, 10, -6, -6, -16, - 4, -2)
        val variantTwo = listOf(9, 1, 11, 3, 10, 5, 0, 7, 9, 15, 4, 10, 4, 9)
        println("with ${input.joinToString("")}")
        repeat(14) { digit ->
            w = input[digit].toLong()
            x = z % 26
            if (digit in listOf(4, 7, 9, 10, 11, 12, 13)) { // whenever variantOne is negative
                z /= 26
            }
            x += variantOne[digit]
            println("x: $x, w: $w")
            if (x != w) {
                z *= 26
                z += w + variantTwo[digit]
            }
            println("After $digit ($w): z=$z")
        }

        println("Theprogram 2: $w, $x, $y, $z")
        // desired: z == 0
    }

    fun theProgram3(input: List<Int>): Int {
        var (w, x, y, z) = listOf(0L, 0, 0, 0)
        val variantOne = listOf(15, 11, 10, 12, -11, 11, 14, -6, 10, -6, -6, -16, - 4, -2)
        val variantTwo = listOf(9, 1, 11, 3, 10, 5, 0, 7, 9, 15, 4, 10, 4, 9)
        var thisIsIt = false
        repeat(14) { digit ->
            w = input[digit].toLong()
            x = z % 26
            if (digit in listOf(4, 7, 9, 10, 11, 12, 13)) { // whenever variantOne is negative
                z /= 26
                thisIsIt = true
            }
            x += variantOne[digit]
            if (thisIsIt && x != w) {
                println("Break down at $digit, x: $x, w: $w")
                // failed on 'digit'
                return digit
            }
            thisIsIt = false
            if (x != w) {
                z *= 26
                z += w + variantTwo[digit]
            }
        }

        println("Theprogram 2: $w, $x, $y, $z")
        // desired: z == 0
        return -1
    }


    fun solvePart1(): Long {
        // index 3: 9, index 4: 1
        // index 6: 7, index 7: 1
        // index 8: 6, index 9: 9
        // index 10: 1
        //val testInput =  "99991993698469".map { it.digitToInt() } // fails on last
        val testInput =  "29991993698469".map { it.digitToInt() }
        theProgram3(testInput)
        return 29991993698469
    }

    fun solvePart2(): Long {
        //v testInput =  "11191293691111".map { it.digitToInt() } // breaks down at digit 11
        //l testInput =  "11191271141111".map { it.digitToInt() } // breaks down at 11
        //                01234567890123
        val testInput =  "14691271141118".map { it.digitToInt() }

        // 67 can be 71, 82, 93
        // 10 depends on 5
        // 11 depends on 2
        // 12 depends on 1
        // 13 depends on 0n
        theProgram3(testInput)
        return 14691271141118
    }
}