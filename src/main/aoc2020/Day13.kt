package aoc2020

class Day13(input: List<String>) {
    private val ts = input.first().toInt()
    private val busses: List<String> = input.last().split(",")

    fun solvePart1(): Long {
        return busses.filterNot { it == "x" }
                .map {
                    val busId = it.toInt()
                    val nextDeparture = (ts / busId + 1) * busId - ts
                    nextDeparture to nextDeparture * busId.toLong()
                }.minByOrNull { it.first }!!.second
    }

    // Example 2: 17,x,13,19
    // id: 17, offset: 0
    // id: 13, offset: 2
    // id: 19, offset: 3
    //
    // y + 0 = 17x ==> y mod 17 = 0
    // y + 2 = 13x ==> y mod 13 = -2 (11)
    // y + 3 = 19x ==> y mod 19 = -3 (16)
    //
    // Use Chinese remainder theorem to find y
    // Modulus = id, Remainder = id - offset
    fun solvePart2(): Long {
        return busses.withIndex()
                .filterNot { it.value == "x" }
                .map { (offset, modulus) -> modulus.toInt() to modulus.toInt() - offset }
                .let { MyMath.chineseRemainder(it) }
    }
}
