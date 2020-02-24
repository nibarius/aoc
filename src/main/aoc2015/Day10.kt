package aoc2015

class Day10(val input: String) {

    private fun lookAndSay(look: List<Int>): List<Int> {
        var count = 1
        var prev = look[0]
        val ret = mutableListOf<Int>()
        var i = 1
        while (i < look.size) {
            val ch = look[i]
            if (ch == prev) {
                count++
            } else {
                ret.addAll(count.digits())
                ret.add(prev)
                count = 1
                prev = ch
            }
            i++
        }
        ret.add(count)
        ret.add(prev)
        return ret
    }

    private fun Int.digits(): List<Int> {
        val ret = mutableListOf<Int>()
        do {
            ret.add(this % 10)
            val rem = this / 10
        } while (rem > 0)
        return ret.reversed()
    }

    fun solvePart1(repeats: Int = 40): Int {
        var ret = input.map { it.toString().toInt() }
        repeat(repeats) {
            ret = lookAndSay(ret)
        }
        return ret.size
    }

    fun solvePart2(): Int {
        return solvePart1(50)
    }
}