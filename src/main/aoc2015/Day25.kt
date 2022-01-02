package aoc2015

class Day25(input: List<String>) {
    private val row = input.first().substringBeforeLast(",").substringAfterLast(" ").toInt()
    private val col = input.first().substringBeforeLast(".").substringAfterLast(" ").toInt()

    private val firstCode = 20151125L
    private val multiplicand = 252533
    private val divisor = 33554393

    private fun calculateCode(n: Int): Int {
        var ret = firstCode
        repeat(n - 1) {
            ret = (ret * multiplicand) % divisor
        }
        return ret.toInt()
    }

    //   | 1   2   3   4   5   6
    //---+---+---+---+---+---+---+
    // 1 |  1   3   6  10  15  21
    // 2 |  2   5   9  14  20
    // 3 |  4   8  13  19
    // 4 |  7  12  18
    // 5 | 11  17
    // 6 | 16
    //

    private fun indexOfRowInColumnOne(row: Int): Int {
        // first column
        // row 1 = 1, row 2 = row 1 + 1, row 3 = row 2 + 2, row 4 = row 3 + 3
        // ==> row 6 = 1 + 1 + 2 + 3 + 4 + 5
        return 1 + (1 until row).sum()
    }

    private fun indexOf(row: Int, col: Int): Int {
        // For column 2 take the index of column one and increase with current row + 1
        // For column 3 take the index of column 2 and increase with previous increase + 1
        // Example, row 4
        // 7, 12 (= 7 + 5), 18 (= 12 + 6), 25 (= 18 + 7), etc
        return indexOfRowInColumnOne(row) + (1 until col).sumOf { row + it }
    }

    fun solvePart1(): Int {
        return calculateCode(indexOf(row, col))
    }
}