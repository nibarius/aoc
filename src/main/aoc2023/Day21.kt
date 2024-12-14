package aoc2023

import AMap
import Pos
import kotlin.math.abs
import kotlin.math.sign

class Day21(input: List<String>) {

    private val garden = AMap.parse(input, listOf('#'))
    private val startPos = garden.positionOf('S')
    private val gardenSize = garden.xRange().count()

    private fun countReachablePlots(steps: Int): Int {
        var currentPos = setOf(startPos)
        repeat(steps) {
            currentPos = currentPos.flatMap { it.allNeighbours() }.filter { it in garden.keys }.toSet()
        }
        return currentPos.size
    }

    // Returns in which garden the given absolute position is in
    private fun whichGarden(pos: Pos, size: Int): Pair<Int, Int> {
        val x = pos.x - if (pos.x < 0) size - 1 else 0
        val y = pos.y - if (pos.y < 0) size - 1 else 0
        return x / size to y / size
    }

    // There are 8 sections:
    // Four that are directly above/below/left/right of the initial center garden
    // and four that cover all gardens diagonally from the center.
    //  123
    //  4.5
    //  678
    //
    // We'll reach a new diagonal garden every N steps, where N is the width of the original garden.
    // The first visited point in each diagonal garden is always the corner closest to the center
    //
    // For the straight gardens we have to reach the third additional garden before the entry points
    // become stable
    //
    // Which plots are visited in each garden toggles when moving to the next one.
    // With total even number of steps and gardens: ABC means A and C is even variant and B is odd variant.


    private data class GardenInfo(
        val id: Pair<Int, Int>,
        val entry: Pos,
        var firstVisit: Int = -1,
        val sizes: MutableList<Int>
    )

    private fun countReachablePlots2(steps: Int): Long {
        // visible plots toggle between odd and even steps, so keep one set for odd and one for even
        // and add the just add the frontier to the appropriate step
        val reachable = listOf(mutableSetOf(startPos), mutableSetOf())
        var frontier = setOf(startPos)

        val visitedGardens = mutableMapOf(Pair(0, 0) to GardenInfo(Pair(0, 0), startPos, 0, mutableListOf(1)))

        // Do a brute force scanning far enough out from the origin that each next garden behaves
        // the same. How far to look was found by visualizing each step and manually looking for the
        // appropriate distance.
        val interestingSectors = setOf(
            /*                                   */Pair(0, -2),
            /*                      */Pair(-1, -1), Pair(0, -1), Pair(1, -1),
            Pair(-3, 0), Pair(-2, 0), Pair(-1, 0), Pair(0, 0), Pair(1, 0), Pair(2, 0), Pair(3, 0),
            /*                      */Pair(-1, 1), Pair(0, 1), Pair(1, 1),
            /*                                   */Pair(0, 2)
        )

        repeat(steps) { step ->
            val offset = (step + 1) % 2
            frontier = frontier
                .flatMap {// all unvisited neighbours to the frontier
                    it.allNeighbours().filter { neighbour -> neighbour !in reachable[offset] }
                }
                .filter {// Filter out rocks
                    it.wrapWithin(gardenSize, gardenSize) in garden.keys
                }
                .toSet()

            val newFrontier = mutableSetOf<Pos>()

            // check if we entered a new garden
            frontier
                .groupBy {
                    whichGarden(it, gardenSize)
                } // which garden to all positions in the frontier in the same garden
                .filter { it.key in interestingSectors }
                .forEach { (inGarden, positions) ->
                    newFrontier.addAll(positions)
                    var g = visitedGardens[inGarden]
                    if (g == null) {
                        g = GardenInfo(
                            inGarden, positions.first().wrapWithin(gardenSize, gardenSize), step + 1,
                            mutableListOf(positions.size)
                        )
                        visitedGardens[inGarden] = g
                    } else {
                        // New size is the size of the garden one step before plus the frontier
                        // since all plots toggle on odd/even steps. Add 0 to the beginning of the list
                        // to ensure that there are always at least two entries in it.
                        val prevSize = (listOf(0) + g.sizes).dropLast(1).last()
                        g.sizes.add(prevSize + positions.size)
                    }
                }
            // Add the frontier to the set of reachable plots
            reachable[offset].addAll(frontier)
            frontier = newFrontier
        }
        return reachableGardens(visitedGardens, steps)
    }

    private fun howManyGardensAway(visitedGardens: MutableMap<Pair<Int, Int>, GardenInfo>, steps: Int): Int {
        // look at one arbitrary garden one step away and extrapolate how far we'll get
        // in test/input you can always reach a new garden in "gardenSize" steps
        // not all gardens are entered at the same time in the test input, so there might
        // be some number of steps where it returns the wrong answer. But in the real input there
        // are open lanes from the center, so it always takes "gardenSize" steps there. All given
        // examples inputs also works fine.
        val gardenInfo = visitedGardens[Pair(1, 0)]!!
        val stepsRemaining = steps - gardenInfo.firstVisit
        val whole = stepsRemaining / gardenSize
        return 1 + whole
    }

    /**
     * Returns the number of reachable plots in the given garden with the given amount of steps.
     */
    private fun howManyPlotsAt(
        visitedGardens: MutableMap<Pair<Int, Int>, GardenInfo>,
        id: Pair<Int, Int>,
        steps: Int
    ): Long {
        // With test example the gardens becomes stable after the third garden, for vertical after
        // the second. Diagonal gardens are stable after the first. With the real input they are
        // always stable after the first garden.
        val initial = when {
            id.second == 0 -> visitedGardens[Pair(id.first.sign * 3, 0)]!! // horizontal
            id.first == 0 -> visitedGardens[Pair(0, id.second.sign * 2)]!! // vertical
            else -> visitedGardens[Pair(id.first.sign, id.second.sign)]!!
        }
        val gardensFromInitial = abs(id.first) + abs(id.second) - abs(initial.id.first) - abs(initial.id.second)
        // it takes gardenSize steps to move to the next garden
        val stepsLeft = steps - initial.firstVisit - gardensFromInitial * gardenSize
        val stepsToFull = initial.sizes.size
        val size = if (stepsLeft < 0) {
            0
        } else if (stepsLeft < stepsToFull) {
            initial.sizes[stepsLeft]
        } else {
            // Each additional step when the garden is full toggles between the two last states
            initial.sizes.takeLast(2)[(stepsLeft - stepsToFull) % 2]
        }
        return size.toLong()
    }

    /*
           ANB
          AanbB
         Aa1.2bB
        Aa1...2bB
        Ee.....wW      Radius 4 example
        Dd4...3cC
         Dd4.3cC
          DdscC
           DSC

    The three outermost gardens in the circle may not be filled up completely and needs to be
    counted separately. The outermost edges (uppercase A-D) have the same length as the radius.
    The one inside that (lowercase a-d) have radius-1 in length, and the innermost (1-4) have
    radius-2 in length.

    The corners (ENWS) and those one step inside them (enws) may also not be full, they also
    needs to be counted separately.

    Everything inside this (.) are completely filled gardens. This is the area of a manhattan
    coordinate circle with radius "radius - 2". The area can be seen as the sum of the circumference
    of a series of circles each with one higher radius than the next.

    Each circle with an even radius have the same plots reachable as the center garden.
    Each circle with an odd radius have the opposite plots reachable as the center garden.

    The circumference of a circle in manhattan coordinates is 4 * radius.
    Sum of all even numbers up to L is L/2 * (L/2+1)
    Sum of all odd numbers up to L is ((L+1)/2)^2
     */
    private fun reachableGardens(visitedGardens: MutableMap<Pair<Int, Int>, GardenInfo>, steps: Int): Long {
        val radius = howManyGardensAway(visitedGardens, steps)
        val reachableOnTheEdge = listOf(
            Triple(Pair(-radius, -1), Pair(-radius + 1, -1), Pair(-radius + 2, -1)), // NW edge
            Triple(Pair(-radius, 1), Pair(-radius + 1, 1), Pair(-radius + 2, 1)), // SW edge
            Triple(Pair(radius, -1), Pair(radius - 1, -1), Pair(radius - 2, -1)), // NE edge
            Triple(Pair(radius, 1), Pair(radius - 1, 1), Pair(radius - 2, 1)), // NE edge
        ).sumOf { (a, b, c) ->
            howManyPlotsAt(visitedGardens, a, steps) * radius +
                    howManyPlotsAt(visitedGardens, b, steps) * (radius - 1) +
                    howManyPlotsAt(visitedGardens, c, steps) * (radius - 2)
        }

        val reachableCorners = listOf(
            Pair(-radius, 0), Pair(-radius + 1, 0), // West
            Pair(0, -radius), Pair(0, -radius + 1), // North
            Pair(radius, 0), Pair(radius - 1, 0), // East
            Pair(0, radius), Pair(0, radius - 1)  // South
        ).sumOf { howManyPlotsAt(visitedGardens, it, steps) }

        val innerRadius = (radius - 2).toLong()

        // 1 extra for the center garden
        val numEvenGardens = 1 + 4 * (innerRadius / 2 * (innerRadius / 2 + 1))
        val numOddGardens = 4 * (((innerRadius + 1) / 2) * ((innerRadius + 1) / 2))

        val sizes = visitedGardens[Pair(0, 0)]!!.sizes
        // list of two items, the first saying the size of a full garden at even steps
        // the second the size of a full garden at odd steps.
        val evenSize: Int
        val oddSize: Int
        if (sizes.size % 2 == steps % 2) {
            // The index of the sizes array note number of steps taken from the starting position
            // even number of entries means the order of the last two items is even, odd
            // Example: Even number (4) of entries in sizes: [0, 1, 2, 3]
            evenSize = sizes.takeLast(2).first()
            oddSize = sizes.takeLast(2).last()
        } else {
            // Example: Odd number (5) of entries in sizes: [0, 1, 2, 3, 4]
            evenSize = sizes.takeLast(2).last()
            oddSize = sizes.takeLast(2).first()
        }

        return reachableOnTheEdge + reachableCorners + numEvenGardens * evenSize + numOddGardens * oddSize
    }

    fun solvePart1(stepsLeft: Int = 64): Int {
        return countReachablePlots(stepsLeft)
    }

    fun solvePart2(stepsLeft: Int = 26501365): Long {
        return countReachablePlots2(stepsLeft)
    }
}