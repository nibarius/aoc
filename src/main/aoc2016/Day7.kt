package aoc2016

class Day7(input: List<String>) {
    private val ipv7s = makeIpv7s(input)

    private data class Ipv7(private val supernetSequences: List<String>, private val hypernetSequences: List<String>) {
        fun String.hasAbba(): Boolean {
            if (length < 4) {
                return false
            }
            for (i in 3 until length) {
                if (this[i - 3] == this[i] && this[i - 2] == this[i - 1] && this[i - 1] != this[i]) {
                    return true
                }
            }
            return false
        }

        // Get all corresponding BABs for all the ABAs in the string
        fun String.getBabs(): List<String> {
            val ret = mutableListOf<String>()
            if (length < 3) {
                return ret
            }
            for (i in 2 until length) {
                if (this[i - 2] == this[i] && this[i - 1] != this[i]) {
                    ret.add("${this[i - 1]}${this[i]}${this[i - 1]}")
                }
            }
            return ret
        }

        fun supportsTls(): Boolean {
            return hypernetSequences.none { it.hasAbba() } && supernetSequences.any { it.hasAbba() }
        }

        fun supportsSsl(): Boolean {
            for (supernetSequence in supernetSequences) {
                for (bab in supernetSequence.getBabs()) {
                    for (hypernetSequence in hypernetSequences) {
                        if (hypernetSequence.contains(bab)) {
                            return true
                        }
                    }
                }
            }
            return false
        }
    }


    private fun makeIpv7s(input: List<String>): List<Ipv7> {
        val ret = mutableListOf<Ipv7>()
        input.forEach {
            var searchingFor = '['
            var currentIndex = 0
            val supernetSequences = mutableListOf<String>()
            val hypernetSequences = mutableListOf<String>()

            it.forEachIndexed { index, c ->
                if (c == searchingFor) {
                    val part = it.substring(currentIndex, index)
                    currentIndex = index + 1
                    when (searchingFor) {
                        '[' -> {
                            supernetSequences.add(part)
                            searchingFor = ']'
                        }
                        ']' -> {
                            hypernetSequences.add(part)
                            searchingFor = '['
                        }
                    }
                } else if (index == it.length - 1) {
                    supernetSequences.add(it.substring(currentIndex))
                }
            }
            ret.add(Ipv7(supernetSequences, hypernetSequences))
        }
        return ret
    }

    fun solvePart1(): Int {
        return ipv7s.count { it.supportsTls() }
    }

    fun solvePart2(): Int {
        return ipv7s.count { it.supportsSsl() }
    }
}
