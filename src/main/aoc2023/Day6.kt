package aoc2023

class Day6(input: List<String>) {

    private data class Race(val time: Int, val distance: Long)

    private val races: List<Race>
    private val race2: Race

    init {
        races = buildList {
            val times = input.first().substringAfter(":").trim().split("\\s+".toRegex())
            val distance = input.last().substringAfter(":").trim().split("\\s+".toRegex())
            for (i in times.indices) {
                add(Race(times[i].toInt(), distance[i].toLong()))
            }
            race2 = Race(
                times.joinToString("").toInt(),
                distance.joinToString("").toLong()
            )
        }
    }

    fun distance(holdTime: Int, totalTime: Int) = (totalTime - holdTime).toLong() * holdTime.toLong()

    fun solvePart1(): Int {
        return races.map { race ->
            (1..<race.time).map { distance(it, race.time) }.filter { it > race.distance }
        }.map { it.size }.reduce(Int::times)
    }

    fun solvePart2(): Int {
        val firstWinningTime = (1..<race2.time).first { distance(it, race2.time) > race2.distance }
        val lastWinningTime = (1..<race2.time).reversed().first { distance(it, race2.time) > race2.distance }
        return lastWinningTime - firstWinningTime + 1
    }
}