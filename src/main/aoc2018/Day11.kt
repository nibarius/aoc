package aoc2018

class Day11(input: String) {

    private val serialNumber = input.toInt()

    private val grid = List(300) { x -> List(300) { y -> getPowerLevel(x + 1, y + 1) } }

    fun getPowerLevel(x: Int, y: Int): Int {
        val rackId = x + 10
        var powerLevel = rackId * y
        powerLevel += serialNumber
        powerLevel *= rackId
        powerLevel = powerLevel.toString().takeLast(3).take(1).toInt()
        powerLevel -= 5
        return powerLevel
    }

    // The sum of all the numbers inside the given square
    private fun squareValue(x: Int, y: Int, size: Int): Int {
        var sum = 0
        for (dx in 0 until size) {
            for (dy in 0 until size) {
                sum += grid[x + dx - 1][y + dy - 1]
            }
        }
        return sum
    }

    // The sum of all numbers in a given row with the given start x coordinate and row length
    private fun rowSum(row: Int, start: Int, size: Int): Int {
        var sum = 0
        for (x in start until start + size) {
            sum += grid[x - 1][row - 1]
        }
        return sum
    }

    // The sum of all numbers in a given column starting from the top and with the given length
    private fun colSum(col: Int, size: Int): Int {
        var sum = 0
        for (y in 1..size) {
            sum += grid[col - 1][y - 1]
        }
        return sum
    }

    // Find the square with the largest value with the given size
    // To make things faster calculate the full square value only for
    // the top left square. For all other squares calculate the value
    // based on the previous square and the columns/rows that differs.
    private fun findMaxSquare(size: Int): Pair<String, Int> {
        var address = ""
        var previousXSquare = squareValue(1, 1, size)
        var previousYSquare = previousXSquare
        var max = previousXSquare
        for (x in 1..300 - size) {
            if (x != 1) { // x = 1 is the initial value calculated outside the loop
                // Calculate the value of the square to the right of the previously calculated square at y=1
                // previous value - column to the left + column to the right
                val currentSquare = previousXSquare - colSum(x - 1, size) + colSum(x + size - 1, size)
                if (currentSquare > max) {
                    max = currentSquare
                    address = "$x,0,$size"
                }
                previousYSquare = currentSquare
                previousXSquare = currentSquare
            }
            for (y in 2..300 - size) { // y = 1 is calculated outside this loop
                // Calculate the value of the square of the given size starting at (x,y) based on the previously calculate square above this one
                // previous value - row above + row below
                val currentSquare = previousYSquare - rowSum(y - 1, x, size) + rowSum(y + size - 1, x, size)


                if (currentSquare > max) {
                    max = currentSquare
                    address = "$x,$y,$size"
                }
                previousYSquare = currentSquare
            }
        }
        return Pair(address, max)
    }

    fun solvePart1(): String {
        return findMaxSquare(3).first.substringBeforeLast(",")
    }

    fun solvePart2(): String {
        return (1..300).map { findMaxSquare(it) }.maxByOrNull { it.second }!!.first
    }
}