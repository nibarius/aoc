package aoc2024

class Day5(input: List<String>) {

    private fun processSection(section: String, splitOn: String): List<List<Int>> {
        return section.split("\n").map { line ->
            line.split(splitOn).map { it.toInt() }
        }
    }

    private val rules = processSection(input.first(), "|")
    private val updates = processSection(input.last(), ",")

    /**
     * Run the given function inline if the Boolean is true
     */
    private inline fun Boolean.ifTrue(fn: () -> Unit) = if (this) fn() else Unit

    private fun List<List<Int>>.pagesThatMustBeAfter(page: Int): List<Int> {
        return filter { it.first() == page }.map { it.last() }
    }

    private fun isValid(update: List<Int>): Boolean {
        val seenBefore = mutableSetOf<Int>()
        update.forEach { currentPage ->
            rules.pagesThatMustBeAfter(currentPage)
                .any { it in seenBefore }
                .ifTrue { return false }
            seenBefore.add(currentPage)
        }
        return true
    }

    fun solvePart1(): Int {
        return updates.filter { isValid(it) }.sumOf { it[it.size / 2] }
    }

    private fun fixUpdate(update: List<Int>): List<Int> {
        val ret = mutableListOf<Int>()
        update.forEach { pageToInsert ->
            val pagesAfterCurrent = rules.pagesThatMustBeAfter(pageToInsert)
            val whereToInsert = ret                       // Insert the new page
                .indexOfFirst { it in pagesAfterCurrent } // before the first other page that must come after
                .let { if (it == -1) ret.size else it }   // or last if there are no requirements on position
            ret.add(whereToInsert, pageToInsert)
        }
        return ret
    }

    fun solvePart2(): Int {
        val wrongUpdates = updates.filter { !isValid(it) }
        return wrongUpdates.map { fixUpdate(it) }.sumOf { it[it.size / 2] }
    }
}