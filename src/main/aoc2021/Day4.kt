package aoc2021

class Day4(input: List<String>) {
    private val toCall: List<Int>
    private val boards: List<Board>

    init {
        toCall = input.first().split(",").map { it.toInt() }
        val currentGrid = mutableListOf<Int>()
        val tmp = mutableListOf<Board>()
        input.drop(2).forEach { line ->
            if (line.isNotBlank()) {
                line.split(" ")
                    .filter { it.isNotBlank() }
                    .map { it.toInt() }
                    .forEach { currentGrid.add(it) }
            } else {
                tmp.add(Board(currentGrid.toList()))
                currentGrid.clear()
            }
        }
        tmp.add(Board(currentGrid))
        boards = tmp
    }


    data class Board(val grid: List<Int>) {
        private val called = mutableListOf<Int>()

        /**
         * Call a number and return true if it resulted in bingo.
         *
         * Only checks the row/column of the called number for bingo since
         * other parts of the grid is unchanged.
         */
        fun call(number: Int): Boolean {
            val index = grid.indexOf(number)
            if (index == -1) {
                return false
            }
            called.add(number)

            // Row and column of where the called number is in the grid.
            val row = index / 5
            val column = index % 5

            val numbersInRow = (0..4).map { grid[row * 5 + it] }
            val numbersInCol = (0..4).map { grid[it * 5 + column] }

            // Bingo if all the items in the modified row or column has been called.
            return numbersInRow.all { it in called } || numbersInCol.all { it in called }
        }

        // The score is sum of uncalled numbers multiplied by the number
        // that resulted in bingo.
        fun score(): Int {
            return grid.filter { it !in called }.sum() * called.last()
        }
    }

    private fun playBingo(stopOnVictory: Boolean): Int {
        var lastScore = 0
        val remainingBoards = boards.toMutableList()
        toCall.forEach { calledNumber ->
            for (i in remainingBoards.indices.reversed()) {
                val isBingo = remainingBoards[i].call(calledNumber)
                if (isBingo) {
                    lastScore = remainingBoards[i].score()
                    if (stopOnVictory) {
                        return lastScore
                    }
                    remainingBoards.removeAt(i)
                }
            }
        }
        return lastScore
    }

    fun solvePart1(): Int {
        return playBingo(stopOnVictory = true)
    }

    fun solvePart2(): Int {
        return playBingo(stopOnVictory = false)
    }
}