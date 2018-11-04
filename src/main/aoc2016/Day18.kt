package aoc2016

class Day18(private val rows: Int, private val firstRow: String) {

    private fun Char.isTrap(): Boolean = this == '^'

    private fun nextRow(currentRow: String): String {
        val next = CharArray(currentRow.length) { i ->
            val left = if (i == 0) '.' else currentRow[i- 1]
            val center = currentRow[i]
            val right = if (i == currentRow.length - 1) '.' else currentRow[i + 1]
            if ((left.isTrap() && center.isTrap() && !right.isTrap())
                || (!left.isTrap() && center.isTrap() && right.isTrap())
                || (left.isTrap() && !center.isTrap() && !right.isTrap())
                || (!left.isTrap() && !center.isTrap() && right.isTrap())){
                '^'
            } else {
                '.'
            }
        }
        return next.joinToString("")
    }

    private fun generateRoom(): List<String> {
        val room = mutableListOf(firstRow)
        repeat(rows - 1) {
            room.add(nextRow(room.last()))
        }
        return room
    }

    fun solvePart1(): Int {
        return generateRoom().sumBy { it.count {c -> c == '.' } }
    }

    fun solvePart2(): Int {
        return solvePart1()
    }
}
