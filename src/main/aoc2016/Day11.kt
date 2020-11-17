package aoc2016

import kotlin.math.abs

class Day11(input: List<String>) {

    // First approach used separate classes for Generators and Microchips with their positions
    // and names. But that got messy and cumbersome when moving items and when checking for
    // equivalent states. Just one list of Ints representing the positions of all the items
    // made everything much simpler.
    // items: generator1, chip1, generator2, chip2, etc
    data class State(val elevatorPosition: Int, val items: List<Int>, val steps: Int) {

        // Essential optimization: It doesn't matter which microchip/generator pair is moved
        // Example: With Chip A + Generator A and Chip B + Generator B on floor 3
        // it doesn't matter if the A pair is brought to floor 4 first or if
        // pair B is moved first. Both approaches will require the same amount of
        // moves to get both to floor 4. Do comparisons against a state where
        // the order of the pairs doesn't matter.
        fun getEquivalentState(): Int {
            var i = 0
            var ret = "E$elevatorPosition "
            val l = mutableListOf<Pair<Int, Int>>()
            while (i < items.size) {
                l.add(Pair(items[i], items[i + 1]))
                i += 2
            }
            l.sortWith(compareBy({ it.first }, { it.second }))
            for (p in l) {
                ret += "(${p.first},${p.second}) "
            }
            return ret.hashCode()
        }

        fun isWin() = items.all { it == 4 }

        fun isLose(items: List<Int>): Boolean {
            val floorsWithGenerators = items.filterIndexed { index, _ -> index % 2 == 0 }
            var i = 0
            while (i < items.size) {
                if (items[i] != items[i + 1] && floorsWithGenerators.contains(items[i + 1])) {
                    // there is a chip without it's generator on same floor and there is (another)
                    // generator on the same floor
                    return true
                }
                i += 2
            }
            return false
        }

        // minor optimization: elevator never need to go down below the lowest floor with an item
        fun elevatorCanMoveTo(floor: Int): Boolean {
            return floor >= items.minOrNull()!! && floor <= 4 && abs(elevatorPosition - floor) == 1
        }
    }

    private val initialState = parseInput(input)
    private val alreadyChecked = mutableSetOf<Int>()

    private fun parseInput(input: List<String>): State {
        val generatorList = mutableListOf<Pair<String, Int>>()
        val microchipList = mutableListOf<Pair<String, Int>>()

        input.forEachIndexed { index, description ->
            val floor = index + 1
            description.split(",", " and").forEach {
                when {
                    it.contains("generator") -> {
                        val generator = it.substringBefore(" generator").substringAfterLast(" ")
                        generatorList.add(Pair(generator, floor))
                    }
                    it.contains("microchip") -> {
                        val microchip = it.substringBefore("-compatible").substringAfterLast(" ")
                        microchipList.add(Pair(microchip, floor))
                    }
                }
            }
        }

        generatorList.sortBy { it.first }
        microchipList.sortBy { it.first }
        val items = mutableListOf<Int>()
        for (i in 0 until generatorList.size) {
            items.add(generatorList[i].second)
            items.add(microchipList[i].second)
        }
        return State(1, items, 0)
    }

    private fun enqueueOneMoveFor(state: State, dir: Int, toCheck: MutableList<State>, alreadyChecked: MutableSet<Int>, vararg itemsToMove: Int): Boolean {
        val toFloor = state.elevatorPosition + dir
        if (!state.elevatorCanMoveTo(toFloor)) {
            return false
        }

        val newItems = state.items.toMutableList()
        for (item in itemsToMove) {
            newItems[item] = toFloor
        }

        if (state.isLose(newItems)) {
            return false
        }

        val newState = State(toFloor, newItems, state.steps + 1)

        // Optimization: Don't enqueue a state that is already in the queue, or an item that has
        // previously been checked.
        val stateIsAlreadyQueued = toCheck.any { it.getEquivalentState() == newState.getEquivalentState() }
        val stateHasAlreadyBeenChecked = alreadyChecked.contains(newState.getEquivalentState())
        if (!stateHasAlreadyBeenChecked && !stateIsAlreadyQueued) {
            toCheck.add(newState)
        }
        return true
    }

    private fun enqueueAllPossibleMovesFor(state: State, toCheck: MutableList<State>, alreadyChecked: MutableSet<Int>) {
        val movableItems = mutableListOf<Int>()
        state.items.forEachIndexed { index, itemFloor ->
            if (itemFloor == state.elevatorPosition) {
                movableItems.add(index)
            }
        }

        //make all combos of movable items for moving two items at a time
        val itemPairs = mutableListOf<List<Int>>()
        for (first in 0 until movableItems.size) {
            for (second in first + 1 until movableItems.size) {
                itemPairs.add(listOf(movableItems[first], movableItems[second]))
            }
        }

        // upward
        var hasMovedUpTwo = false
        for (i in 0 until itemPairs.size) {
            hasMovedUpTwo = enqueueOneMoveFor(state, 1, toCheck, alreadyChecked, itemPairs[i][0], itemPairs[i][1])
                    || hasMovedUpTwo
        }
        if (!hasMovedUpTwo) {
            // Optimization: Don't try to move up one item if it's possible to move up two
            for (i in 0 until movableItems.size) {
                enqueueOneMoveFor(state, 1, toCheck, alreadyChecked, movableItems[i])
            }
        }

        //downward
        var hasMovedDown = false
        for (i in 0 until movableItems.size) {
            hasMovedDown = enqueueOneMoveFor(state, -1, toCheck, alreadyChecked, movableItems[i])
                    || hasMovedDown//move down
        }

        if (!hasMovedDown) {
            // Optimization: Don't try to move down two items if it's possible to move down one
            for (i in 0 until itemPairs.size) {
                enqueueOneMoveFor(state,-1, toCheck, alreadyChecked, itemPairs[i][0], itemPairs[i][1]) //move down
            }
        }
    }


    private fun run(initialState: State): Int {
        val toCheck = mutableListOf(initialState)
        var iterations = 0
        while (toCheck.size > 0) {
            iterations++
            val nextState = toCheck.removeAt(0)
            alreadyChecked.add(nextState.getEquivalentState())
            when {
                nextState.isWin() -> {
                    println("Done after $iterations iterations")
                    return nextState.steps
                }
                nextState.isLose(nextState.items) -> {
                    // Should never happen with a working implementation
                    println("Lose state found: $nextState")
                }
                else -> enqueueAllPossibleMovesFor(nextState, toCheck, alreadyChecked)
            }
        }
        // Should never happen with a working implementation
        println("Ran out of things to check after $iterations iterations")
        return -1
    }


    // Example for part 1:
    // With no optimizations: 390682 iterations
    // Skip already checked states (very naive approach): 1298 iterations
    // Don't move to empty floors: 1209 iterations
    // Only make valid moves + implementation bug fixes: 773 iterations
    // Don't take one up if you can take two, don't take two down if you can take 1: 126 iterations
    // Improved state checking - Discard all equivalent states: 43 iterations
    //
    // Part 1:
    // Without improved state checking: 21498 iterations, 18 seconds
    // With improved state checking: 2586 iterations, 1.5 seconds
    fun solvePart1(): Int {
        return run(initialState)
    }

    // Without improved state checking: 782718 iterations, 7 hours 27 minutes
    // With improved state checking: 8039 iterations, 14 seconds
    fun solvePart2(): Int {
        val extendedList = initialState.items.toMutableList()
        extendedList.addAll(listOf(1, 1, 1, 1))
        val part2State = State(1, extendedList, 0)
        return run(part2State)
    }
}
