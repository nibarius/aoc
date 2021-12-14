package aoc2021

import AMap
import Pos

class Day13(input: List<String>) {

    sealed class Fold(val at: Int) {
        class Horizontal(at: Int) : Fold(at)
        class Vertical(at: Int) : Fold(at)
    }

    private val paper = AMap()
    private val folds: List<Fold>

    init {
        input.takeWhile { it.isNotBlank() }
            .map { it.split(",") }
            .forEach { (x, y) ->
                paper[Pos(x.toInt(), y.toInt())] = '#'
            }

        folds = input.takeLastWhile { it.isNotBlank() }
            .map { it.substringAfter("fold along ") }
            .map { it.split("=") }
            .map {
                when (it.first()) {
                    "x" -> Fold.Vertical(it.last().toInt())
                    else -> Fold.Horizontal(it.last().toInt())
                }
            }.toList()
    }

    /**
     * Generic folding function. The part that's not folded starts at 0 and goes to xyMax. xyFoldStart notes
     * the xy coordinate to read from to be in the part being folded while xyFoldStep say in which direction
     * to step each iteration.
     *
     * Horizontal folds (fold along a horizontal line / y coordinate) has -1 yFoldStep and yFoldStart at 2 * fold point
     * while xFoldStep is 1 and xFoldStart is 0 as only points along the y-axis should move.
     *
     * Vertical folds works the opposite way with -1 xFoldStep and xFoldStart at 2 * fold point.
     */
    private fun genericFold(xMax: Int, xFoldStart: Int, xFoldStep: Int, yMax: Int, yFoldStart: Int, yFoldStep: Int) {
        for (y in 0..yMax) {
            for (x in 0..xMax) {
                val toFold = Pos(xFoldStart + x * xFoldStep, yFoldStart + y * yFoldStep)
                if (paper[toFold] == '#') {
                    paper[Pos(x, y)] = '#'
                    paper.keys.remove(toFold)
                }
            }
        }
    }

    private fun doFold(fold: Fold) {
        when (fold) {
            is Fold.Horizontal -> {
                genericFold(
                    paper.xRange().last, 0, 1,
                    fold.at, fold.at * 2, -1
                )
            }
            is Fold.Vertical -> {
                genericFold(
                    fold.at, fold.at * 2, -1,
                    paper.yRange().last, 0, 1,
                    )
            }
        }
    }

    fun solvePart1(): Int {
        doFold(folds.first())
        return paper.keys.size
    }

    fun solvePart2(): String {
        folds.forEach { doFold(it) }
        return paper.toString(' ')
    }
}