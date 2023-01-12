package aoc2022

import kotlin.math.abs
import kotlin.math.sign

class Day20(input: List<String>) {

    private val parsedInput = input.map { it.toInt() }

    fun solvePart1(): Long {
        val sequence = Sequence(parsedInput, 1)
        sequence.mix()
        return sequence.coordinateSum()
    }

    fun solvePart2(): Long {
        val sequence = Sequence(parsedInput, 811589153)
        repeat(10) {
            sequence.mix()
        }
        return sequence.coordinateSum()
    }

    companion object {
        class Node(val value: Long, var prev: Node?, var next: Node?)

        /**
         * A circular sequence of nodes, implemented like a linked list.
         */
        class Sequence(input: List<Int>, private val decryptionKey: Long) {
            val size = input.size
            private val nodesInOriginalOrder = makeSequence(input)

            /**
             * Mix the sequence once.
             */
            fun mix() {
                for (current in nodesInOriginalOrder) {
                    val stepsToMove = getMinStepsToMove(current.value)
                    val sign = stepsToMove.sign

                    if (stepsToMove != 0) {
                        // detach current node
                        current.prev!!.next = current.next
                        current.next!!.prev = current.prev

                        var newPos = current
                        if (sign > 0) {
                            // Move forward
                            repeat(stepsToMove) {
                                newPos = newPos.next!!
                            }
                        } else {
                            // move backward, one extra step is needed since the new node is inserted after the
                            // selected node (when moving backwards we want to insert it before)
                            repeat(-stepsToMove + 1) {
                                newPos = newPos.prev!!
                            }
                        }
                        // Insert current node after the newPos node
                        current.prev = newPos
                        current.next = newPos.next
                        newPos.next!!.prev = current
                        newPos.next = current
                    }
                }
            }

            /**
             * Calculate the minimum required steps to walk to the element that is 'steps' steps away.
             * There is no need to walk several laps around the sequence, and sometimes it's shorter to
             * walk the other way.
             */
            fun getMinStepsToMove(steps: Long): Int {
                val numOtherElements = size - 1
                var sign = steps.sign
                var amount = abs(steps) % numOtherElements

                // It's closer to go the other way
                if (amount > numOtherElements / 2) {
                    amount = numOtherElements - amount
                    sign *= -1
                }
                return (amount * sign).toInt()
            }

            /**
             * The sum of the values at position 1000, 2000, and 3000 from 0.
             */
            fun coordinateSum(): Long {
                var current = nodesInOriginalOrder.find { it.value == 0L }!!
                return listOf(1, 2, 3).sumOf {
                    // Move forward 1000 steps and then get the value at that position
                    repeat(1000 % size) { current = current.next!! }
                    current.value
                }
            }

            /**
             * Make a circular sequence of nodes and return a list holding the original order
             * of the nodes before any mixing has taken place.
             */
            private fun makeSequence(input: List<Int>): List<Node> {
                val first = Node(input.first() * decryptionKey, null, null)
                val originalOrder = mutableListOf(first)
                var current = first
                for (value in input.drop(1)) {
                    val next = Node(value * decryptionKey, current, null)
                    originalOrder.add(next)
                    current.next = next
                    current = next
                }
                current.next = first
                first.prev = current
                return originalOrder
            }
        }
    }
}